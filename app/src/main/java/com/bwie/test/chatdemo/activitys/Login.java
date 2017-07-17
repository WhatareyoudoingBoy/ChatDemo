package com.bwie.test.chatdemo.activitys;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;


import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import android.widget.Button;
import android.widget.CheckBox;

import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;

import com.bwie.test.chatdemo.MyTost;
import com.bwie.test.chatdemo.R;
import com.bwie.test.chatdemo.base.BaseMvpActivity;
import com.bwie.test.chatdemo.bean.RegisterBean;
import com.bwie.test.chatdemo.bean.UserInfoBean;


import com.bwie.test.chatdemo.mpersenter.RegistedPersenter;

import com.bwie.test.chatdemo.mview.RegistedView;
import com.google.gson.Gson;
import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;

/**
 * 作者： 武星宇
 * 日期： 2017/7/4.
 * 类用途：
 */

public class Login extends BaseMvpActivity< RegistedView, RegistedPersenter > implements RegistedView {
  private View head;
  private TextView right_title;
  private CheckBox hidepwd;
  private EditText loginUsername, loginUserpwd;
  private double latitude;
  private double longitude;
  private Button loginRegisted, loginLogin;


  //声明AMapLocationClient类对象
  public AMapLocationClient mLocationClient = null;

  //声明AMapLocationClientOption对象
  public AMapLocationClientOption mLocationOption = null;

  //声明定位回调监听器
  public AMapLocationListener mLocationListener = new AMapLocationListener() {
    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
      if (aMapLocation != null) {
        if (aMapLocation.getErrorCode() == 0) {
          //获取维度
          latitude = aMapLocation.getLatitude();
          //获取精度
          longitude = aMapLocation.getLongitude();

        } else {
          //定位失败时，可通过ErrCode（错误码）信息来确定失败的原因，errInfo是错误信息，详见错误码表。
          Log.e("AmapError", "location Error, ErrCode:"
                  + aMapLocation.getErrorCode() + ", errInfo:"
                  + aMapLocation.getErrorInfo());
        }
      }
    }
  };

  @Override
  public RegistedPersenter initPersenter() {
    return new RegistedPersenter();
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.loginitem);
    initView();//初始化布局
    if (isNetworkAvailable(this)) {
      changeEvent();//点击切换
    } else {
      Toast.makeText(this, "当前无网络，请检查网络状态", Toast.LENGTH_SHORT).show();
    }
    //初始化定位
    mLocationClient = new AMapLocationClient(getApplicationContext());
    //初始化AMapLocationClientOption对象
    mLocationOption = new AMapLocationClientOption();
    //设置定位模式为AMapLocationMode.Hight_Accuracy，高精度模式。
    mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
//设置定位回调监听
    mLocationClient.setLocationListener(mLocationListener);
    //设置是否返回地址信息（默认返回地址信息）
    mLocationOption.setNeedAddress(true);
    //给定位客户端对象设置定位参数
    mLocationClient.setLocationOption(mLocationOption);
//启动定位
    mLocationClient.startLocation();
    changeEvent();//点击切换

  }

  private void changeEvent() {
/**
 * 右上角注册按钮的跳转
 */
    right_title.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        if (isNetworkAvailable(Login.this) ==false){
          Toast.makeText(Login.this, "请检查当前网络状态", Toast.LENGTH_SHORT).show();
        }else {
          startActivity(new Intent(Login.this, Registed.class));
          finish();
        }
      }
    });
    /**
     *登陆按钮
     */
    loginLogin.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        if(isNetworkAvailable(Login.this) ==false){
          Toast.makeText(Login.this, "请检查当前网络状态", Toast.LENGTH_SHORT).show();
        }else {
          String userNameResult = loginUsername.getText().toString().trim(); //获取用户的账号信息
          String userPwdResutl = loginUserpwd.getText().toString().trim(); // 获取用户的密码
          if (TextUtils.isEmpty(userNameResult) || TextUtils.isEmpty(userPwdResutl)) {
            Toast.makeText(Login.this, "账号或密码不能为空", Toast.LENGTH_SHORT).show();
          } else {
            persenter.netWorkLogin(userNameResult, userPwdResutl, latitude + "", longitude + "");

          }
        }


      }

  });
