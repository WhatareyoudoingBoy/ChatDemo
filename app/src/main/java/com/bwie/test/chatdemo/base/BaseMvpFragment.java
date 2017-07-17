package com.bwie.test.chatdemo.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bwie.test.chatdemo.R;

/**
 * 作者： 武星宇
 * 日期： 2017/7/4.
 * 类用途：
 */

public abstract class BaseMvpFragment<V, T extends BasePersenter<V>> extends Fragment {

  public T persenter;
  public abstract T initPersenter();

  public BaseMvpFragment(){

  }

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    persenter = initPersenter();
  }

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater,  ViewGroup container,  Bundle savedInstanceState) {
    return inflater.inflate(R.layout.basemvpfragmentitem,container,false);
  }


  @Override
  public void onResume() {
    super.onResume();
    persenter.attch((V) this);
  }

  @Override
  public void onDestroy() {
    super.onDestroy();
    persenter.detach();
  }
}
