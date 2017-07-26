package com.bwie.test.chatdemo.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.amap.api.location.CoordinateConverter;
import com.amap.api.location.DPoint;

import com.bwie.test.chatdemo.R;
import com.bwie.test.chatdemo.bean.NearbyPeople;
import com.bwie.test.chatdemo.bean.UserInfoBean;

import com.bwie.test.chatdemo.utils.AMapUtils;
import com.bwie.test.chatdemo.utils.DeviceUtils;
import com.bwie.test.chatdemo.utils.DisanceUtils;
import com.bwie.test.chatdemo.utils.PreferencesUtils;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者： 武星宇
 * 日期： 2017/7/10.
 * 类用途：
 */

public class FirstRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener{

  Context context;
  private int itemWidth ;
  List< NearbyPeople.DataBean > mlist;
  private int tag = 1; // 1 先行布局 2 瀑布流
  public FirstRecyclerAdapter(Context context) {
    this.context  = context;
    //当前屏幕 的宽度 除以3
    itemWidth = DeviceUtils.getDisplayInfomation(context).x / 3 ;
  }
  /**
   * 2 瀑布流
   * 1 线性布局
   *
   * @param type
   */
  public void dataChange(int type) {
    this.tag = type;

  }
public MSetonClicklistener mSetonClicklistener;

  @Override
  public void onClick(View v) {
    if(mSetonClicklistener != null){

      mSetonClicklistener.onclickevent(v, (int) v.getTag());
    }

  }

 public  interface MSetonClicklistener{

    void onclickevent(View v,int position);
  }

  public void mOnclickEveryone(MSetonClicklistener mSetonClicklistener){
    this.mSetonClicklistener = mSetonClicklistener;
  }

  public void setData(List< NearbyPeople.DataBean > list,int page){

    if(mlist == null){
      mlist = new ArrayList<>();
    }
    if(page == 1){
      list.clear();
    }
    mlist.addAll(list);
    notifyDataSetChanged();
  }


  @Override
  public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


    if(viewType == 0){
         View view = LayoutInflater.from(context).inflate(R.layout.first_adapter_item,parent, false);
        view.setOnClickListener(this);
      return new UserInfo(view);
    }else {
      View viewa = LayoutInflater.from(context).inflate(R.layout.first_adapter_item_1,parent, false);
      viewa.setOnClickListener(this);
      return new MuserInfo(viewa);
    }




  }


  @Override
  public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    if(holder instanceof UserInfo){
      UserInfo userinfoholder =(UserInfo) holder;
      if(mlist.get(position).getImagePath() != null) {
        Picasso.with(context).load(mlist.get(position).getImagePath()).into(userinfoholder.iv);
      }
      userinfoholder.name.setText(mlist.get(position).getNickname());
      userinfoholder.xingbie.setText(mlist.get(position).getGender());
      userinfoholder.dianhua.setText(mlist.get(position).getAge()+" 岁");
      userinfoholder.jianjiea.setText(mlist.get(position).getIntroduce());
      userinfoholder.itemView.setTag(mlist.get(position).getUserId());



      String lat =  PreferencesUtils.getValueByKey(context, AMapUtils.LAT,"");
      String lng = PreferencesUtils.getValueByKey(context,AMapUtils.LNG,"");

      double olat = mlist.get(position).getLat();
      double olng = mlist.get(position).getLng();

      if(!TextUtils.isEmpty(lat) && !TextUtils.isEmpty(lng) && olat != 0.0 && olng != 0.0){
        double dlat = Double.valueOf(lat);
        double dlng = Double.valueOf(lng);

        DPoint dPoint = new DPoint(dlat,dlng);
        DPoint oPoint = new DPoint(olat,olng);

        float dis = CoordinateConverter.calculateLineDistance(dPoint, oPoint);
        Log.d("FirstRecyclerAdapter", DisanceUtils.standedDistance(dis));
      }
    }else {

      MuserInfo userInfoholder = (MuserInfo) holder;
      if(mlist.get(position).getImagePath() != null) {
        Picasso.with(context).load(mlist.get(position).getImagePath()).into(userInfoholder.faceImage);
      }

      userInfoholder.nickName.setText(mlist.get(position).getNickname());
      userInfoholder.sexy.setText(mlist.get(position).getGender());
      userInfoholder.age.setText(mlist.get(position).getAge()+" 岁");
      userInfoholder.jieshao.setText(mlist.get(position).getIntroduce());
      userInfoholder.itemView.setTag(mlist.get(position).getUserId());





    }

  }
  @Override
  public int getItemViewType(int position) {
    if (tag == 1) {
      return 0;
    } else {
      return 1;
    }
  }

  @Override
  public int getItemCount() {
    return mlist!=null?mlist.size():0;
  }

  class UserInfo extends RecyclerView.ViewHolder{

    ImageView iv;
    TextView name,jianjiea,xingbie,dianhua,juli;
    public UserInfo(View itemView) {
      super(itemView);
     iv = (ImageView) itemView.findViewById(R.id.firstadapter_iv);
      xingbie = (TextView) itemView.findViewById(R.id.firstadapter_sex);
      jianjiea = (TextView) itemView.findViewById(R.id.firstadapter_jianjie);
      dianhua = (TextView) itemView.findViewById(R.id.firstadapter_phone);
      name = (TextView) itemView.findViewById(R.id.firstadater_mingzi);

    }
  }

  class MuserInfo extends RecyclerView.ViewHolder{

   ImageView faceImage;
   TextView nickName,sexy,age,jieshao;
    public MuserInfo(View itemView) {
      super(itemView);

      faceImage = (ImageView) itemView.findViewById(R.id.infoo_iv);
      nickName = (TextView) itemView.findViewById(R.id.infoo_name);
      sexy = (TextView) itemView.findViewById(R.id.infoo_sexy);
      age = (TextView) itemView.findViewById(R.id.infoo_age);
      jieshao = (TextView) itemView.findViewById(R.id.infoo_jianjie);

    }
  }



}
