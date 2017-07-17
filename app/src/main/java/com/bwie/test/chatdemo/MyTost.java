package com.bwie.test.chatdemo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 作者： 武星宇
 * 日期： 2017/7/5.
 * 类用途：
 */

public class MyTost {
  private  static Toast mToast;
  private static TextView textView;

  private MyTost(Context context, CharSequence text, int duration) {
    View v = LayoutInflater.from(context).inflate(R.layout.ctoast, null);
    textView = (TextView) v.findViewById(R.id.textView1);
    textView.setText(text);
    mToast = new Toast(context);
    mToast.setDuration(duration);
    mToast.setView(v);
  }


  public static void makeText(Context context, CharSequence text, int duration) {
    if(mToast == null){
      new MyTost(context, text, duration);
    }else {
      textView.setText(text);
      mToast.setDuration(duration);
    }
    mToast.show();

  }
}
