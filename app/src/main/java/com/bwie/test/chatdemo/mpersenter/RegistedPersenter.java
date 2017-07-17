package com.bwie.test.chatdemo.mpersenter;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

import com.bwie.test.chatdemo.activitys.ReegistedThree;
import com.bwie.test.chatdemo.activitys.RegistedTwo;
import com.bwie.test.chatdemo.base.BasePersenter;
import com.bwie.test.chatdemo.bean.RegisterBean;
import com.bwie.test.chatdemo.mmodel.RegistedModel;
import com.bwie.test.chatdemo.mmodel.RegistedModelmpl;
import com.bwie.test.chatdemo.mview.RegistedView;
import com.bwie.test.chatdemo.utils.PhoneCheckUtils;

/**
 * 作者： 武星宇
 * 日期： 2017/7/5.
 * 类用途：
 */

public class RegistedPersenter extends BasePersenter< RegistedView > {

  public RegistedModelmpl registedModelmpl;

  public RegistedPersenter() {
    registedModelmpl = new RegistedModelmpl();
  }

  /**
   * 0   为空
   * 1   手机号不正确、
   *
   * @param username
   */

  public void checkPhone(String username) {
    if (TextUtils.isEmpty(username)) {
      view.checkPhoneNumber(0);
      return;

    } else if (!PhoneCheckUtils.isChinaPhoneLegal(username)) {
      view.checkPhoneNumber(1);
      return;

    }
    registedModelmpl.getSMS(username);
    sendSMS();

  }

  public void sendSMS() {
    view.showTimer();
  }

  public void nextStep(Context context, String phone, String sms) {
    //判断账号和密码是否为空，
    if (TextUtils.isEmpty(sms) && TextUtils.isEmpty(phone)) {
      view.checkPhoneNumber(2);
      return;
    }
    //判断 有账号没有验证码 的情况
    else if (TextUtils.isEmpty(sms) && !TextUtils.isEmpty(phone)) {
      view.checkPhoneNumber(3);
      return;
    }
    //判断 验证码输入大于4位并且账号信息不为空
    else if (sms.length() > 4 && !TextUtils.isEmpty(phone)) {
      view.checkPhoneNumber(4);
      if (!PhoneCheckUtils.isChinaPhoneLegal(phone)) {
        view.checkPhoneNumber(1);
        return;

      }
      return;


    }
    //判断 验证码大于4位数并且账号为空
     if (sms.length() > 4 && TextUtils.isEmpty(phone)) {
      view.checkPhoneNumber(5);
    }
    //判断 验证码为4位数 并且 账号为空
    else if (sms.length() == 4 && TextUtils.isEmpty(phone)) {
      view.checkPhoneNumber(0);


    }
    //判断验证码小于4位数




      if (!PhoneCheckUtils.isVerificationCode(sms)) {
      view.checkPhoneNumber(6);
    }if (sms.length() < 4) {
      view.checkPhoneNumber(4);
    } else if (sms.length() < 4 && !PhoneCheckUtils.isVerificationCode(sms)) {
      view.checkPhoneNumber(4);
    }else if(sms.length() == 4 && !TextUtils.isEmpty(phone) && !PhoneCheckUtils.isVerificationCode(sms)
            &&!PhoneCheckUtils.isChinaPhoneLegal(phone)){
      view.checkPhoneNumber(7);

    } else {
      Intent intent = new Intent(context,ReegistedThree.class);
      intent.putExtra("phone",phone);
      context.startActivity(intent);
     }


  }


  public void registerUserInfo(String phone,String username,String pwd,String sexy,String age,String location,String des){


    registedModelmpl.postUserInfo(phone,username, pwd, sexy, age, location, des, new RegistedModel.RegistedModelListner() {
      @Override
      public void registedOnSuccess(RegisterBean registerBean) {

        view.regithedSueecssView(registerBean);
      }

      @Override
      public void registedOnFailed() {

        view.registedFailed();
      }
    });

  }


  public void netWorkLogin(String phone,String password,String lat,String lng){

      registedModelmpl.postLogin(phone, password, lat, lng, new RegistedModel.loginSuccess() {
        @Override
        public void loginOnSuccess(String result) {
          view.viewOnSuccess(result);
        }

        @Override
        public void loginOnFailed(int code) {
          view.viewOnFailed(code);
        }
      });
  }


}
