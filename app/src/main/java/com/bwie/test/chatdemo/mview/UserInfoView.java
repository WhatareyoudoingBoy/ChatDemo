package com.bwie.test.chatdemo.mview;

/**
 * 作者： 武星宇
 * 日期： 2017/7/12.
 * 类用途：
 */

public interface UserInfoView {


  void onUserbyidSuccess(String result);
  void onUserbyidFailed(int code);

  void addFriendSuccess(String result);
  void addFriendFailed(int code);
}
