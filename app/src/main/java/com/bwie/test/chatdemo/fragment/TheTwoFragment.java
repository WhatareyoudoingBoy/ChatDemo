package com.bwie.test.chatdemo.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.bwie.test.chatdemo.R;
import com.bwie.test.chatdemo.adapter.TwoRecyclerAdapter;
import com.bwie.test.chatdemo.base.BaseMvpFragment;
import com.bwie.test.chatdemo.bean.FriendlistBean;
import com.bwie.test.chatdemo.mpersenter.TwoPersenter;
import com.bwie.test.chatdemo.mview.TwoView;
import com.google.gson.Gson;

import java.util.List;

/**
 * 作者： 武星宇
 * 日期： 2017/7/14.
 * 类用途：
 */

public class TheTwoFragment extends BaseMvpFragment<TwoView,TwoPersenter> implements TwoView {

  private RecyclerView recy;
  private SwipeRefreshLayout swipe;

  @Override
  public TwoPersenter initPersenter() {
    return new TwoPersenter();
  }

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.twomain, null);
    initView(view); //加载控件
    return view;
  }

  private void initView(View view) {
    View twoTitle = (View) view.findViewById(R.id.two_title);
    TextView raghtTitle = (TextView)twoTitle.findViewById(R.id.raght_title);
    raghtTitle.setText("好友列表");
    recy = (RecyclerView) view.findViewById(R.id.two_rv); //列表展示
    persenter.getFriendList();
    recy.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));
    swipe = (SwipeRefreshLayout)view.findViewById(R.id.two_swipe);

    //刷新
    swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
      @Override
      public void onRefresh() {
        persenter.getFriendList();

        swipe.setRefreshing(false);

      }
    });
  }

  @Override
  public void requstSuccess(String result) {

    if(result!=null){
      Gson gson = new Gson();
      FriendlistBean friendlistBean = gson.fromJson(result, FriendlistBean.class);
      List< FriendlistBean.DataBean > data = friendlistBean.getData();
      int result_code = friendlistBean.getResult_code();
      if(result_code == 200){


        TwoRecyclerAdapter twoRecyclerAdapter = new TwoRecyclerAdapter(getActivity(), data);
        twoRecyclerAdapter.notifyDataSetChanged();
        recy.setAdapter(twoRecyclerAdapter);


      }
    }

  }

  @Override
  public void requestFailed(int code) {
  }
}
