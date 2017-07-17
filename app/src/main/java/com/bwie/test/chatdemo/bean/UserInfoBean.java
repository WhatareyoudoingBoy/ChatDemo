package com.bwie.test.chatdemo.bean;



/**
 * 作者： 武星宇
 * 日期： 2017/7/10.
 * 类用途：
 */


public class UserInfoBean {


  /**
   * result_message : success
   * data : {"area":"甘肃省-白银市-白银区","password":"80ac32fccaa9f0030bea18b8463d66b2","lasttime":1499693968265,"createtime":1499691972764,"gender":"男","lng":116.300617,"phone":"18811587426","introduce":"我是卡萨丁","imagePath":"http://qhb.2dyt.com/MyInterface/images/efba9fd7-e45f-43c6-bb61-e2746aec7a3f.jpg","nickname":"奥里给","userId":203,"lat":40.04027}
   * result_code : 200
   */

  private String result_message;
  private DataBean data;
  private int result_code;

  public String getResult_message() {
    return result_message;
  }

  public void setResult_message(String result_message) {
    this.result_message = result_message;
  }

  public DataBean getData() {
    return data;
  }

  public void setData(DataBean data) {
    this.data = data;
  }

  public int getResult_code() {
    return result_code;
  }

  public void setResult_code(int result_code) {
    this.result_code = result_code;
  }


}
