package com.bwie.test.chatdemo.activitys;

import android.content.Intent;
import android.os.Bundle;

import android.support.annotation.Nullable;


import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


import com.bwie.test.chatdemo.R;
import com.bwie.test.chatdemo.base.BaseActivity;


/**
 * 作者： 武星宇
 * 日期： 2017/7/4.
 * 类用途：
 */

public class Registed extends BaseActivity {



  private View head;
  private TextView right_title;
  private View radioMan;
  private View radioWoman;
  private View registedSure;
  private View radioGroup;




  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.registedone);

    initView();//初始化控件

    anyoneClickEvent(); //控件的点击事件

  }

  private void anyoneClickEvent() {
    /**
     * 右上角登陆按钮
     */
    right_title.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {

        startActivity(new Intent(Registed.this,Login.class));
        finish();
      }
    });


    radioMan.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {

        if(v.isClickable()){
          registedSure.setEnabled(true);

        }

      }
    });


    radioWoman.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {

        if(v.isClickable()){
          registedSure.setEnabled(true);
        }

      }
    });
    /**
     * 确定按钮
     */
    registedSure.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {

        startActivity(new Intent(Registed.this,RegistedTwo.class));
      }
    });







  }

  private void initView() {

    head = (View) findViewById(R.id.head);
    right_title = (TextView) head.findViewById(R.id.right_title);
    right_title.setText("登陆");
    radioMan = findViewById(R.id.registed_man); //男
    radioWoman = findViewById(R.id.registed_women); //女
    registedSure = findViewById(R.id.registed_sure); //确认按钮
    radioGroup = findViewById(R.id.radioGroup); // man and woman


  }



}

