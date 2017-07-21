package com.bwie.test.chatdemo;



import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.multidex.MultiDex;
import android.util.Log;
import android.widget.ImageView;

import com.bwie.test.chatdemo.greendao.gen.DaoMaster;
import com.bwie.test.chatdemo.greendao.gen.DaoSession;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMOptions;
import com.lqr.emoji.IImageLoader;
import com.lqr.emoji.LQREmotionKit;
import com.mob.MobApplication;
import com.squareup.picasso.Picasso;


import org.greenrobot.greendao.database.Database;

import java.util.Iterator;
import java.util.List;

import static com.hyphenate.util.EasyUtils.TAG;


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
    MultiDex.install(this);
    super.onCreate();
    application = this;


    DaoMaster.DevOpenHelper devOpenHelper = new DaoMaster.DevOpenHelper(this, "everyon.db", null);
    Database db = devOpenHelper.getWritableDb();
    DaoMaster daoMaster = new DaoMaster(db);
    daoSession = daoMaster.newSession();
    System.loadLibrary("core");

    //Emoji表情
    LQREmotionKit.init(application, new IImageLoader() {
      @Override
      public void displayImage(Context context, String path, ImageView imageView) {
        Picasso.with(context).load(path).into(imageView);
      }
    });



    int pid = android.os.Process.myPid();
    String processAppName = getAppName(pid);
// 如果APP启用了远程的service，此application:onCreate会被调用2次
// 为了防止环信SDK被初始化2次，加此判断会保证SDK被初始化1次
// 默认的APP会在以包名为默认的process name下运行，如果查到的process name不是APP的process name就立即返回

    if (processAppName == null ||!processAppName.equalsIgnoreCase(this.getPackageName())) {
      Log.e(TAG, "enter the service process!");

      // 则此application::onCreate 是被service 调用的，直接返回
      return;
    }
    EMOptions options = new EMOptions();
// 默认添加好友时，是不需要验证的，改成需要验证
    options.setAcceptInvitationAlways(false);

//初始化
    EMClient.getInstance().init(this, options);
//在做打包混淆时，关闭debug模式，避免消耗不必要的资源
    EMClient.getInstance().setDebugMode(true);






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

  private String getAppName(int pID) {
    String processName = null;
    ActivityManager am = (ActivityManager) this.getSystemService(ACTIVITY_SERVICE);
    List l = am.getRunningAppProcesses();
    Iterator i = l.iterator();
    PackageManager pm = this.getPackageManager();
    while (i.hasNext()) {
      ActivityManager.RunningAppProcessInfo info = (ActivityManager.RunningAppProcessInfo) (i.next());
      try {
        if (info.pid == pID) {
          processName = info.processName;
          return processName;
        }
      } catch (Exception e) {
        // Log.d("Process", "Error>> :"+ e.toString());
      }
    }
    return processName;
  }

}
