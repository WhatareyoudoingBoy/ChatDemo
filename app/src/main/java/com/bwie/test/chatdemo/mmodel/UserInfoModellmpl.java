package com.bwie.test.chatdemo.mmodel;

import com.bwie.test.chatdemo.core.JNICore;
import com.bwie.test.chatdemo.core.SortUtils;
import com.bwie.test.chatdemo.network.BaseObserver;
import com.bwie.test.chatdemo.network.RetrofitManager;

import java.util.HashMap;
import java.util.Map;

/**
 * 作者： 武星宇
 * 日期： 2017/7/12.
 * 类用途：
 */

public class UserInfoModellmpl implements UserInfoModel {




  @Override
  public void getUserInfoById(String id, final Userinfobyid userinfobyid) {

    Map<String,String> map = new HashMap<String,String>();
    map.put("user.userId",id);
    String sign =  JNICore.getSign(SortUtils.getMapResult(SortUtils.sortString(map))) ;
    map.put("user.sign",sign);


    RetrofitManager.get("http://qhb.2dyt.com/MyInterface/userAction_selectUserById.action", map, new BaseObserver() {
      @Override
      public void onSuccess(String result) {

        userinfobyid.userinfoSuccess(result);

      }

      @Override
      public void onFailed(int code) {

        userinfobyid.userinfoFaild(code);
      }
    });

  }

  @Override
  public void getFriend(String sid, final FriendAdd add) {

    Map<String,String> map = new HashMap<String,String>();
    map.put("relationship.friendId",sid);
    String sign =  JNICore.getSign(SortUtils.getMapResult(SortUtils.sortString(map))) ;
    map.put("user.sign",sign);


    RetrofitManager.get("http://qhb.2dyt.com/MyInterface/userAction_addFriends.action", map, new BaseObserver() {
      @Override
      public void onSuccess(String result) {

        add.addSuccess(result);
      }

      @Override
      public void onFailed(int code) {
        add.addFailed(code);
      }
    });

  }
}
