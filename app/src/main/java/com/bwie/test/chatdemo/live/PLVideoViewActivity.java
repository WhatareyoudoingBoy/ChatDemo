package com.bwie.test.chatdemo.live;

import android.app.Activity;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.bwie.test.chatdemo.R;
import com.bwie.test.chatdemo.live.widget.MediaController;
import com.pili.pldroid.player.AVOptions;
import com.pili.pldroid.player.PLMediaPlayer;
import com.pili.pldroid.player.widget.PLVideoView;

/**
 * 我要当观众
 */
public class PLVideoViewActivity extends Activity {
  private static final String TAG = PLVideoViewActivity.class.getSimpleName();

  private static final int MESSAGE_ID_RECONNECTING = 0x01;

  private MediaController mMediaController;
  private PLVideoView mVideoView;
  private Toast mToast = null;
  private String mVideoPath = null;
  private int mDisplayAspectRatio = PLVideoView.ASPECT_RATIO_FIT_PARENT;
  private boolean mIsActivityPaused = true;
  private View mLoadingView;
  private View mCoverView = null;
  private int mIsLiveStreaming = 1;
  private ListView mScreenlist;
  private EditText mEdtext;
  private RelativeLayout mRelativeLayout;
  private Button mLivesend;

  private void setOptions(int codecType) {
    AVOptions options = new AVOptions();
    // the unit of timeout is ms
    options.setInteger(AVOptions.KEY_PREPARE_TIMEOUT, 10 * 1000);
    options.setInteger(AVOptions.KEY_GET_AV_FRAME_TIMEOUT, 10 * 1000);
    options.setInteger(AVOptions.KEY_PROBESIZE, 128 * 1024);
    // Some optimization with buffering mechanism when be set to 1
    options.setInteger(AVOptions.KEY_LIVE_STREAMING, mIsLiveStreaming);
    if (mIsLiveStreaming == 1) {
      options.setInteger(AVOptions.KEY_DELAY_OPTIMIZATION, 1);
    }
    // 1 -> hw codec enable, 0 -> disable [recommended]
    options.setInteger(AVOptions.KEY_MEDIACODEC, codecType);
    // whether start play automatically after prepared, default value is 1
    options.setInteger(AVOptions.KEY_START_ON_PREPARED, 0);
    mVideoView.setAVOptions(options);
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_plvideo_view);
    mVideoView = (PLVideoView) findViewById(R.id.VideoView);
    mScreenlist = (ListView) findViewById(R.id.screen_list);//弹幕内容区
    mEdtext = (EditText) findViewById(R.id.live_edtext); //输入聊天
    mRelativeLayout = (RelativeLayout) findViewById(R.id.live_relative);//直播聊天内容框
    mLivesend = (Button) findViewById(R.id.live_send);//聊天发送

    mScreenlist.setVisibility(View.INVISIBLE);//加载前隐藏
    mRelativeLayout.setVisibility(View.INVISIBLE);//加载前隐藏


