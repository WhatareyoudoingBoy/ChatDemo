package com.bwie.test.chatdemo.activitys;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bwie.test.chatdemo.R;
import com.bwie.test.chatdemo.base.BaseMvpActivity;
import com.bwie.test.chatdemo.bean.RegisterBean;
import com.bwie.test.chatdemo.mpersenter.RegistedPersenter;
import com.bwie.test.chatdemo.mview.RegistedView;
import com.google.gson.Gson;
import com.lljjcoder.citypickerview.widget.CityPickerView;

/**
 * 作者： 武星宇
 * 日期： 2017/7/6.
 * 类用途：
 */

public class ReegistedThree extends BaseMvpActivity< RegistedView, RegistedPersenter > implements RegistedView {


  private TextView location;
  private Button tonext;
  private EditText registedName;
  private EditText registedPwd;
  private TextView registedSexy;
  private TextView registedAge;
  private EditText registedSelfsign;
  private android.app.AlertDialog.Builder builder;
  private String phone;
  android.app.AlertDialog.Builder buildera;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.registerinfo);
    initView();//加载控件
    phone = getIntent().getStringExtra("phone");
    clickEvent(); //按钮点击事件
  }

  private void clickEvent() {
    final String[] sexArry = new String[]{"女", "男"};
    registedSexy.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        builder = new android.app.AlertDialog.Builder(ReegistedThree.this);
        builder.setTitle("请选择性别");
        builder.setItems(sexArry, new DialogInterface.OnClickListener() {
          @Override
          public void onClick(DialogInterface dialog, int which) {
            dialog.dismiss();
            registedSexy.setText(sexArry[which]);
          }
        });
        builder.show();
      }
    });

    location.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        location.clearFocus();
        CityPickerView cityPickerView = new CityPickerView(ReegistedThree.this);
        cityPickerView.setOnCityItemClickListener(new CityPickerView.OnCityItemClickListener() {
          @Override
          public void onSelected(String... citySelected) {
//省份  
            String province = citySelected[0];
//城市  
            String city = citySelected[1];
//区县  
            String district = citySelected[2];


            location.setText(province + "-" + city + "-" + district);
            location.setPadding(200,10,0,0);
          }
        });
        cityPickerView.show();
      }
    });

    tonext.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        String name = registedName.getText().toString().trim();//用户输入的昵称
        String pwd = registedPwd.getText().toString().trim(); //用户输入的密码
        String sexy = registedSexy.getText().toString().trim();//性别
        String age = registedAge.getText().toString().trim();//年龄
        String selfsign = registedSelfsign.getText().toString().trim();//个签
        String locationa = location.getText().toString().trim();//地区
        persenter.registerUserInfo(phone,name,pwd,sexy,age,locationa,selfsign);

        if(TextUtils.isEmpty(name)&&TextUtils.isEmpty(pwd)&&TextUtils.isEmpty(sexy)&&TextUtils.isEmpty(age)
                &&TextUtils.isEmpty(selfsign)&&TextUtils.isEmpty(locationa)) {
          Toast.makeText(ReegistedThree.this, "请重新检测填写的信息", Toast.LENGTH_SHORT).show();


        }else {
          //下一步的操作执行
          startActivity(new Intent(ReegistedThree.this, RegistedFour.class));
        }
      }
    });

    registedAge.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        if (buildera == null) {
          final String[] ages = getResources().getStringArray(R.array.age);
          buildera = new android.app.AlertDialog.Builder(ReegistedThree.this);
          buildera.setTitle("请选择年龄");
          buildera.setItems(ages, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
              dialog.dismiss();
              registedAge.setText(ages[which]);
            }
          });
        }

        buildera.show();
      }
    });

  }

  private void initView() {
    location = (TextView) findViewById(R.id.location); //城市三级列表选择
    tonext = (Button) findViewById(R.id.register_next); //下一步按钮
    registedName = (EditText) findViewById(R.id.registed_name); //昵称
    registedPwd = (EditText) findViewById(R.id.registed_pwd); //密码
    registedSexy = (TextView) findViewById(R.id.registed_sexy); //性别
    registedAge = (TextView) findViewById(R.id.registed_age); //年龄
    registedSelfsign = (EditText) findViewById(R.id.registed_selfsign); //个签

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
  public void viewOnSuccess(String result) {

  }

  @Override
  public void viewOnFailed(int code) {

    if(code == 300){
      Toast.makeText(this, "参数不正确", Toast.LENGTH_SHORT).show();
    }
  }

  @Override
  public RegistedPersenter initPersenter() {
    return new RegistedPersenter();
  }
}
