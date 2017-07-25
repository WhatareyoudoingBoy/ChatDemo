package com.bwie.test.chatdemo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.bwie.test.chatdemo.R;

/**
 * 作者： 武星宇
 * 日期： 2017/7/24.
 * 类用途：
 */

public class MoreFunctionGridAdapter extends BaseAdapter {

  Context context;
  public MoreFunctionGridAdapter(Context context){
    this.context = context;
  }

  @Override
  public int getCount() {
    return 1;
  }

  @Override
  public Object getItem(int position) {
    return null;
  }

  @Override
  public long getItemId(int position) {
    return 0;
  }

  @Override
  public View getView(int position, View convertView, ViewGroup parent) {

    if(convertView == null){
      convertView = LayoutInflater.from(context).inflate(R.layout.addfunctioingrid,null);
    }



    return convertView;
  }
}
