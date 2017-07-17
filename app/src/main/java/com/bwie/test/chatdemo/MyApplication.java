package com.bwie.test.chatdemo;



import com.bwie.test.chatdemo.greendao.gen.DaoMaster;
import com.bwie.test.chatdemo.greendao.gen.DaoSession;
import com.mob.MobApplication;


/**
 * 作者： 武星宇
 * 日期： 2017/7/4.
 * 类用途：
 */

public class MyApplication extends MobApplication {


  public static MyApplication application ;
  private static DaoSession daoSession;

  @Override
  public void onCreate() {
    super.onCreate();
    application = this;
    DaoMaster.DevOpenHelper devOpenHelper = new DaoMaster.DevOpenHelper(getApplicationContext(), "everyon.db", null);
    DaoMaster daoMaster = new DaoMaster(devOpenHelper.getWritableDb());
    daoSession = daoMaster.newSession();



  }

  public static DaoSession getDaoSession(){
    return daoSession;
  }
  public static MyApplication getApplication(){
    if(application == null){
      application = getApplication() ;
    }
    return application;
  }
}
