package com.bwie.test.chatdemo.mmodel;

import com.bwie.test.chatdemo.MyApplication;
import com.bwie.test.chatdemo.bean.RegisterBean;
import com.bwie.test.chatdemo.core.JNICore;
import com.bwie.test.chatdemo.core.SortUtils;
import com.bwie.test.chatdemo.network.BaseObserver;
import com.bwie.test.chatdemo.network.RetrofitManager;
import com.bwie.test.chatdemo.utils.Md5Utils;
import com.bwie.test.chatdemo.utils.PreferencesUtils;
import com.bwie.test.chatdemo.utils.aes.JNCryptorUtils;
import com.bwie.test.chatdemo.utils.rsa.RsaUtils;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

import cn.smssdk.SMSSDK;

/**
 * 作者： 武星宇
 * 日期： 2017/7/5.
 * 类用途：
 */

public class RegistedModelmpl implements RegistedModel {
  @Override
  public void getSMS(String username) {

    SMSSDK.getVerificationCode("86", username);

  }

  @Override
  public void postUserInfo(String phone, String username, String pwd, String sexy, String age, String location, String des, final RegistedModelListner listner) {

    Map<String,String> map = new HashMap<String,String>();
    map.put("user.phone",phone);
    map.put("user.nickname",username);
    map.put("user.password",Md5Utils.getMD5(pwd));
    map.put("user.gender",sexy);
    map.put("user.area",location);
    map.put("user.age",age);
    map.put("user.introduce",des);
    System.out.println("SortUtils.getMapResult(SortUtils.sortString(map)) = " + SortUtils.getMapResult(SortUtils.sortString(map)));

    String sign =  JNICore.getSign(SortUtils.getMapResult(SortUtils.sortString(map))) ;
    map.put("user.sign",sign);

    System.out.println("sign = " + sign);

    RetrofitManager.post("http://qhb.2dyt.com/MyInterface/userAction_add.action", map, new BaseObserver() {
      @Override
      public void onSuccess(String result) {


        Gson gson = new Gson();
        RegisterBean registerBean = gson.fromJson(result, RegisterBean.class);

        if(registerBean.getResult_code() == 200){
          PreferencesUtils.addConfigInfo(MyApplication.getApplication(),"phone",registerBean.getData().getPhone());
          PreferencesUtils.addConfigInfo(MyApplication.getApplication(),"password",registerBean.getData().getPassword());
          PreferencesUtils.addConfigInfo(MyApplication.getApplication(),"yxpassword",registerBean.getData().getYxpassword());
          PreferencesUtils.addConfigInfo(MyApplication.getApplication(),"uid",registerBean.getData().getUserId());
          PreferencesUtils.addConfigInfo(MyApplication.getApplication(),"nickname",registerBean.getData().getNickname());
        }
        listner.registedOnSuccess(registerBean);

      }

      @Override
      public void onFailed(int code) {
        listner.registedOnFailed();
      }


    });

  }



  @Override
  public void postLogin(String phone, String password, String lat, String lng, final loginSuccess loginlistener) {

    String randomKey =   RsaUtils.getStringRandom(16);


    String rsaRandomKey =   RsaUtils.getInstance().createRsaSecret(MyApplication.getApplication(),randomKey);


    String cipherPhone =   JNCryptorUtils.getInstance().encryptData(phone,MyApplication.getApplication(),randomKey);

      Map<String,String> map = new HashMap<String,String>();

    map.put("user.phone",cipherPhone);
    map.put("user.password", Md5Utils.getMD5(password));





    map.put("user.secretkey",rsaRandomKey);

    map.put("user.lat",lat);
    map.put("user.lng",lng);
    String sign =  JNICore.getSign(SortUtils.getMapResult(SortUtils.sortString(map))) ;
    map.put("user.sign",sign);
    RetrofitManager.post("http://qhb.2dyt.com/MyInterface/userAction_login.action", map, new BaseObserver() {
      @Override
      public void onSuccess(String result) {
        loginlistener.loginOnSuccess(result);
      }

      @Override
      public void onFailed(int code) {


        loginlistener.loginOnFailed(code);
      }
    });

  }


}
