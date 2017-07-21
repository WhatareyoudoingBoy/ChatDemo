package com.bwie.test.chatdemo.activitys;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;

import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;

import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;


import com.bwie.test.chatdemo.R;

import com.bwie.test.chatdemo.adapter.ChatMsgAdapter;
import com.bwie.test.chatdemo.witgh.keybord.KeyBoardHelper;
import com.hyphenate.EMCallBack;
import com.hyphenate.EMMessageListener;

import com.hyphenate.chat.EMClient;


import com.hyphenate.chat.EMMessage;
import com.lqr.emoji.EmotionLayout;
import com.lqr.emoji.IEmotionSelectedListener;
import com.lqr.emoji.LQREmotionKit;
import com.lqr.emoji.MoonUtils;


import java.util.ArrayList;
import java.util.List;


public class ChatActivity extends AppCompatActivity implements KeyBoardHelper.OnKeyBoardStatusChangeListener {

  List< EMMessage > list;
  private View chatTitle;
  private RecyclerView chatRecycler;
  private Button yuyin;
  private EditText chatMessage;
  private ImageView biaoqing;
  private ImageView chatMore;


  Handler handler = new Handler() {


    @Override
    public void handleMessage(Message msg) {
      super.handleMessage(msg);
      switch (msg.what) {
        case 1:
          break;
        case 2:
          adapter.notifyDataSetChanged();
          chatRecycler.scrollToPosition(adapter.getItemCount() - 1);
          break;
        case 3:
          adapter.notifyDataSetChanged();
          chatRecycler.scrollToPosition(adapter.getItemCount() - 1);
          break;

      }

    }

  };
  ChatMsgAdapter adapter;
  private String uidd;
  private EMMessageListener msgListener;
  private EmotionLayout mElEmotion;
  private String userface;
  private Button jianpan;
  private Button sendVideo;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_chat);
    uidd = getIntent().getStringExtra("uidd");
    userface = getIntent().getStringExtra("userface");
    initView();//加载控件
    list = new ArrayList< EMMessage >();
    adapter = new ChatMsgAdapter(this, list);
