package com.bwie.test.chatdemo.mmodel;

import com.bwie.test.chatdemo.base.BasePersenter;
import com.bwie.test.chatdemo.mview.FirstView;

/**
 * 作者： 武星宇
 * 日期： 2017/7/10.
 * 类用途：
 */

public interface FirstModel {

  void checkEveryoneinfo(FirstInfo info);

  interface FirstInfo{

    void firstSuccess(String relust);
    void firstFailed(int code);
  }


}