/**
 * 明暗密码
 */
      hidepwd.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()

  {
    @Override
    public void onCheckedChanged (CompoundButton buttonView,boolean isChecked){
    if (isChecked) {
      loginUserpwd.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
    } else {
      loginUserpwd.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
    }
  }


  });
  /**
   * 注册按钮
   */
      loginRegisted.setOnClickListener(new View.OnClickListener()

  {
    @Override
    public void onClick (View v){
    if (isNetworkAvailable(Login.this) == false) {
      Toast.makeText(Login.this, "请检查当前网络状态", Toast.LENGTH_SHORT).show();
    } else {
      startActivity(new Intent(Login.this, Registed.class));
      finish();
    }
  }
  });
}

  private void initView() {
    head = (View) findViewById(R.id.login_head);
    right_title = (TextView) head.findViewById(R.id.right_title);
    right_title.setText("注册");
    loginUsername = (EditText) findViewById(R.id.login_username);//用户账号
    loginUserpwd = (EditText) findViewById(R.id.login_password); //用户密码
    hidepwd = (CheckBox) findViewById(R.id.hidepwd);  //密码 明暗文展示按钮
    loginRegisted = (Button) findViewById(R.id.login_registed); //注册按钮
    loginLogin = (Button) findViewById(R.id.login_login); //登陆按钮
  }


  @Override
  protected void onDestroy() {
    super.onDestroy();
    mLocationClient.onDestroy();//销毁定位客户端，同时销毁本地定位服务。
  }


  @Override
  public void checkPhoneNumber(int type) {
  }

  @Override
  public void showTimer() {
  }

  @Override
  public void regithedSueecssView(RegisterBean registerBean) {
  }

  @Override
  public void registedFailed() {
  }

  @Override
  public void viewOnSuccess(final String result) {
    Gson gson = new Gson();
    UserInfoBean userInfoBean = gson.fromJson(result, UserInfoBean.class);
    int result_code = userInfoBean.getResult_code();

    if (result_code == 200) {
      String userName = userInfoBean.getData().getPhone();
      String password = userInfoBean.getData().getPassword();
      EMClient.getInstance().login(userName,password,new EMCallBack() {//回调
        @Override
        public void onSuccess() {
          EMClient.getInstance().groupManager().loadAllGroups();
          EMClient.getInstance().chatManager().loadAllConversations();
          Log.d("main", "登录聊天服务器成功！");
          Intent intent = new Intent(Login.this, TabActivity.class);
          intent.putExtra("info", result);
          startActivity(intent);

        }

        @Override
        public void onProgress(int progress, String status) {

        }

        @Override
        public void onError(int code, String message) {
          Log.d("main", "登录聊天服务器失败！");
          Log.d("main", message+"    --" +code);


        }
      });



    } else {
      viewOnFailed(result_code);
    }

  }

  @Override
  public void viewOnFailed(int code) {
    switch (code) {
      case 303:
        MyTost.makeText(Login.this, "请检查用户名和密码是否正确，或许可以再注册一个", Toast.LENGTH_SHORT);
        break;
      case 500:
        MyTost.makeText(Login.this, "服务器异常请稍后再试", Toast.LENGTH_SHORT);
        break;
      case 404:
        MyTost.makeText(Login.this, "请先注册后登陆", Toast.LENGTH_SHORT);
        break;


    }

  }

  /**
   * 检查当前网络是否可用
   *
   * @param
   * @return
   */

  public boolean isNetworkAvailable(Activity activity) {
    Context context = activity.getApplicationContext();
    // 获取手机所有连接管理对象（包括对wi-fi,net等连接的管理）
    ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
    if (connectivityManager == null) {
      return false;
    } else {
      // 获取NetworkInfo对象
      NetworkInfo[] networkInfo = connectivityManager.getAllNetworkInfo();
      if (networkInfo != null && networkInfo.length > 0) {
        for (int i = 0; i < networkInfo.length; i++) {
          System.out.println(i + "===状态===" + networkInfo[i].getState());
          System.out.println(i + "===类型===" + networkInfo[i].getTypeName());
          // 判断当前网络状态是否为连接状态
          if (networkInfo[i].getState() == NetworkInfo.State.CONNECTED) {
            return true;
          }
        }
      }
    }
    return false;
  }

}
