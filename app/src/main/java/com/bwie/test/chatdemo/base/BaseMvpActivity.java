package com.bwie.test.chatdemo.base;

import android.os.Bundle;

import com.bwie.test.chatdemo.R;


/**
 * 作者： 武星宇
 * 日期： 2017/7/5.
 * 类用途：
 */

public abstract class BaseMvpActivity <V, T extends BasePersenter<V>> extends BaseActivity {

  public T persenter;
  public abstract T initPersenter();

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.baseactivityimte);

    persenter = initPersenter();
  }

  @Override
  protected void onResume() {
    super.onResume();

    persenter.attch((V) this);
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();

    persenter.detach();
  }
}
