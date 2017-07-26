package com.bwie.test.chatdemo.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.bwie.test.chatdemo.R;
import com.bwie.test.chatdemo.bean.FriendlistBean;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * 作者： 武星宇
 * 日期： 2017/7/14.
 * 类用途：
 */

public class TwoRecyclerAdapter extends RecyclerView.Adapter<TwoRecyclerAdapter.TwoHolder>{
  Context context;
  List< FriendlistBean.DataBean > data;

  public TwoRecyclerAdapter(Context context,List< FriendlistBean.DataBean > data){
    this.context  = context;
    this.data = data;
  }

  @Override
  public TwoHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(context).inflate(R.layout.tworvitem, null);
    return new TwoHolder(view);
  }

  @Override
  public void onBindViewHolder(TwoHolder holder, int position) {

    if(data.get(position).getImagePath()!=null){
      Picasso.with(context).load(data.get(position).getImagePath()).into(holder.face);
    }

    holder.name.setText(data.get(position).getNickname());
    holder.sex.setText(data.get(position).getGender());
    holder.agee.setText(data.get(position).getAge());
    holder.jieshaoa.setText(data.get(position).getIntroduce());



  }

  @Override
  public int getItemCount() {
    return data == null?0:data.size();
  }

  class TwoHolder extends RecyclerView.ViewHolder{

    ImageView face;
    TextView name,sex,agee,jieshaoa;
    public TwoHolder(View itemView) {
      super(itemView);
      face = (ImageView) itemView.findViewById(R.id.twoadapter_iv);
      name = (TextView) itemView.findViewById(R.id.twoadater_mingzi);
      sex = (TextView) itemView.findViewById(R.id.twoadapter_sex);
      agee = (TextView) itemView.findViewById(R.id.twoadapter_phone);
      jieshaoa = (TextView) itemView.findViewById(R.id.twoadapter_jianjie);

    }
  }
}
