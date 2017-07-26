package com.bwie.test.chatdemo.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


import com.bwie.test.chatdemo.R;
import com.bwie.test.chatdemo.bean.InfoUserBean;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者： 武星宇
 * 日期： 2017/7/13.
 * 类用途：
 */

public class UserInfoRvAdapter extends RecyclerView.Adapter<UserInfoRvAdapter.MyUserInfoAdapter> implements View.OnClickListener {
  Context context;
  List< InfoUserBean.DataBean.PhotolistBean > list;
  OnItemClickListener photoEventListener = null;

  public UserInfoRvAdapter(Context context,List< InfoUserBean.DataBean.PhotolistBean > photolist){
    this.context = context;
    this.list = photolist;
  }

  @Override
  public void onClick(View v) {
    if(photoEventListener != null){

      photoEventListener.onClickPhoto(v,(String) v.getTag());

    }
  }


  public interface OnItemClickListener{
    void onClickPhoto(View v,String position);
  }

  public  void setOnItemClickListener(OnItemClickListener photoEventListener){
 this.photoEventListener = photoEventListener;
  }

  @Override
  public MyUserInfoAdapter onCreateViewHolder(ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(context).inflate(R.layout.userinfophoto, null);
    view.setOnClickListener(this);
    return new MyUserInfoAdapter(view);
  }

  @Override
  public void onBindViewHolder(MyUserInfoAdapter holder, int position) {




     Picasso.with(context).load(list.get(position).getImagePath()).into(holder.iv);

     holder.itemView.setTag(list.get(position).getImagePath());

  }

  @Override
  public int getItemCount() {
    return list == null? 0:list.size();
  }

  class MyUserInfoAdapter extends RecyclerView.ViewHolder{
  private ImageView iv;
    public MyUserInfoAdapter(View itemView) {
      super(itemView);
      iv = (ImageView) itemView.findViewById(R.id.userinfo_phonto);
    }
  }
}
