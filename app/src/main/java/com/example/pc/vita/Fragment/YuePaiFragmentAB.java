package com.example.pc.vita.Fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.pc.vita.Adapter.CardAdapter;
import com.example.pc.vita.Data.Model.YuePaiDataModel;
import com.example.pc.vita.R;
import com.example.pc.vita.View.Custom.CardView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 约拍子界面A（二级页面，约拍模特）
 * Created by pc on 2016/7/13.
 */
public class YuePaiFragmentAB extends Fragment implements CardView.OnCardClickListener {

    private Activity yuepai;
    List<YuePaiDataModel> yuepaiDatalist;
    private YuePaiDetailFragmentA frag;
    private View navibar;
    private int eventflag=0;//标识出此fragment的事件类型 1.约模特 2.约摄影师
    private ImageButton btn_previous;
    private ImageButton btn_next;
    private CardView cardView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        yuepai=this.getActivity();
        View view = inflater.inflate(R.layout.fragment_yue_pai_a, container, false);
        initUI(view);

        navibar=yuepai.findViewById(R.id.fragment_list);
        navibar.setVisibility(View.GONE);


        btn_previous=(ImageButton)view.findViewById(R.id.btn_previouscard);
        btn_previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cardView.goUp();
            }
        });


        btn_next=(ImageButton)view.findViewById(R.id.btn_nextcard);
        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(yuepai, "clicked uobutton", Toast.LENGTH_SHORT).show();
                cardView.goDown();
            }
        });


        return view;
    }

    private void initUI(View view) {

        //先把卡片绑定
        cardView = (CardView) view.findViewById(R.id.yuepai_cards_a);
        cardView.setOnCardClickListener(this);
        cardView.setItemSpace(20);
        //再设置好适配器（这里建了内部类来做适配器）
        YuePaiCardAdapter adapter = new YuePaiCardAdapter(yuepai);
        adapter.addAll(initData());
        cardView.setAdapter(adapter);

        FragmentManager manager = getActivity().getSupportFragmentManager();
        frag = new YuePaiDetailFragmentA();
        manager.beginTransaction().add(R.id.contentView, frag).commit();

       /* ViewTreeObserver vto = cardView.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                cardView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                float height = cardView.getHeight();
                //cardView.setItemSpace((int)Utils.convertPixelsToDp(yuepai, height));
                cardView.setItemSpace(200);
                //再设置好适配器（这里建了内部类来做适配器）
                YuePaiCardAdapter adapter = new YuePaiCardAdapter(yuepai);
                adapter.addAll(initData());
                cardView.setAdapter(adapter);

                FragmentManager manager = getActivity().getSupportFragmentManager();
                frag = new YuePaiDetailFragmentA();
                manager.beginTransaction().add(R.id.contentView, frag).commit();
            }
        });*/



    }


    //点中某张卡片后
    @Override
    public void onCardClick(final View view, final int position) {
        //Toast.makeText(MainActivity.this, position + "", Toast.LENGTH_SHORT).show();
      //  Bundle bundle = new Bundle();
       // bundle.putString("text", yuepaiDatalist.get(position % yuepaiDatalist.size()));
        frag.show(view);
    }



    private List<YuePaiDataModel> initData() {
        yuepaiDatalist = new ArrayList<YuePaiDataModel>();
        //模拟从网络获取数据
        for (int i=0;i<10;i++)
        {
            YuePaiDataModel model=new YuePaiDataModel();
            model.setUsername("用户" + i);
            model.setIntroduce("这是活动"+i+"的描述不能少于十五字不能多于一百五十字但是点开之前只能显示两行");
            model.setStartTime(new Date());
            model.setLocation("这是活动"+i+"的活动地点");
            yuepaiDatalist.add(model);
        }





        return yuepaiDatalist;
    }

    public void setEventflag(int eventflag) {
        this.eventflag = eventflag;
    }


    public class YuePaiCardAdapter extends CardAdapter<YuePaiDataModel> {

        public int xx=2;

        public YuePaiCardAdapter(Context context) {
            super(context);
        }

        @Override
        public int getCount() {
            return Integer.MAX_VALUE;
        }

        @Override
        protected View getCardView(int position,
                                   View convertView, ViewGroup parent) {
            if(convertView == null) {
                LayoutInflater inflater = LayoutInflater.from(yuepai);
                convertView = inflater.inflate(R.layout.item_layout, parent, false);
            }

            ImageView cardPic=(ImageView)convertView.findViewById(R.id.yuepai_card_pic);
            TextView username = (TextView) convertView.findViewById(R.id.yuepai_username);
            TextView introduce = (TextView) convertView.findViewById(R.id.yuepai_introduce);
            TextView time = (TextView) convertView.findViewById(R.id.yuepai_time);
            TextView location = (TextView) convertView.findViewById(R.id.yuepai_location);

            YuePaiDataModel model=getItem(position % yuepaiDatalist.size());
           //这里用Glide加载一个imgview，待写
            username.setText(model.getUsername());
            introduce.setText(model.getIntroduce());
            time.setText(model.getStartTime().toString());
            location.setText(model.getLocation());
            return convertView;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        navibar.setVisibility(View.VISIBLE);
    }

}