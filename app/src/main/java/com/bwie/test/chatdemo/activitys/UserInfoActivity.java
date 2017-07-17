package com.bwie.test.chatdemo.activitys;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bwie.test.chatdemo.R;
import com.bwie.test.chatdemo.adapter.UserInfoRvAdapter;
import com.bwie.test.chatdemo.base.BaseMvpActivity;
import com.bwie.test.chatdemo.bean.FriendBean;
import com.bwie.test.chatdemo.bean.InfoUserBean;
import com.bwie.test.chatdemo.mpersenter.UserInfoPersenter;
import com.bwie.test.chatdemo.mview.UserInfoView;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;


import java.util.List;

public class UserInfoActivity extends BaseMvpActivity< UserInfoView, UserInfoPersenter > implements UserInfoView {

  private ImageView iamgeFace;
  private TextView areaUser;
  private RecyclerView recyclerview;
  private TextView touxiang;
  private TextView nickname;
  private TextView guanxi;
  private Button addFriend;
  private String userid;
  private TextView jianjie;
  private TextView lasttime;
  private Button sendMsg;

  /**
   * 0 非好友
   * 1  好友
   *
   * @return
   */

  @Override
  public UserInfoPersenter initPersenter() {
    return new UserInfoPersenter();
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_user_info);
    userid = getIntent().getStringExtra("userid");
    persenter.getUserInfo(userid);
    initView(); //加载


  }

  private void initView() {
    iamgeFace = (ImageView) findViewById(R.id.userinfo_face); // 图片
    areaUser = (TextView) findViewById(R.id.userinfo_diqu); //地区
    recyclerview = (RecyclerView) findViewById(R.id.userinfo_rv); //图片集
    touxiang = (TextView) findViewById(R.id.userinfo_sexy);   //性别
    nickname = (TextView) findViewById(R.id.userinfo_nickname); //昵称
    guanxi = (TextView) findViewById(R.id.userinfo_relation);//年龄
    addFriend = (Button) findViewById(R.id.btn_addfriend); //打招呼按钮
    jianjie = (TextView) findViewById(R.id.userinfo_jianjie);//简介
    lasttime = (TextView) findViewById(R.id.userinfo_lasttiem); //最后上线时间
    sendMsg = (Button) findViewById(R.id.btn_sendmsgtofriend);//发送消息按钮
    /**
     * 添加好友
     */
    addFriend.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        persenter.addUserFriend(userid);

      }
    });


  }

  @Override
  public void onUserbyidSuccess(String result) {
    Gson gson = new Gson();
    InfoUserBean infoUserBean = gson.fromJson(result, InfoUserBean.class);
    InfoUserBean.DataBean data = infoUserBean.getData();
    int result_code1 = infoUserBean.getResult_code();
    int relation = data.getRelation();
    if (relation == 1) {
      addFriend.setVisibility(View.INVISIBLE);
      sendMsg.setVisibility(View.VISIBLE);
      guanxi.setText("好友");
      guanxi.setTextColor(Color.GREEN);
    } else {
      sendMsg.setVisibility(View.INVISIBLE);
      addFriend.setVisibility(View.VISIBLE);
      guanxi.setText("陌生人");
      guanxi.setTextColor(Color.RED);
    }
    if (result_code1 == 200) {
      List< InfoUserBean.DataBean.PhotolistBean > photolist = data.getPhotolist();
      Picasso.with(this).load(data.getImagePath()).into(iamgeFace);
      nickname.setText(data.getNickname());
      areaUser.setText("地区 :  " + data.getArea());
      touxiang.setText("性别 :  " + data.getGender());
      jianjie.setText("个人签名： " + data.getIntroduce());
      UserInfoRvAdapter userInfoRvAdapter = new UserInfoRvAdapter(UserInfoActivity.this, photolist);
      recyclerview.setLayoutManager(new LinearLayoutManager(UserInfoActivity.this, LinearLayoutManager.HORIZONTAL, false));
      userInfoRvAdapter.setOnItemClickListener(new UserInfoRvAdapter.OnItemClickListener() {
        @Override
        public void onClickPhoto(View v, String position) {
          Intent intent = new Intent(UserInfoActivity.this, PhotoActivity.class);
          intent.putExtra("photoPath", position);
          startActivity(intent);
        }
      });
      recyclerview.setAdapter(userInfoRvAdapter);

    } else {
      int result_code = infoUserBean.getResult_code();
      onUserbyidFailed(result_code);
    }


  }

  @Override
  public void onUserbyidFailed(int code) {
    switch (code) {
    }
  }

  @Override
  public void addFriendSuccess(String result) {
    Gson gson = new Gson();
    FriendBean friendBean = gson.fromJson(result, FriendBean.class);
    int result_code = friendBean.getResult_code();
    FriendBean.AddUserBean addUser = friendBean.getAddUser();
    int relation = addUser.getRelation();
    if (relation == 1) {
      addFriend.setVisibility(View.INVISIBLE);
      sendMsg.setVisibility(View.VISIBLE);
      guanxi.setText("好友");
      guanxi.setTextColor(Color.GREEN);
    } else {
      sendMsg.setVisibility(View.INVISIBLE);
      addFriend.setVisibility(View.VISIBLE);
      guanxi.setText("陌生人");
      guanxi.setTextColor(Color.RED);
    }
    if (result_code == 200) {
      Toast.makeText(UserInfoActivity.this, "添加好友成功", Toast.LENGTH_SHORT).show();
      addFriend.setVisibility(View.INVISIBLE);
      sendMsg.setVisibility(View.VISIBLE);
      guanxi.setText("好友");
      guanxi.setTextColor(Color.GREEN);


    } else {
      addFriendFailed(result_code);
    }


  }

  @Override
  public void addFriendFailed(int code) {
  }
}
