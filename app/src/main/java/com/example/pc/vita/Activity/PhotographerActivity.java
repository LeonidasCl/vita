package com.example.pc.vita.Activity;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.pc.vita.APP;
import com.example.pc.vita.Adapter.ImageAddGridViewAdapter;
import com.example.pc.vita.Adapter.ImagePagerAdapter;
import com.example.pc.vita.Adapter.MyViewPager;
import com.example.pc.vita.Adapter.PhotoViewAttacher;
import com.example.pc.vita.Data.MainStruct.ThemeObject;
import com.example.pc.vita.Fragment.ImageDisplayFragment;
import com.example.pc.vita.Network.MyInterface;
import com.example.pc.vita.Network.NetRequest;
import com.example.pc.vita.R;
import com.example.pc.vita.Util.CommonUrl;
import com.example.pc.vita.Util.CommonUtils;
import com.example.pc.vita.Util.UploadPhotoUtil;
import com.example.pc.vita.Util.UserInfoUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by pc on 2016/6/28.
 */
public class PhotographerActivity extends AppCompatActivity implements View.OnClickListener, MyInterface.NetRequestIterface, MyInterface.OnSingleTapDismissBigPhotoListener {

    private final int UPLOAD_TAKE_PICTURE = 5;
    private final int UPLOAD_LOCAL_PICTURE = 6;
    private final int SAVE_PHOTO_IMAGE = 7;
    private final int NONE = 0, TAKE_PICTURE = 1, LOCAL_PICTURE = 2;
    private final int SHOW_PHOTO = 4;
    private final int SAVE_THEME_IMAGE = 8;
    private final int SHOW_TAKE_PICTURE = 9;
    private final int SHOW_LOCAL_PICTURE = 10;
    private RelativeLayout edit_photo_fullscreen_layout, edit_photo_outer_layout,
            uploading_photo_progress, display_big_image_layout,show_upload_pic_layout;
    private Animation get_photo_layout_out_from_up, get_photo_layout_in_from_down;
    private TextView title, take_picture, select_local_picture, position_in_total, upload;
    private ImageView delete_image;
    private String photo_take_file_path = APP.photo_path + "temp.png";
    private String photo_local_file_path, photo_url = null, takePictureUrl, newThemeId;
    private Intent intent;
    private NetRequest requestFragment;
    private GridView display_image_gridview;
    private GridView add_image_gridview;
    private int addPicCount = 1, addTakePicCount = 1, viewpagerPosition;
    private List<String> uploadImgUrlList = new ArrayList<String>();
    private List<Drawable> addPictureList = new ArrayList<Drawable>();
    private List<String> pictureUrlList = new ArrayList<String>();
    private ImageAddGridViewAdapter imageAddGridViewAdapter;
    private ImagePagerAdapter imagePagerAdapter;
    private MyViewPager image_viewpager;
    private boolean isBigImageShow = false, isShowUploadPic=false,addPic = false, clearFormerUploadUrlList = true;
    private EditText theme_title_edit, theme_desc_edit;
    private ListView theme_listview;