    Rect outRect = new Rect();
    getWindow().getDecorView().getWindowVisibleDisplayFrame(outRect);
    RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) mVideoView.getLayoutParams();
    params.height = outRect.bottom - outRect.top;




    mLoadingView = findViewById(R.id.LoadingView);
    mVideoView.setBufferingIndicator(mLoadingView);
    mLoadingView.setVisibility(View.VISIBLE);
    //拉流的地址
    mVideoPath = getIntent().getStringExtra("videoPath");
    mIsLiveStreaming = getIntent().getIntExtra("liveStreaming", 1);
    // 1 -> hw codec enable, 0 -> disable [recommended]
    int codec = getIntent().getIntExtra("mediaCodec", AVOptions.MEDIA_CODEC_SW_DECODE);
    setOptions(codec);
    // Set some listeners
    mVideoView.setOnInfoListener(mOnInfoListener);
    mVideoView.setOnVideoSizeChangedListener(mOnVideoSizeChangedListener);
    mVideoView.setOnBufferingUpdateListener(mOnBufferingUpdateListener);
    mVideoView.setOnCompletionListener(mOnCompletionListener);
    mVideoView.setOnSeekCompleteListener(mOnSeekCompleteListener);
    mVideoView.setOnErrorListener(mOnErrorListener);
    mVideoView.setVideoPath(mVideoPath);
    // You can also use a custom `MediaController` widget
    mMediaController = new MediaController(this, false, mIsLiveStreaming == 1);
    mVideoView.setMediaController(mMediaController);

  }

  @Override
  protected void onResume() {
    super.onResume();
    mIsActivityPaused = false;
    mVideoView.start();

  }

  @Override
  protected void onPause() {
    super.onPause();
    mToast = null;
    mIsActivityPaused = true;

  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
    mVideoView.stopPlayback();

  }

  public void onClickSwitchScreen(View v) {
    mDisplayAspectRatio = (mDisplayAspectRatio + 1) % 5;
    mVideoView.setDisplayAspectRatio(mDisplayAspectRatio);
    switch (mVideoView.getDisplayAspectRatio()) {
      case PLVideoView.ASPECT_RATIO_ORIGIN:
        showToastTips("Origin mode");

        break;
      case PLVideoView.ASPECT_RATIO_FIT_PARENT:
        showToastTips("Fit parent !");
        break;
      case PLVideoView.ASPECT_RATIO_PAVED_PARENT:
        showToastTips("Paved parent !");
        break;
      case PLVideoView.ASPECT_RATIO_16_9:
        showToastTips("16 : 9 !");
        break;
      case PLVideoView.ASPECT_RATIO_4_3:
        showToastTips("4 : 3 !");
        break;
      default:
        break;
    }
  }

  private PLMediaPlayer.OnInfoListener mOnInfoListener = new PLMediaPlayer.OnInfoListener() {
    @Override
    public boolean onInfo(PLMediaPlayer plMediaPlayer, int what, int extra) {


      mScreenlist.setVisibility(View.VISIBLE);
      mRelativeLayout.setVisibility(View.VISIBLE);
      return false;
    }
  };

  private PLMediaPlayer.OnErrorListener mOnErrorListener = new PLMediaPlayer.OnErrorListener() {
    @Override
    public boolean onError(PLMediaPlayer plMediaPlayer, int errorCode) {
      boolean isNeedReconnect = false;
      Log.e(TAG, "Error happened, errorCode = " + errorCode);
      switch (errorCode) {
        case PLMediaPlayer.ERROR_CODE_INVALID_URI:
          showToastTips("Invalid URL !");
          break;
        case PLMediaPlayer.ERROR_CODE_404_NOT_FOUND:
          showToastTips("404 resource not found !");
          break;
        case PLMediaPlayer.ERROR_CODE_CONNECTION_REFUSED:
          showToastTips("Connection refused !");
          break;
        case PLMediaPlayer.ERROR_CODE_CONNECTION_TIMEOUT:
          showToastTips("Connection timeout !");
          isNeedReconnect = true;
          break;
        case PLMediaPlayer.ERROR_CODE_EMPTY_PLAYLIST:
          showToastTips("Empty playlist !");
          break;
        case PLMediaPlayer.ERROR_CODE_STREAM_DISCONNECTED:
          showToastTips("Stream disconnected !");
          isNeedReconnect = true;
          break;
        case PLMediaPlayer.ERROR_CODE_IO_ERROR:
          showToastTips("Network IO Error !");
          isNeedReconnect = true;
          break;
        case PLMediaPlayer.ERROR_CODE_UNAUTHORIZED:
          showToastTips("Unauthorized Error !");
          break;
        case PLMediaPlayer.ERROR_CODE_PREPARE_TIMEOUT:
          showToastTips("Prepare timeout !");
          isNeedReconnect = true;
          break;
        case PLMediaPlayer.ERROR_CODE_READ_FRAME_TIMEOUT:
          showToastTips("Read frame timeout !");
          isNeedReconnect = true;
          break;
        case PLMediaPlayer.ERROR_CODE_HW_DECODE_FAILURE:
          setOptions(AVOptions.MEDIA_CODEC_SW_DECODE);
          isNeedReconnect = true;
          break;
        case PLMediaPlayer.MEDIA_ERROR_UNKNOWN:
          break;
        default:
          showToastTips("unknown error !");
          break;
      }
      // Todo pls handle the error status here, reconnect or call finish()
      if (isNeedReconnect) {
        sendReconnectMessage();

      } else {

        finish();
      }

      return true;
    }
  };

  private PLMediaPlayer.OnCompletionListener mOnCompletionListener = new PLMediaPlayer.OnCompletionListener() {
    @Override
    public void onCompletion(PLMediaPlayer plMediaPlayer) {
      Log.d(TAG, "Play Completed !");
      showToastTips("Play Completed !");


      finish();
    }
  };

  private PLMediaPlayer.OnBufferingUpdateListener mOnBufferingUpdateListener = new PLMediaPlayer.OnBufferingUpdateListener() {
    @Override
    public void onBufferingUpdate(PLMediaPlayer plMediaPlayer, int precent) {
      Log.d(TAG, "onBufferingUpdate: " + precent);

    }
  };

  private PLMediaPlayer.OnSeekCompleteListener mOnSeekCompleteListener = new PLMediaPlayer.OnSeekCompleteListener() {
    @Override
    public void onSeekComplete(PLMediaPlayer plMediaPlayer) {
      Log.d(TAG, "onSeekComplete !");

    }
  };

  private PLMediaPlayer.OnVideoSizeChangedListener mOnVideoSizeChangedListener = new PLMediaPlayer.OnVideoSizeChangedListener() {
    @Override
    public void onVideoSizeChanged(PLMediaPlayer plMediaPlayer, int width, int height, int videoSar, int videoDen) {
      Log.d(TAG, "onVideoSizeChanged: width = " + width + ", height = " + height + ", sar = " + videoSar + ", den = " + videoDen);


    }
  };

  private void showToastTips(final String tips) {
    if (mIsActivityPaused) {

      return;
    }
    runOnUiThread(new Runnable() {
      @Override
      public void run() {
        if (mToast != null) {
          mToast.cancel();

        }
        mToast = Toast.makeText(PLVideoViewActivity.this, tips, Toast.LENGTH_SHORT);
        mToast.show();
      }
    });
  }

  protected Handler mHandler = new Handler(Looper.getMainLooper()) {
    @Override
    public void handleMessage(Message msg) {
      if (msg.what != MESSAGE_ID_RECONNECTING) {

        return;
      }

      mVideoView.setVideoPath(mVideoPath);

      mVideoView.start();

    }
  };

  private void sendReconnectMessage() {
    showToastTips("正在重连...");

    mLoadingView.setVisibility(View.VISIBLE);
    mHandler.removeCallbacksAndMessages(null);
    mHandler.sendMessageDelayed(mHandler.obtainMessage(MESSAGE_ID_RECONNECTING), 500);
  }
}
