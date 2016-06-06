package com.example.pc.vita.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.pc.vita.Data.Model.PictureModel;
import com.example.pc.vita.R;
import com.example.pc.vita.Task.ImgTaskManager;

public class LatestFragment extends Fragment {


	ImageView imageView1;
	ImageView imageView2;
	ImageView imageView3;
    ImgTaskManager loader1;
	String url1 = "http://i1.sinaimg.cn/dy/deco/2013/0329/logo/LOGO_1x.png";
	String url2 = "http://mat1.gtimg.com/www/images/qq2012/qqLogoFilter.png";
	String url3 = "http://images.cnblogs.com/logo_small.gif";
	PictureModel pic1= new PictureModel(url1);
	PictureModel pic2= new PictureModel(url2);
	PictureModel pic3= new PictureModel(url2);

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		Toast.makeText(this.getActivity(),"最新鲜Fragment创建成功",Toast.LENGTH_SHORT).show();

		return  inflater.inflate(R.layout.fragment_latest, container, false);
	}
	 
   
 

@Override
public void onActivityCreated(Bundle savedInstanceState) {
	// TODO Auto-generated method stub
	   
	 setView(); 
	 setListener();
    loader1=new ImgTaskManager();
    loader1.loadImg(pic1,imageView1);
    loader1.loadImg(pic2,imageView2);
    loader1.loadImg(pic3,imageView3);
	loader1.loadImg(pic1,imageView2);
	/*可注入
	loader1.loadImg(new PictureModel("label"){
		@Override
		public void setSrc(Drawable src) {
			src=null;
		}
	},imageView2);*/
	super.onActivityCreated(savedInstanceState);
}




private void setListener() {
	// TODO Auto-generated method stub

}




private void setView() {
	imageView1 = (ImageView)getView().findViewById(R.id.exextor_img);
	imageView2 = (ImageView)getView().findViewById(R.id.exextor_img1);
	imageView3 = (ImageView)getView().findViewById(R.id.exextor_img2);
}
   
}
