package com.bwie.test.chatdemo.live;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bwie.test.chatdemo.R;
import com.bwie.test.chatdemo.bean.GuanZhongBean;

import java.util.List;





public class Adapter extends BaseAdapter {
    List<GuanZhongBean.ListBean> list;
    Context context;
    private ImageView imag_id_id;
    private TextView name;

    public Adapter(List<GuanZhongBean.ListBean> list, Context context){
        this.list=list;
        this.context=context;

    }
    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView==null){
            convertView=View.inflate(context, R.layout.list_item_,null);
            imag_id_id = (ImageView) convertView.findViewById(R.id.imag_id_id);
            name = (TextView) convertView.findViewById(R.id.name_name);

          name.setText(list.get(position).getNickName());

            Glide.with(context).load(list.get(position).getLivepic()).error(R.mipmap.thumbnail).into(imag_id_id);
        }


        return convertView;
    }
}
