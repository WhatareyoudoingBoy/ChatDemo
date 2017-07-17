package com.bwie.test.chatdemo.activitys;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.bwie.test.chatdemo.MyTost;
import com.bwie.test.chatdemo.R;
import com.bwie.test.chatdemo.base.BaseMvpActivity;
import com.bwie.test.chatdemo.bean.RegisterBean;
import com.bwie.test.chatdemo.mpersenter.RegistedPersenter;
import com.bwie.test.chatdemo.mview.RegistedView;


import java.util.concurrent.TimeUnit;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * 作者： 武星宇
 * 日期： 2017/7/5.
 * 类用途：
 */

public class RegistedTwo extends BaseMvpActivity< RegistedView, RegistedPersenter > implements RegistedView {

  private EditText phoneNumber,verificationCode;
  private String phone;

  private Button getsms;
  EventHandler eventHandler;
  private Button register;
  private Button canel;
  private String yanma;

  @Override
  public RegistedPersenter initPersenter() {
    return new RegistedPersenter();
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.registed);
    initView();//加载控件
    SMSSDK.initSDK(RegistedTwo.this, "1f2a5e136ee51", "9976a6ecf4372ee35916e5216f0c5e9e");
    eventHandler = new EventHandler() {
      public void afterEvent(int event, int result, Object data) {
        if (data instanceof Throwable) {
          Throwable throwable = (Throwable) data;
          String msg = throwable.getMessage();
        } else {
          if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {
          }
        }
      }
    };
    // 注册监听器
    SMSSDK.registerEventHandler(eventHandler);
    /**
     * 获取验证码
     */
    getsms.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        //用户输入的账号信息
        phone = phoneNumber.getText().toString().trim();
        persenter.checkPhone(phone);

      }
    });
    /**
     * 注册按钮  判断账号及验证码
     */

    register.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        phone = phoneNumber.getText().toString().trim();
        //验证码信息
        yanma = verificationCode.getText().toString().trim();

        persenter.nextStep(RegistedTwo.this,phone, yanma);



      }
    });



  }

  private void initView() {
    phoneNumber = (EditText) findViewById(R.id.registed_phonenum);  //账号输入框
    verificationCode = (EditText) findViewById(R.id.registed_yanzhengma); //验证码
    getsms = (Button) findViewById(R.id.getsms);  //验证码按钮
    register = (Button) findViewById(R.id.registed_registed); //注册按钮
    canel = (Button) findViewById(R.id.registed_canl);  //取消按钮

  }


  @Override
  public void checkPhoneNumber(int type) {
    switch (type) {
      case 0:
        MyTost.makeText(RegistedTwo.this, "手机号码不能为空", Toast.LENGTH_SHORT);
        break;
      case 1:
        MyTost.makeText(RegistedTwo.this, "手机号码不正确", Toast.LENGTH_SHORT);
        break;
      case 2:
        MyTost.makeText(RegistedTwo.this, "账号和验证码不能为空", Toast.LENGTH_SHORT);
        break;
      case 3:
        MyTost.makeText(RegistedTwo.this, "验证码不能为空", Toast.LENGTH_SHORT);
        break;
      case 4:
        MyTost.makeText(RegistedTwo.this, "请检查验证码", Toast.LENGTH_SHORT);
        break;
      case 5:
        MyTost.makeText(RegistedTwo.this, "请检查验证码并且手机不能为空", Toast.LENGTH_SHORT);
        break;
      case 6:
        MyTost.makeText(RegistedTwo.this, "验证码应为纯数字", Toast.LENGTH_SHORT);
        break;
      case 7:
        MyTost.makeText(RegistedTwo.this, "请重新检查信息是否填写正确", Toast.LENGTH_SHORT);
        break;


    }
  }
  private Disposable disposable ;
  /**
   * 倒计时
   */
  @Override
  public void showTimer() {

    getsms.setEnabled(false);
    Observable.interval(0,1, TimeUnit.SECONDS)
            .take(30)
            .map(new Function<Long, Long>() {
              @Override
              public Long apply(@NonNull Long aLong) throws Exception {
                return 29 - aLong;
              }
            })
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(new Observer<Long>() {
              @Override
              public void onSubscribe(@NonNull Disposable d) {


                disposable = d ;

              }

              @Override
              public void onNext(@NonNull Long aLong) {

                System.out.println("aLong = " + aLong);
                getsms.setText(aLong+" S 后重新获取");

              }

              @Override
              public void onError(@NonNull Throwable e) {

              }

              @Override
              public void onComplete() {
                getsms.setEnabled(true);
                getsms.setText("获取验证码");

              }
            });
  }

  @Override
  public void regithedSueecssView(RegisterBean registerBean) {
  }

  @Override
  public void registedFailed() {
  }

  @Override
  public void viewOnSuccess(String result) {
  }

  @Override
  public void viewOnFailed(int code) {
  }


  @Override
  protected void onDestroy() {
    super.onDestroy();
    SMSSDK.unregisterEventHandler(eventHandler);
  }


}
