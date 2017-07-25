package com.bwie.test.chatdemo.activitys;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;


import com.bwie.test.chatdemo.MyApplication;
import com.bwie.test.chatdemo.R;
import com.bwie.test.chatdemo.base.IActivity;

import com.bwie.test.chatdemo.fragment.TheFirstFragment;
import com.bwie.test.chatdemo.fragment.TheTwoFragment;
import com.bwie.test.chatdemo.witgh.ButtomLayout;
import com.hyphenate.chat.EMClient;

import java.util.ArrayList;
import java.util.List;


/**
 * 主导航
 */

public class TabActivity extends IActivity implements ButtomLayout.OnSelectListener {

  private ButtomLayout buttomLayout;
  private FragmentManager fragmentManager;

  private List< Fragment > fragments = new ArrayList< Fragment >();
  private CallReceiver callReceiver;

  private class CallReceiver extends BroadcastReceiver {


    @Override
    public void onReceive(Context context, Intent intent) {
      // 拨打方username
      String from = intent.getStringExtra("from");
      // call type
      String type = intent.getStringExtra("type");
      //跳转到通话页面
      MyApplication.ring();
      VideoActivity.startTelActivity(2, from, TabActivity.this);


    }

  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_tab);
    fragmentManager = getSupportFragmentManager();
    createFragment(savedInstanceState);
    buttomLayout = (ButtomLayout) findViewById(R.id.buttom_layout);
    buttomLayout.setOnSelectListener(this);
    switchFragment(0);
    incoming();
  }

  public void incoming() {
    callReceiver = new CallReceiver();
    IntentFilter callFilter = new IntentFilter(EMClient.getInstance().callManager().getIncomingCallBroadcastAction());
    registerReceiver(callReceiver, callFilter);

  }



  public void createFragment(Bundle savedInstanceState) {
    TheFirstFragment firstFragment = (TheFirstFragment) fragmentManager.findFragmentByTag("FirstFragment");
    TheTwoFragment secondFragment = (TheTwoFragment) fragmentManager.findFragmentByTag("SecondFragment");
//        ThirdFragment thirdFragment = (ThirdFragment) fragmentManager.findFragmentByTag("ThirdFragment");
//        FourthFragment fourthFragment = (FourthFragment) fragmentManager.findFragmentByTag("FourthFragment");
    if (firstFragment == null) {
      firstFragment = new TheFirstFragment();
    }
    if (secondFragment == null) {
      secondFragment = new TheTwoFragment();
    }
//        if(thirdFragment == null){
//            thirdFragment = new ThirdFragment();
//        }
//        if(fourthFragment == null){
//            fourthFragment = new FourthFragment();
//        }
    fragments.add(firstFragment);
    fragments.add(secondFragment);
//        fragments.add(thirdFragment);
//        fragments.add(fourthFragment);
  }



  public void switchFragment(int pos) {
    switchIFragment(pos, fragments, R.id.container);

  }


  /**
   * 底部导航 点击 回调
   *
   * @param index
   */
  @Override
  public void onSelect(int index) {
    switchFragment(index);
  }
}
