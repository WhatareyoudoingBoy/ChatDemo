package com.bwie.test.chatdemo.mpersenter;

import com.bwie.test.chatdemo.base.BasePersenter;
import com.bwie.test.chatdemo.mmodel.TwoModel;
import com.bwie.test.chatdemo.mmodel.TwoModellmpl;
import com.bwie.test.chatdemo.mview.TwoView;

/**
 * 作者： 武星宇
 * 日期： 2017/7/14.
 * 类用途：
 */

public class TwoPersenter extends BasePersenter<TwoView> {

  public TwoModellmpl twoModellmpl;
    public TwoPersenter(){
      twoModellmpl = new TwoModellmpl();
    }


    public void getFriendList(){

      twoModellmpl.friendData(new TwoModel.FriendInfo() {
        @Override
        public void friendSueecss(String result) {
          view.requstSuccess(result);
        }

        @Override
        public void friendFailed(int code) {
          view.requestFailed(code);
        }
      });

    }

}
