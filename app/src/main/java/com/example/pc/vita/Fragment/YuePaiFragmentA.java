package com.example.pc.vita.Fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pc.vita.Activity.ModelActivity;
import com.example.pc.vita.Activity.PhotographerActivity;
import com.example.pc.vita.Activity.activityActivity;
import com.example.pc.vita.Adapter.CardAdapter;
import com.example.pc.vita.R;
import com.example.pc.vita.Util.Utils;
import com.example.pc.vita.View.Custom.CardView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pc on 2016/7/13.
 */
public class YuePaiFragmentA extends Fragment implements CardView.OnCardClickListener {

    private Activity yuepai;
    List<String> list;
    private YuePaiDetailFragmentA frag;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        yuepai=this.getActivity();
        View view = inflater.inflate(R.layout.fragment_yue_pai_a, container, false);
        initUI(view);

        return view;
    }

    private void initUI(View view) {

        //先把卡片绑定
        CardView cardView = (CardView) view.findViewById(R.id.cardView1);
        cardView.setOnCardClickListener(this);
        cardView.setItemSpace(Utils.convertDpToPixelInt(yuepai, 20));

        //再设置好适配器（这里建了内部类来做适配器）
        MyCardAdapter adapter = new MyCardAdapter(yuepai);
        adapter.addAll(initData());
        cardView.setAdapter(adapter);

        FragmentManager manager = getActivity().getSupportFragmentManager();
        frag = new YuePaiDetailFragmentA();
        manager.beginTransaction().add(R.id.contentView, frag).commit();
    }


    //点中某张卡片后
    @Override
    public void onCardClick(final View view, final int position) {
        //Toast.makeText(MainActivity.this, position + "", Toast.LENGTH_SHORT).show();
        Bundle bundle = new Bundle();
        bundle.putString("text", list.get(position%list.size()));
        frag.show(view,bundle);
    }



    private List<String> initData() {
        list = new ArrayList<String>();

        list.add("a");
        list.add("b");
        list.add("c");
        list.add("d");
        list.add("e");

        list.add("f");
        list.add("g");
        list.add("h");
        list.add("i");
        list.add("j");

        return list;
    }

    public class MyCardAdapter extends CardAdapter<String> {

        public MyCardAdapter(Context context) {
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
            TextView tv = (TextView) convertView.findViewById(R.id.textView1);
            String text = getItem(position%list.size());
            tv.setText(text);
            return convertView;
        }
    }

}
