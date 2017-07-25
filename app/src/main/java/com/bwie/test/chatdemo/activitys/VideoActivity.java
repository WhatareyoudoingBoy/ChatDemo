package com.bwie.test.chatdemo.activitys;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.bwie.test.chatdemo.MyApplication;
import com.bwie.test.chatdemo.R;
import com.hyphenate.chat.EMCallStateChangeListener;
import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.EMNoActiveCallException;
import com.hyphenate.exceptions.EMServiceNotReadyException;
import com.hyphenate.media.EMCallSurfaceView;




public class VideoActivity extends Activity {


  private String uid;
  private int type;
  private EMCallSurfaceView surfaceBig;
  private EMCallSurfaceView surfaceSmall;
  private TextView videoactivityTv;
  private Button videoactivityBtJieting;
  private Button videoactivityBtGuaduan;
  private Button videoactivityBtJujie;

  /**
   * @param type    1 拨打视频电话  2 接受视频电话
   * @param uid
   * @param context
   */
  public static void startTelActivity(int type, String uid, Context context) {
    Intent intent = new Intent(context, VideoActivity.class);
    intent.putExtra("type", type);
    intent.putExtra("uid", uid);
    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    context.startActivity(intent);

  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_video);

    initView();
    uid = getIntent().getExtras().getString("uid");
    type = getIntent().getExtras().getInt("type");
    //让自己图像 显示在上面
    surfaceSmall.getHolder().setFormat(PixelFormat.TRANSPARENT);
    surfaceSmall.setZOrderOnTop(true);
    if (1 == type) {
      //拨打电话
      videoactivityBtJieting.setVisibility(View.GONE);
      videoactivityBtJujie.setVisibility(View.GONE);
      videoactivityBtGuaduan.setVisibility(View.VISIBLE);
      //拨打视频通话
      try {
        EMClient.getInstance().callManager().makeVideoCall(uid);
        MyApplication.callTo();

      } catch (EMServiceNotReadyException e) {
        e.printStackTrace();
      }


    } else {
      //接听电话
      videoactivityBtJieting.setVisibility(View.VISIBLE);
      videoactivityBtJujie.setVisibility(View.VISIBLE);
      videoactivityBtGuaduan.setVisibility(View.GONE);


    }
    EMClient.getInstance().callManager().setSurfaceView(surfaceSmall, surfaceBig);
    connectState();
    onclickEvent();
  }

  private void initView() {
    surfaceBig = (EMCallSurfaceView) findViewById(R.id.surface_big);//打视频
    surfaceSmall = (EMCallSurfaceView) findViewById(R.id.surface_small);
    videoactivityTv = (TextView) findViewById(R.id.videoactivity_tv);
    videoactivityBtJieting = (Button) findViewById(R.id.videoactivity_bt_jieting);
    videoactivityBtGuaduan = (Button) findViewById(R.id.videoactivity_bt_guaduan);
    videoactivityBtJujie = (Button) findViewById(R.id.videoactivity_bt_jujie);
  }


  public void connectState() {
    EMClient.getInstance().callManager().addCallStateChangeListener(new EMCallStateChangeListener() {
      @Override
      public void onCallStateChanged(CallState callState, CallError error) {
        Log.e("videoactivityTv ", "callState " + callState);
        Log.e("videoactivityTv ", "error " + error);
        switch (callState) {
          case CONNECTING: // 正在连接对方
            runOnUiThread(new Runnable() {
              @Override
              public void run() {
                videoactivityTv.setText("正在连接");

              }
            });
            Log.e("videoactivityTv ", "正在连接");
            break;
          case CONNECTED: // 双方已经建立连接
            Log.e("videoactivityTv ", "双方已经建立连接");
            break;
          case ACCEPTED: // 电话接通成功
            runOnUiThread(new Runnable() {
              @Override
              public void run() {
                videoactivityTv.setText("通话中");

              }
            });
            Log.e("videoactivityTv ", "电话接通成功");
            break;
          case DISCONNECTED: // 电话断了
            finish();
            Log.e("videoactivityTv ", "电话断了");
            break;
          case NETWORK_UNSTABLE: //网络不稳定
            if (error == CallError.ERROR_NO_DATA) {
              //无通话数据
            } else {
            }
            Log.e("videoactivityTv ", "网络不稳定");
            break;
          case NETWORK_NORMAL: //网络恢复正常
            Log.e("videoactivityTv ", "网络恢复正常");
            break;
          default:
            Log.e("videoactivityTv ", "default");
            break;
        }

      }
    });
  }


        public void onclickEvent(){
          videoactivityBtJieting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              //接听
              try {
                EMClient.getInstance().callManager().answerCall();
              } catch (EMNoActiveCallException e) {
                e.printStackTrace();
              } catch (Exception e) {
                e.printStackTrace();
              }

            }
          });
          videoactivityBtGuaduan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              try {
                //挂断
                EMClient.getInstance().callManager().endCall();
              } catch (EMNoActiveCallException e) {
                e.printStackTrace();
              }
              finish();
            }
          });
          videoactivityBtJujie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              //拒接
              try {
                EMClient.getInstance().callManager().rejectCall();
              } catch (EMNoActiveCallException e) {
                e.printStackTrace();
              }

            }
          });

        }



}
