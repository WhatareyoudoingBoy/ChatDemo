package com.bwie.test.chatdemo.base;

import android.os.Bundle;

import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.bwie.test.chatdemo.R;

/**
 * 作者： 武星宇
 * 日期： 2017/7/4.
 * 类用途：
 */

public class BaseActivity extends AppCompatActivity implements View.OnClickListener{

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.baseactivity);




  }

  @Override
  public void onClick(View v) {
  }
}