//    adapter.leftImage(userface);
    chatRecycler.setAdapter(adapter);
    /**
     * 接收消息
     */
    receive();
    /**
     * 软键盘
     */
    KeyBoardHelper keyBoardHelper = new KeyBoardHelper(this);
    keyBoardHelper.onCreate();
    keyBoardHelper.setOnKeyBoardStatusChangeListener(this);
    biaoqing.setTag(1);
    setKeyBoardModelResize();


  }


  /**
   * 加载控件
   */

  private void initView() {
    chatTitle = (View) findViewById(R.id.chattitle); //标题布局
    chatRecycler = (RecyclerView) findViewById(R.id.chat_recycler); //recyclerview //消息列表
    yuyin = (Button) findViewById(R.id.chat_yuyin); //语音
    chatMessage = (EditText) findViewById(R.id.chat_msg);  //信息
    biaoqing = (ImageView) findViewById(R.id.chat_expression); //表情
    chatMore = (ImageView) findViewById(R.id.chat_more); //更多
    chatRecycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
    mElEmotion = (EmotionLayout) findViewById(R.id.elEmotion);//表情布局
    jianpan = (Button) findViewById(R.id.jianpan);//底部键盘
    sendVideo = (Button) findViewById(R.id.sendvideo);//底部语音按钮
    /**
     * -----------------------------------------------------------------------------------------------------------------------------------
     */
    mElEmotion.attachEditText(chatMessage);
    mElEmotion.setEmotionSelectedListener(new IEmotionSelectedListener() {
      @Override
      public void onEmojiSelected(String key) {
        if (chatMessage == null)
          return;
        Editable editable = chatMessage.getText();
        if (key.equals("/DEL")) {
          chatMessage.dispatchKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_FORWARD_DEL));  //keyEvent(开始（按下），结束（抬起）);
        } else {
          int start = chatMessage.getSelectionStart();
          int end = chatMessage.getSelectionEnd();
          start = (start < 0 ? 0 : start);
          end = (start < 0 ? 0 : end);
          editable.replace(start, end, "");
          int editEnd = chatMessage.getSelectionEnd();
          MoonUtils.replaceEmoticons(LQREmotionKit.getContext(), editable, 0, editable.toString().length());
          chatMessage.setSelection(editEnd);
        }
      }

      @Override
      public void onStickerSelected(String categoryName, String stickerName, String stickerBitmapPath) {
      }
    });
    /**
     * 软键盘和表情布局的稳定不颤抖切换
     */
    biaoqing.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        setKeyBoardModelPan();
        int tag = (Integer) biaoqing.getTag();
        if (tag == 1) {
          // 显示表情
          hidenKeyBoard(chatMessage);
          mElEmotion.setVisibility(View.VISIBLE);
          biaoqing.setTag(2);
        } else {
          biaoqing.setTag(1);
          //  显示键盘
          showKeyBoard(chatMessage);
        }
      }
    });
    chatMore.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        String trim = chatMessage.getText().toString().trim();
        if (!TextUtils.isEmpty(trim)) {
          setTextMessage(trim, uidd);
          chatMessage.getText().clear();
        } else {
        }


      }
    });
    /**
     * 底部键盘按钮
     */
    yuyin.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        setKeyBoardModelPan();
        jianpan.setVisibility(View.VISIBLE);//可见
        yuyin.setVisibility(View.GONE);//隐藏
        chatMessage.setVisibility(View.GONE);
        sendVideo.setVisibility(View.VISIBLE);//可见
        mElEmotion.setVisibility(View.GONE);
        hidenKeyBoard(chatMessage);
        setKeyBoardModelPan();
      }
    });

    jianpan.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        setKeyBoardModelPan();
        jianpan.setVisibility(View.GONE);
        yuyin.setVisibility(View.VISIBLE);
        chatMessage.setVisibility(View.VISIBLE);
        sendVideo.setVisibility(View.GONE);

        showKeyBoard(chatMessage);

      }
    });



  }
  //设置输入法模式 pan

  public void setKeyBoardModelPan() {
    getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN
            | WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

  }
  //设置输入法模式 resize

  public void setKeyBoardModelResize() {
    getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN
            | WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

  }
  //隐藏键盘

  public void hidenKeyBoard(EditText editText) {
    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
    imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);

  }
  // 显示键盘

  public void showKeyBoard(EditText editText) {
    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
    imm.showSoftInput(editText, InputMethodManager.SHOW_FORCED);

  }


  /**
   * 发送消息
   *
   * @param content
   * @param id
   */
  public void setTextMessage(String content, String id) {
    EMMessage emMessage = EMMessage.createTxtSendMessage(content, id);
    emMessage.setChatType(EMMessage.ChatType.Chat);
    EMClient.getInstance().chatManager().sendMessage(emMessage);
    EMClient.getInstance().chatManager().getConversation(id).insertMessage(emMessage);
    emMessage.setMessageStatusCallback(new EMCallBack() {

      @Override
      public void onSuccess() {
      }


      @Override
      public void onError(int i, String s) {
      }


      @Override
      public void onProgress(int i, String s) {
      }

    });
    list.add(emMessage);
    handler.sendEmptyMessage(3);

  }


  @Override
  public void OnKeyBoardPop(int keyBoardheight) {
    handler.sendEmptyMessageAtTime(1, 200);
  }

  @Override
  public void OnKeyBoardClose(int oldKeyBoardheight) {
  }

  /**
   * 销毁后移除接受消息的监听事件
   */

  @Override
  protected void onDestroy() {
    super.onDestroy();
    EMClient.getInstance().chatManager().removeMessageListener(msgListener);

  }

  /**
   * 接收消息的监听事件
   */
  public void receive() {
    msgListener = new EMMessageListener() {


      @Override
      public void onMessageReceived(List< EMMessage > messages) {
        list.addAll(messages);
        handler.sendEmptyMessage(2);


      }


      @Override
      public void onCmdMessageReceived(List< EMMessage > messages) {
        //收到透传消息
      }


      @Override
      public void onMessageRead(List< EMMessage > messages) {
        //收到已读回执
      }


      @Override
      public void onMessageDelivered(List< EMMessage > message) {
        //收到已送达回执
      }


      @Override
      public void onMessageChanged(EMMessage message, Object change) {
        //消息状态变动
      }

    };
    //添加接收消息的监听
    EMClient.getInstance().chatManager().addMessageListener(msgListener);


  }


  public native void open();
  static {
    System.loadLibrary("speex");
  }
}
