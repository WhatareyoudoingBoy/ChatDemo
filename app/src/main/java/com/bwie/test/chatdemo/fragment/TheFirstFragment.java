package com.bwie.test.chatdemo.fragment;

import android.content.Intent;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import android.widget.TextView;
import android.widget.Toast;

import com.bwie.test.chatdemo.MyTost;

import com.bwie.test.chatdemo.R;
import com.bwie.test.chatdemo.activitys.UserInfoActivity;
import com.bwie.test.chatdemo.adapter.FirstRecyclerAdapter;
import com.bwie.test.chatdemo.base.BaseMvpFragment;
import com.bwie.test.chatdemo.bean.NearbyPeople;

import com.bwie.test.chatdemo.mpersenter.FirstPersenter;
import com.bwie.test.chatdemo.mview.FirstView;

import com.google.gson.Gson;


import java.util.List;

/**
 * 作者： 武星宇
 * 日期： 2017/7/11.
 * 类用途：
 * Log.e("first",info);
 */

public class TheFirstFragment extends BaseMvpFragment< FirstView, FirstPersenter > implements FirstView {

  private RecyclerView recyclerView;
  private NearbyPeople userInfoBean;
  private SwipeRefreshLayout swipeRefresh;
  private boolean isload;
  private ImageView layoutbtn;
  private LinearLayoutManager linearLayoutManager;
  private FirstRecyclerAdapter firstRecyclerAdapter;
  private StaggeredGridLayoutManager staggeredGridLayoutManager;
  private View titleinfo;
  private ImageView face;


  @Override
  public FirstPersenter initPersenter() {
    return new FirstPersenter();
  }

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_first, null);
    initView(view);//加载控件
    return view;
  }

  private void initView(View view) {
    recyclerView = (RecyclerView) view.findViewById(R.id.first_recyclerview);
    layoutbtn = (ImageView) view.findViewById(R.id.first_btn);
    swipeRefresh = (SwipeRefreshLayout) view.findViewById(R.id.first_swiperefresh);
    titleinfo = (View) view.findViewById(R.id.firsttitle);
    persenter.getEveryone();
    firstRecyclerAdapter = new FirstRecyclerAdapter(getActivity());
    face = (ImageView) titleinfo.findViewById(R.id.title_face);
    TextView title = (TextView)titleinfo.findViewById(R.id.raght_title);
    title.setText("附近的人");


  }

  public void toLinearLayoutManager() {
    if (linearLayoutManager == null) {
      linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
    }
    firstRecyclerAdapter.dataChange(1);
    recyclerView.setLayoutManager(linearLayoutManager);
    recyclerView.setAdapter(firstRecyclerAdapter);

  }

  public void toStaggeredGridLayoutManager(){
    if(staggeredGridLayoutManager == null){
      staggeredGridLayoutManager = new StaggeredGridLayoutManager(3,StaggeredGridLayoutManager.VERTICAL);
    }
    firstRecyclerAdapter.dataChange(2);
    recyclerView.setLayoutManager(staggeredGridLayoutManager);
    recyclerView.setAdapter(firstRecyclerAdapter);

  }

  /**
   * 刷新数据
   */
  public void loadDataOrRefreshData() {
    swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
      @Override
      public void onRefresh() {



        persenter.getEveryone();
        swipeRefresh.setRefreshing(false);

      }
    });


  }

  @Override
  public void onSuccess(String result) {

    Gson gson = new Gson();
    userInfoBean = gson.fromJson(result, NearbyPeople.class);
    int result_code = userInfoBean.getResult_code();
    final List< NearbyPeople.DataBean > data = userInfoBean.getData();

    if (result_code == 200) {



      toLinearLayoutManager();
      firstRecyclerAdapter.setData(data, 0);
      layoutbtn.setTag(1);
      layoutbtn.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
          int tag = (int) v.getTag() ;
          if(tag == 1){
            layoutbtn.setTag(2);
            toStaggeredGridLayoutManager();

          } else {
            layoutbtn.setTag(1);
            toLinearLayoutManager();
          }
        }
      });
      recyclerView.setAdapter(firstRecyclerAdapter);
      loadDataOrRefreshData();
      recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
          super.onScrollStateChanged(recyclerView, newState);
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
          super.onScrolled(recyclerView, dx, dy);
          int lastVisibleItemPosition = linearLayoutManager.findLastVisibleItemPosition();

          if (lastVisibleItemPosition + 1 == firstRecyclerAdapter.getItemCount()) {
            MyTost.makeText(getActivity(), "本页最后一个条目，自动加载中..", Toast.LENGTH_SHORT);
            boolean refreshing = swipeRefresh.isRefreshing();
            if (refreshing) {
              firstRecyclerAdapter.notifyItemRemoved(firstRecyclerAdapter.getItemCount());
            } else {
              if (!isload) {
                isload = true;
                persenter.getEveryone();
                firstRecyclerAdapter.setData(data, 0);
                isload = false;
              }
            }
          }
        }
      });

      firstRecyclerAdapter.mOnclickEveryone(new FirstRecyclerAdapter.MSetonClicklistener() {
        @Override
        public void onclickevent(View v, int position) {
          Intent intent = new Intent(getActivity(), UserInfoActivity.class);
          intent.putExtra("userid",position+"");
          startActivity(intent);
        }




      });


    } else {
      onFailed(result_code);
    }


  }

  @Override
  public void onFailed(int code) {
    switch (code) {
      case 300:
        MyTost.makeText(getActivity(), "参数异常", Toast.LENGTH_SHORT);
        break;
      case 303:
        MyTost.makeText(getActivity(), "请前往注册账号", Toast.LENGTH_SHORT);
        break;
      case 500:
        MyTost.makeText(getActivity(), "服务器错误", Toast.LENGTH_SHORT);
        break;
      case 404:
        MyTost.makeText(getActivity(), "数据异常", Toast.LENGTH_SHORT);
        break;


    }
  }


}
