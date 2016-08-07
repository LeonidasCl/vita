package com.example.pc.vita.Fragment;
import android.app.Activity;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ImageButton;
import android.widget.ListView;
import com.example.pc.vita.Adapter.RankItemAdapter;
import com.example.pc.vita.Data.Model.RankItemModel;
import com.example.pc.vita.R;
import java.util.ArrayList;
import java.util.List;


/**
 * licl 2016.7.18
 * 重写的排行榜fragment
 */
public class YuePaiFragmentD extends android.support.v4.app.Fragment{

    private Activity yuepai;

    final int GRAPHER_AND_POP = 0;
    final int GRAPHER_AND_STYLE = 1;
    final int MODEL_AND_POP = 2;
    final int MODEL_AND_STYLE = 3;

    private ImageButton button_grapher;
    private ImageButton button_model;
    private ImageButton button_pop;
    private ImageButton button_style;
    boolean isGrapher;//是否显示摄影师
    boolean isPop;//是否显示人气榜


    private SwipeRefreshLayout refreshLayout;
    private RankItemAdapter personAdapter;
    private ListView listView;
    private int bootCounter=0;
    private int maxRecords = 400;

    View rankView;

    public YuePaiFragmentD() {
        isGrapher=true;
        isPop=true;
    }

    public View getListView(){
        return listView;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        yuepai=this.getActivity();

        rankView = inflater.inflate(R.layout.fragment_rank, container, false);



        button_grapher = (ImageButton) rankView.findViewById(R.id.button_grapher);
        button_model = (ImageButton) rankView.findViewById(R.id.button_model);
        button_pop = (ImageButton) rankView.findViewById(R.id.button_pop);
        button_style = (ImageButton) rankView.findViewById(R.id.button_style);

        personAdapter = new RankItemAdapter(yuepai,bootData(GRAPHER_AND_POP));
        listView = (ListView) rankView.findViewById(R.id.rank_list);
        refreshLayout = (SwipeRefreshLayout) rankView.findViewById(R.id.swipe_refresh_layout);
        listView.setAdapter(personAdapter);

        onScrollListener();
        onRefreshListener();

        setListener();

        return rankView;
    }

    private void setListener(){
        button_grapher.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (isGrapher){
                }else {
                    isGrapher = true;
                    button_grapher.setImageResource(R.drawable.button_grapher_down);
                    button_model.setImageResource(R.drawable.button_model_up);

                    refreshLayout.setRefreshing(true);
                    bootCounter=0;
                    personAdapter.refresh(bootData(isPop?GRAPHER_AND_POP:GRAPHER_AND_STYLE));
                    personAdapter.notifyDataSetChanged();//直接调用BaseAdapter的notify
                    refreshLayout.setRefreshing(false);

                }
                return false;
            }
        });

        button_model.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (isGrapher) {
                    isGrapher = false;
                    button_grapher.setImageResource(R.drawable.button_grapher_up);
                    button_model.setImageResource(R.drawable.button_model_down);

                    refreshLayout.setRefreshing(true);
                    bootCounter=0;
                    personAdapter.refresh(bootData(isPop?MODEL_AND_POP:MODEL_AND_STYLE));
                    personAdapter.notifyDataSetChanged();
                    refreshLayout.setRefreshing(false);

                }
                return false;
            }
        });

        button_style.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (isPop) {
                    isPop = false;
                    button_pop.setImageResource(R.drawable.button_pop_up);
                    button_style.setImageResource(R.drawable.button_style_down);

                    refreshLayout.setRefreshing(true);
                    bootCounter=0;
                    personAdapter.refresh(bootData(isGrapher?GRAPHER_AND_STYLE:MODEL_AND_STYLE));
                    personAdapter.notifyDataSetChanged();
                    refreshLayout.setRefreshing(false);

                }
                return false;
            }
        });

        button_pop.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (!isPop) {
                    isPop = true;
                    button_pop.setImageResource(R.drawable.button_pop_down);
                    button_style.setImageResource(R.drawable.button_style_up);

                    refreshLayout.setRefreshing(true);
                    bootCounter=0;
                    personAdapter.refresh(bootData(isGrapher?GRAPHER_AND_POP:MODEL_AND_POP));
                    personAdapter.notifyDataSetChanged();
                    refreshLayout.setRefreshing(false);

                }
                return false;
            }
        });
    }
    private void onRefreshListener(){
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshLayout.setRefreshing(true);
                bootCounter = 0;
                personAdapter.refresh(bootData(isGrapher ? (isPop ? GRAPHER_AND_POP : GRAPHER_AND_STYLE) : (isPop ? MODEL_AND_POP : MODEL_AND_STYLE)));
                personAdapter.notifyDataSetChanged();
                refreshLayout.setRefreshing(false);
            }
        });
    }

    private void onScrollListener(){
        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int i) {

            }

            @Override
            public void onScroll(AbsListView absListView, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (firstVisibleItem + visibleItemCount > totalItemCount - 2 && totalItemCount < maxRecords) {
                    personAdapter.add(bootData(isGrapher ? (isPop ? GRAPHER_AND_POP : GRAPHER_AND_STYLE) : (isPop ? MODEL_AND_POP : MODEL_AND_STYLE)));
                    personAdapter.notifyDataSetChanged();
                }
            }
        });
    }

    private List<RankItemModel> bootData(int Type){
        List<RankItemModel> persons = new ArrayList<>();
        Resources res=getResources();
        for(int i=bootCounter;i<bootCounter+20;i++){
            RankItemModel person = new RankItemModel();
            person.setRank(i+1);
            person.setCommentNum((int)(Math.random()*1000)+1);
            person.setFavorNum((int)(Math.random()*1000)+1);
            person.setMainPicture(res.getDrawable(R.drawable.main_picture1));
            person.setUserNameText("user" + i);
            person.setUserAddressText(i + "addddddddddrrrrrrrrresssssssssss");
            person.setUserIamgeSrc(res.getDrawable(R.drawable.user_image));
            person.setComentPicture(res.getDrawable(R.drawable.comment_image));
            person.setPraisePicture(res.getDrawable(R.drawable.praise));
            persons.add(person);
        }
        bootCounter+=20;
        return persons;
    }
}
