package com.bwie.test.chatdemo.mmodel;

import com.bwie.test.chatdemo.core.JNICore;
import com.bwie.test.chatdemo.core.SortUtils;
import com.bwie.test.chatdemo.network.BaseObserver;
import com.bwie.test.chatdemo.network.RetrofitManager;

import java.util.HashMap;
import java.util.Map;

/**
 * 作者： 武星宇
 * 日期： 2017/7/14.
 * 类用途：
 */

public class TwoModellmpl implements TwoModel {




  @Override
  public void friendData(final FriendInfo friendInfo) {

    Map<String,String> map = new HashMap<String,String>();
    map.put("user.currenttimer", System.currentTimeMillis() + "");

    String sign =  JNICore.getSign(SortUtils.getMapResult(SortUtils.sortString(map))) ;

    map.put("user.sign",sign);
    RetrofitManager.get("http://qhb.2dyt.com/MyInterface/userAction_selectAllUserAndFriend.action", map, new BaseObserver() {
      @Override
      public void onSuccess(String result) {

        friendInfo.friendSueecss(result);

      }

      @Override
      public void onFailed(int code) {
        friendInfo.friendFailed(code);
      }
    });
  }
}
