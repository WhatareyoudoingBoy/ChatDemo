package com.bwie.test.chatdemo.mview;

/**
 * 作者： 武星宇
 * 日期： 2017/7/10.
 * 类用途：
 */

public interface FirstView {

  //数据返回成功
  void onSuccess(String result);
  void onFailed(int code);
}
