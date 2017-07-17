package com.bwie.test.chatdemo.mpersenter;

import com.bwie.test.chatdemo.base.BasePersenter;
import com.bwie.test.chatdemo.mmodel.FirstFragmentModellmpl;
import com.bwie.test.chatdemo.mmodel.FirstModel;
import com.bwie.test.chatdemo.mview.FirstView;

/**
 * 作者： 武星宇
 * 日期： 2017/7/10.
 * 类用途：
 */

public class FirstPersenter extends BasePersenter<FirstView> {

  private FirstFragmentModellmpl firstFragmentModellmpl;
  public FirstPersenter(){

    this.firstFragmentModellmpl = new FirstFragmentModellmpl();
  }


  //展示所有人
  public void getEveryone(){


    firstFragmentModellmpl.checkEveryoneinfo( new FirstModel.FirstInfo() {
      @Override
      public void firstSuccess(String relust) {

        view.onSuccess(relust);
      }

      @Override
      public void firstFailed(int code) {

        view.onFailed(code);
      }
    });

  }

}
