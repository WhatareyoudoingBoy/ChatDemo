package com.bwie.test.chatdemo.mmodel;

/**
 * 作者： 武星宇
 * 日期： 2017/7/14.
 * 类用途：
 */

public interface TwoModel {

  void friendData(FriendInfo friendInfo);

  interface FriendInfo{
    void friendSueecss(String result);
    void friendFailed(int code);
  }

}
