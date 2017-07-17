package com.bwie.test.chatdemo.mview;

import com.bwie.test.chatdemo.bean.RegisterBean;

/**
 * 作者： 武星宇
 * 日期： 2017/7/5.
 * 类用途：
 */

public interface RegistedView {

  //判断手机号是否正确

  public void checkPhoneNumber(int type);


  public void showTimer();


  public void regithedSueecssView(RegisterBean registerBean);

  public void registedFailed();


  //登陆成功的数据返回
 public void viewOnSuccess(String result);
  //登陆失败的数据返回
 public  void viewOnFailed(int code);

}
