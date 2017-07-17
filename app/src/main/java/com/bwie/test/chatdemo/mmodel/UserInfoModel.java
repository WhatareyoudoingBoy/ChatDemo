package com.bwie.test.chatdemo.mmodel;

/**
 * 作者： 武星宇
 * 日期： 2017/7/12.
 * 类用途：
 */

public interface UserInfoModel  {

  void getUserInfoById(String id,Userinfobyid userinfobyid);

  void getFriend(String sid,FriendAdd add);

  interface Userinfobyid{
    void userinfoSuccess(String result);
    void userinfoFaild(int code);
  }

  interface FriendAdd{
    void addSuccess(String result);
    void addFailed(int code);
  }


}
