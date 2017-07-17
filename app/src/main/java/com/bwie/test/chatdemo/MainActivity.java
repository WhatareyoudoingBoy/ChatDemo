package com.bwie.test.chatdemo;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.bwie.test.chatdemo.activitys.Login;
import com.bwie.test.chatdemo.activitys.Registed;
import com.bwie.test.chatdemo.base.BaseActivity;

public class MainActivity extends BaseActivity {


  private ImageView heart;

  private AnimationDrawable animationDrawable;
  /**
   * 倒计时
   */

  private CountDownTimer timer = new CountDownTimer(3000,1000) {
    @Override
    public void onTick(long millisUntilFinished) {


    }

    @Override
    public void onFinish() {

      finish();
      animationDrawable.stop();
      Intent intent = new Intent(MainActivity.this,Login.class);

      startActivity(intent);
    }
  };


  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    heart = (ImageView) findViewById(R.id.heart);

    animationDrawable = (AnimationDrawable) heart.getDrawable();
    animationDrawable.start();


//这里以ACCESS_COARSE_LOCATION为例
    if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
            != PackageManager.PERMISSION_GRANTED) {
      //申请WRITE_EXTERNAL_STORAGE权限
      ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
             0);//自定义的code
    }




    timer.start(); //开启
  }

  @Override
  public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
    super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    //可在此继续其他操作。

    if(requestCode==0){


    }

  }

}