    Handler handler = new Handler() {

        //TODO 选完图片后就是在这里处理的
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {

                case SAVE_THEME_IMAGE:
                    Map<String, Object> map = (Map<String, Object>) msg.obj;
                    requestFragment.httpRequest(map, CommonUrl.saveThemeImgNew);
                    break;
                case UPLOAD_TAKE_PICTURE:
                    Log.d("gaolei",
                            "uploadImgUrlList.size()--------upload---------"
                                    + uploadImgUrlList.size());
                    if (uploadImgUrlList.size() > 0) {
                        for (int i = 0; i < uploadImgUrlList.size(); i++) {
                            saveThemeImgNew(newThemeId, uploadImgUrlList.get(i));
                        }
                    }
                    getThemeList();
                    show_upload_pic_layout.setVisibility(View.VISIBLE);
                    isShowUploadPic=true;
                    break;
                case SHOW_TAKE_PICTURE:
                    addPic = true;
                    if (clearFormerUploadUrlList) {
                        if (uploadImgUrlList.size() > 0) {
                            uploadImgUrlList.clear();
                        }
                        clearFormerUploadUrlList = false;
                    }
                    Bitmap bitmap = UploadPhotoUtil.getInstance()
                            .trasformToZoomBitmapAndLessMemory(takePictureUrl);
                    BitmapDrawable bd = new BitmapDrawable(getResources(), bitmap);
                    addPictureList.add(bd);
                    uploadImgUrlList.add(takePictureUrl);
                    imageAddGridViewAdapter.changeList(addPictureList);
                    imageAddGridViewAdapter.notifyDataSetChanged();
                    addPicCount++;
                    Log.d("gaolei", "uploadImgUrlList.size()--------add---------"
                            + uploadImgUrlList.size());
                    break;

                case SHOW_LOCAL_PICTURE:
                    addPic = true;
                    if (clearFormerUploadUrlList) {
                        if (uploadImgUrlList.size() > 0) {
                            uploadImgUrlList.clear();
                        }
                        clearFormerUploadUrlList = false;
                    }//获取到资源位置
                    Uri uri = intent.getData();
                  /*  String[] pojo = {MediaStore.Images.Media.DATA};

                    CursorLoader cursorLoader = new CursorLoader(UploadPicActivity.this, uri, pojo, null,null, null);
                    Cursor cursor = cursorLoader.loadInBackground();
                    cursor.moveToFirst();
                    String photo_local_file_path = cursor.getString(cursor.getColumnIndex(pojo[0]));*/
                    String photo_local_file_path =getPath_above19(getApplicationContext(),uri);
                    Bitmap bitmap2 = UploadPhotoUtil.getInstance()
                            .trasformToZoomBitmapAndLessMemory(photo_local_file_path);
                    addPictureList.add(new BitmapDrawable(getResources(),
                            bitmap2));
                    uploadImgUrlList.add(photo_local_file_path);
                    imageAddGridViewAdapter.changeList(addPictureList);
                    imageAddGridViewAdapter.notifyDataSetChanged();
                    addPicCount++;

                    break;
            }
        }
    };
    //是否为外置存储器
    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }
    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */
    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }
    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }
    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is Google Photos.
     */
    public static boolean isGooglePhotosUri(Uri uri) {
        return "com.google.android.apps.photos.content".equals(uri.getAuthority());
    }
    /**
     * Get the value of the data column for this Uri. This is useful for
     * MediaStore Uris, and other file-based ContentProviders.
     *
     * @param context       The context.
     * @param uri           The Uri to query.
     * @param selection     (Optional) Filter used in the query.
     * @param selectionArgs (Optional) Selection arguments used in the query.
     * @return The value of the _data column, which is typically a file path.
     */
    public static String getDataColumn(Context context, Uri uri, String selection,
                                       String[] selectionArgs) {
        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {column};
        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs,
                    null);
            if (cursor != null && cursor.moveToFirst()) {
                final int index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    public  static String getPath_above19(final Context context, final Uri uri) {
        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;
        // DocumentProvider
        if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
            // ExternalStorageProvider
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];
                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }
                // TODO handle non-primary volumes
            }
            // DownloadsProvider
            else if (isDownloadsDocument(uri)) {
                final String id = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));
                return getDataColumn(context, contentUri, null, null);
            }
            // MediaProvider
            else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];
                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }
                final String selection = "_id=?";
                final String[] selectionArgs = new String[]{
                        split[1]
                };
                return getDataColumn(context, contentUri, selection, selectionArgs);
            }
        }
        // MediaStore (and general)
        else if ("content".equalsIgnoreCase(uri.getScheme())) {
            // Return the remote address
            if (isGooglePhotosUri(uri))
                return uri.getLastPathSegment();
            return getDataColumn(context, uri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }
        return null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.upload_pic_activity);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window window = getWindow();

            window.setFlags(
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        requestFragment = new NetRequest(this, this);
        edit_photo_fullscreen_layout = (RelativeLayout) findViewById(R.id.edit_photo_fullscreen_layout);
        edit_photo_outer_layout = (RelativeLayout) findViewById(R.id.edit_photo_outer_layout);
        uploading_photo_progress = (RelativeLayout) findViewById(R.id.uploading_photo_progress);
        display_big_image_layout = (RelativeLayout) findViewById(R.id.display_big_image_layout);
        show_upload_pic_layout = (RelativeLayout) findViewById(R.id.show_upload_pic_layout);
        take_picture = (TextView) findViewById(R.id.take_picture);
        title = (TextView) findViewById(R.id.title);
        title.setText(getResources().getString(R.string.upload_pic));
        position_in_total = (TextView) findViewById(R.id.position_in_total);
        select_local_picture = (TextView) findViewById(R.id.select_local_picture);
        upload = (TextView) findViewById(R.id.upload);
        upload.setVisibility(View.VISIBLE);
        theme_title_edit = (EditText) findViewById(R.id.theme_title_edit);
        theme_desc_edit = (EditText) findViewById(R.id.theme_desc_edit);
        delete_image = (ImageView) findViewById(R.id.delete_image);
        add_image_gridview = (GridView) findViewById(R.id.add_image_gridview);
        image_viewpager = (MyViewPager) findViewById(R.id.image_viewpager);
        theme_listview = (ListView) findViewById(R.id.theme_listview);
        upload.setOnClickListener(this);
        delete_image.setOnClickListener(this);

        ImageDisplayFragment.showNetImg = false;
        addPictureList.add(getResources().getDrawable(
                R.mipmap.theme_add_picture_icon));
        imageAddGridViewAdapter = new ImageAddGridViewAdapter(this,
                addPictureList);
        add_image_gridview.setAdapter(imageAddGridViewAdapter);
        add_image_gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                // TODO 这里是添加图片的按钮的回调

                if (position == 0) {
                    if (addPicCount == 4) {
                      /*  CommonUtils.getUtilInstance().showToast(UploadPicActivity.this,
                                getString(R.string.no_more_than_3));*/
                        return;
                    } else {
                        //点击添加图片
                        edit_photo_fullscreen_layout
                                .setVisibility(View.VISIBLE);
                     /*   get_photo_layout_in_from_down = AnimationUtils
                                .loadAnimation(UploadPicActivity.this,
                                        R.anim.search_layout_in_from_down);*/
                        edit_photo_outer_layout
                                .startAnimation(get_photo_layout_in_from_down);
                    }
                } else {
                    //点击图片查看大图
                    showImageViewPager(position, pictureUrlList,
                            uploadImgUrlList, "local", "upload");
                    viewpagerPosition = position - 1;
                }
            }
        });

        take_picture.setOnClickListener(this);
        select_local_picture.setOnClickListener(this);

    }

    public void showImageViewPager(int position,
                                   final List<String> pictureUrlList, final List<String> localUrlList,
                                   final String flag, final String str) {
        List<String> urlList;
        if (flag.equals("net")) {
            urlList = pictureUrlList;
        } else {
            urlList = localUrlList;
        }
        Log.d("gaolei", "urlList.toString()------------------" + urlList.toString());
        display_big_image_layout.setVisibility(View.VISIBLE);
        imagePagerAdapter = new ImagePagerAdapter(getSupportFragmentManager(), urlList);
        image_viewpager.setAdapter(imagePagerAdapter);
        imagePagerAdapter.notifyDataSetChanged();
        image_viewpager.setOffscreenPageLimit(imagePagerAdapter.getCount());
        image_viewpager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrollStateChanged(int state) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onPageSelected(int position) {
                // TODO Auto-generated method stub
                viewpagerPosition = position;
                if (flag.equals("net")) {
                    position_in_total.setText((position + 1) + "/"
                            + pictureUrlList.size());
                } else {
                    position_in_total.setText((position + 1) + "/"
                            + localUrlList.size());
                }

            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
                // TODO Auto-generated method stub
            }
        });
        if (str.equals("display")) {
            image_viewpager.setCurrentItem(position);
            delete_image.setVisibility(View.GONE);
            position_in_total.setText((position + 1) + "/" + urlList.size());
            isBigImageShow = true;

        } else {
            image_viewpager.setCurrentItem(position - 1);
            delete_image.setVisibility(View.VISIBLE);
            position_in_total.setText((position) + "/" + urlList.size());
            isBigImageShow = true;

        }
        PhotoViewAttacher.setOnSingleTapToPhotoViewListener(this);
    }

    public void uploadUserPhotoNew(final String filePath) {
        uploading_photo_progress.setVisibility(View.VISIBLE);
        //为什么另开一个线程呢?因为要把图片字节流转化为字符串上传，比较耗时，阻塞UI线程，会使应用卡卡卡，所以要另开一线程
        new Thread() {
            public void run() {
                String fileType = UploadPhotoUtil.getInstance().getFileType(filePath);
                String fileString = UploadPhotoUtil.getInstance().getUploadPhotoZoomString(
                        filePath);
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("imgType", fileType);
                map.put("imgBody", fileString);
                Message msg = handler.obtainMessage();
                msg.obj = map;
                msg.what = SAVE_PHOTO_IMAGE;
                handler.sendMessage(msg);

            }
        }.start();
    }

    public void showEditPhotoLayout(View view) {
        edit_photo_fullscreen_layout.setVisibility(View.VISIBLE);
        get_photo_layout_in_from_down = AnimationUtils.loadAnimation(
                this, R.anim.search_layout_in_from_down);
        edit_photo_outer_layout.startAnimation(get_photo_layout_in_from_down);
    }

    public void hideEditPhotoLayout(View view) {
        get_photo_layout_out_from_up = AnimationUtils.loadAnimation(
                this, R.anim.search_layout_out_from_up);
        edit_photo_outer_layout.startAnimation(get_photo_layout_out_from_up);
        get_photo_layout_out_from_up
                .setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationEnd(Animation arg0) {
                        //
                        edit_photo_fullscreen_layout.setVisibility(View.GONE);
                    }

                    @Override
                    public void onAnimationRepeat(Animation arg0) {
                        //
                    }

                    @Override
                    public void onAnimationStart(Animation arg0) {
                        //
                    }
                });
    }
    //TODO 下面函数是选中了要上传图片或者拍照打完钩后运行的
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if (resultCode == NONE)
            return;
        //为什么不在这处理图片呢？因为处理图片比较耗时，如果在这里处理图片，从图库或者拍照Activity时会不流畅，很卡卡卡，试试就知道了
        if (requestCode == TAKE_PICTURE) {
            handler.sendEmptyMessage(SHOW_TAKE_PICTURE);
            return;
        }
        if (resultCode == Activity.RESULT_OK) {
            this.intent = intent;
            handler.sendEmptyMessage(SHOW_LOCAL_PICTURE);
        }
    }

    @Override
    //TODO 选择本地图片 拍照 取消 三个按钮 发表按钮
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.take_picture:
                edit_photo_fullscreen_layout.setVisibility(View.GONE);
                takePictureUrl = APP.photo_path + "take_pic"
                        + addTakePicCount + ".png";
                File file = new File(takePictureUrl);
                if (file.exists()) {
                    if (file.exists()) {
                        file.delete();
                    }
                }
                intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
                startActivityForResult(intent, TAKE_PICTURE);
                addTakePicCount++;
                break;
            case R.id.select_local_picture:
                edit_photo_fullscreen_layout.setVisibility(View.GONE);
                intent = new Intent();
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                        "image/*");
                startActivityForResult(intent, LOCAL_PICTURE);
                break;
            case R.id.upload:
                if (UserInfoUtil.getInstance().getAuthKey() == null) {
                    CommonUtils.getUtilInstance().showToast(this,
                            getString(R.string.publish_theme_after_login));
                    return;
                }
                if (theme_title_edit.getText().toString().length() == 0) {
                    CommonUtils.getUtilInstance().showToast(this,
                            getString(R.string.input_theme_comment_title));
                    return;
                }
                if (theme_desc_edit.getText().toString().length() == 0) {
                    CommonUtils.getUtilInstance().showToast(this,
                            getString(R.string.input_theme_comment_desc));
                }
                if (!addPic) {
                    if (clearFormerUploadUrlList) {
                        if (uploadImgUrlList.size() > 0) {
                            uploadImgUrlList.clear();
                            clearFormerUploadUrlList = false;
                        }
                    }
                }
                saveThemeInfo();


                if (!clearFormerUploadUrlList) {
                    clearFormerUploadUrlList = true;
                }
                break;
            case R.id.delete_image:
                if (uploadImgUrlList.size() == 0) {
                    return;
                }
                uploadImgUrlList.remove(viewpagerPosition);
                imagePagerAdapter.changeList(uploadImgUrlList);
                imagePagerAdapter.notifyDataSetChanged();
                addPictureList.remove(viewpagerPosition + 1);
                imageAddGridViewAdapter.changeList(addPictureList);
                imageAddGridViewAdapter.notifyDataSetChanged();
                position_in_total.setText((viewpagerPosition + 1) + "/"
                        + uploadImgUrlList.size());
                if (uploadImgUrlList.size() == 0) {
                    display_big_image_layout.setVisibility(View.GONE);
                    isBigImageShow = false;
                }
                addPicCount--;
                break;
        }
    }

    public void hideAddThemeLayout() {
        edit_photo_fullscreen_layout.setVisibility(View.GONE);
        upload.setVisibility(View.GONE);
        title.setText(getResources().getString(R.string.detail));
        addPic = false;
    }

    public void saveThemeImgNew(final String themeId, final String picUrl) {
        new Thread() {
            public void run() {
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("themeId", themeId);
                map.put("imgBody", UploadPhotoUtil.getInstance()
                        .getUploadBitmapZoomString(picUrl));
                map.put("imgType",
                        UploadPhotoUtil.getInstance().getFileType(picUrl));
                map.put("type", 1);
                Message msg = handler.obtainMessage();
                msg.obj = map;
                msg.what = SAVE_THEME_IMAGE;
                handler.sendMessage(msg);
            }
        }.start();
    }

    public void getThemeList() {

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("from", 0);
        map.put("to", 2);
        requestFragment.httpRequest(map, CommonUrl.getThemeList);
    }

    public void saveThemeInfo() {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("themeTitle", theme_title_edit.getText().toString());
        map.put("themeDescr", theme_desc_edit.getText().toString());
        requestFragment.httpRequest(map, CommonUrl.saveThemeInfo);
    }

    @Override
    public void changeView(final String result, String requestUrl) {

        if (requestUrl.equals(CommonUrl.saveThemeImgNew)) {
            try {
                JSONObject object = new JSONObject(result);
                int errorCode = object.getInt("errorCode");
                Log.d("gaolei", "result--------saveThemeImgNew-------------"
                        + result);
            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        if (requestUrl.equals(CommonUrl.saveThemeInfo)) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    try {
                        JSONObject gamesInfoObject = new JSONObject(result);
                        Log.d("gaolei", "result-----------saveThemeInfo------------"
                                + result);
                        int errorCode = gamesInfoObject.getInt("errorCode");

                        if (errorCode == 0) {
                           /* CommonUtils.getUtilInstance().showToast(UploadPicActivity.this,
                                    getString(R.string.publish_theme_sucess));*/
                            newThemeId = gamesInfoObject.getString("themeId");
                            UserInfoUtil.getInstance().setThemeNum(
                                    UserInfoUtil.getInstance().getThemeNum() + 1);
                            handler.sendEmptyMessageDelayed(UPLOAD_TAKE_PICTURE, 100);
                        } else {
                           /* CommonUtils.getUtilInstance().showToast(UploadPicActivity.this,
                                    getString(R.string.publish_theme_failure));*/
                        }

                    } catch (JSONException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            });
        }

        if (requestUrl.equals(CommonUrl.getThemeList))
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                  /*   try {
                       JSONObject gamesInfoObject = new JSONObject(result);
                        Log.d("gaolei", "result--------getThemeList--------" + result);

                        List<ThemeObject> themeList = new Gson().fromJson(
                                gamesInfoObject.getString("themeInfoList"),
                                new TypeToken<List<ThemeObject>>() {
                                }.getType());

                        ThemeListViewAdapter
                                themeListViewAdapter = new ThemeListViewAdapter(themeList,
                                uploadImgUrlList, UploadPicActivity.this);
                        theme_listview.setAdapter(themeListViewAdapter);
                        hideAddThemeLayout();
                    } catch (JSONException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }*/
                }
            });
    }

    @Override
    public void onDismissBigPhoto() {
        hideDisplayBigImageLayout();
    }

    private void hideDisplayBigImageLayout() {
        display_big_image_layout.setVisibility(View.GONE);
        isBigImageShow = false;
    }

    @Override
    public void exception(IOException e, String requestUrl) {
        Log.d("gaolei", "exception--------------------------" + e.getMessage());
    }
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        super.onKeyDown(keyCode, event);
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            if(isShowUploadPic){
                show_upload_pic_layout.setVisibility(View.GONE);
                upload.setVisibility(View.VISIBLE);
                title.setText(getResources().getString(R.string.upload_pic));
                theme_title_edit.setText("");
                theme_desc_edit.setText("");
                addPictureList.clear();addPictureList.add(getResources().getDrawable(
                        R.mipmap.theme_add_picture_icon));
                imageAddGridViewAdapter.changeList(addPictureList);
                imageAddGridViewAdapter.notifyDataSetChanged();
                isShowUploadPic=false;
                return false;
            }  if(isBigImageShow){
                hideDisplayBigImageLayout();
                return false;
            }
        }
        finish();
        return false;
    }
}
