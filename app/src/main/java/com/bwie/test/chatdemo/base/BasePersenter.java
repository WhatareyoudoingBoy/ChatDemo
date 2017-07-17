package com.bwie.test.chatdemo.base;

/**
 * 作者： 武星宇
 * 日期： 2017/7/4.
 * 类用途：
 */

public abstract class BasePersenter<T> {

  public T view;

  public void attch(T view){

    this.view = view;

  }

  public void detach(){


    this.view = null;
  }


}
