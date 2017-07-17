package com.bwie.test.chatdemo.mmodel;

import android.util.Log;

import com.bwie.test.chatdemo.MyApplication;
import com.bwie.test.chatdemo.bean.DataBean;
import com.bwie.test.chatdemo.bean.NearbyPeople;
import com.bwie.test.chatdemo.core.JNICore;
import com.bwie.test.chatdemo.core.SortUtils;
import com.bwie.test.chatdemo.greendao.gen.DaoSession;
import com.bwie.test.chatdemo.greendao.gen.DataBeanDao;
import com.bwie.test.chatdemo.network.BaseObserver;
import com.bwie.test.chatdemo.network.RetrofitManager;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 作者： 武星宇
 * 日期： 2017/7/11.
 * 类用途：
 */

public class FirstFragmentModellmpl implements FirstModel {




  @Override
  public void checkEveryoneinfo( final FirstInfo info) {

    Map<String,String> map = new HashMap<String,String>();
    map.put("user.currenttimer", System.currentTimeMillis() + "");

    String sign =  JNICore.getSign(SortUtils.getMapResult(SortUtils.sortString(map))) ;

    map.put("user.sign",sign);

    RetrofitManager.get("http://qhb.2dyt.com/MyInterface/userAction_selectAllUser.action",map, new BaseObserver() {
      @Override
      public void onSuccess(String result) {

        info.firstSuccess(result);

        Gson gson = new Gson();
       DataBean nearbyPeople = gson.fromJson(result, DataBean.class);
        DaoSession daoSession = MyApplication.getDaoSession();
        DataBeanDao dataBeanDao = daoSession.getDataBeanDao();
        dataBeanDao.insert(nearbyPeople);


      }

      @Override
      public void onFailed(int code) {
        info.firstFailed(code);
      }
    });

  }
}
