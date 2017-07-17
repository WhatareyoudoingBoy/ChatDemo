package com.bwie.test.chatdemo.mmodel;

import com.bwie.test.chatdemo.bean.RegisterBean;

/**
 * 作者： 武星宇
 * 日期： 2017/7/5.
 * 类用途：
 */

public interface RegistedModel {

  void getSMS(String username);

  void postUserInfo(String phone,String username,String pwd,String sexy,String age,String location,String des,RegistedModelListner listner);

  interface RegistedModelListner{

    void registedOnSuccess(RegisterBean registerBean);
    void registedOnFailed();

  }

  void postLogin(String phone,String password,String lat,String lng,loginSuccess loginlistener);
  interface loginSuccess{
    void loginOnSuccess(String result);
    void loginOnFailed(int code);
  }


}
