package com.bwie.test.chatdemo.mpersenter;

import com.bwie.test.chatdemo.base.BasePersenter;
import com.bwie.test.chatdemo.mmodel.UserInfoModel;
import com.bwie.test.chatdemo.mmodel.UserInfoModellmpl;
import com.bwie.test.chatdemo.mview.UserInfoView;


/**
 * 作者： 武星宇
 * 日期： 2017/7/12.
 * 类用途：
 */

public class UserInfoPersenter extends BasePersenter<UserInfoView> {

  public UserInfoModellmpl userInfoModellmpl;

  public UserInfoPersenter(){

    userInfoModellmpl = new UserInfoModellmpl();


  }

  /**
   * 好友详情
   * @param id
   */
  public void getUserInfo(String  id){

    userInfoModellmpl.getUserInfoById(id, new UserInfoModel.Userinfobyid() {
      @Override
      public void userinfoSuccess(String result) {

        view.onUserbyidSuccess(result);
      }

      @Override
      public void userinfoFaild(int code) {
        view.onUserbyidFailed(code);
      }
    });

  }

  /**
   * 添加好友
   * @param sid
   */
  public void addUserFriend(String sid){

    userInfoModellmpl.getFriend(sid, new UserInfoModel.FriendAdd() {
      @Override
      public void addSuccess(String result) {
        view.addFriendSuccess(result);
      }

      @Override
      public void addFailed(int code) {
        view.addFriendFailed(code);
      }
    });
  }


}
