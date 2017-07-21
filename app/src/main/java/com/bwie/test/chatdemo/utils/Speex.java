package com.bwie.test.chatdemo.utils;

/**
 * 作者： 武星宇
 * 日期： 2017/7/21.
 * 类用途：
 */

public class Speex {
  private static final int DEFAULT_COMPRESSION = 8;

  Speex() {
  }

  public void init() {
    load();
    open(DEFAULT_COMPRESSION);
  }

  private void load() {
    try {
      System.loadLibrary("speex");
    } catch (Throwable e) {
      e.printStackTrace();
    }

  }

  public native int open(int compression);

  public native int getFrameSize();

  public native int decode(byte encoded[], short lin[], int size);

  public native int encode(short lin[], int offset, byte encoded[], int size);

  public native void close();
}
