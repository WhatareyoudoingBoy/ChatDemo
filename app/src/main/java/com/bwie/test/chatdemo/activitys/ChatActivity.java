package com.bwie.test.chatdemo.activitys;


import android.content.Context;

import android.content.Intent;
import android.graphics.Color;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;

import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;

import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;


import com.bwie.test.chatdemo.R;

import com.bwie.test.chatdemo.adapter.ChatMsgAdapter;
import com.bwie.test.chatdemo.adapter.MoreFunctionGridAdapter;
import com.bwie.test.chatdemo.utils.SDCardUtils;
import com.bwie.test.chatdemo.utils.speexutils.SpeexPlayer;
import com.bwie.test.chatdemo.utils.speexutils.SpeexRecorder;
import com.bwie.test.chatdemo.witgh.keybord.KeyBoardHelper;
import com.hyphenate.EMCallBack;
import com.hyphenate.EMMessageListener;

import com.hyphenate.chat.EMClient;


import com.hyphenate.chat.EMMessage;
import com.hyphenate.exceptions.EMServiceNotReadyException;
import com.lqr.emoji.EmotionLayout;
import com.lqr.emoji.IEmotionSelectedListener;
import com.lqr.emoji.LQREmotionKit;
import com.lqr.emoji.MoonUtils;


import java.io.File;
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
  ChatMsgAdapter adapter;
  private SpeexRecorder recorderInstance;


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
          break;

      }

    }

  };




  private String uidd;
  private EMMessageListener msgListener;
  private EmotionLayout mElEmotion;
  private String userface;
  private Button jianpan;
  private Button sendVideo;
  private String fileName;
  private int starttime;
  private int endtime;
  private SpeexPlayer player;
  private Button sendMsg;
  private String trim;
  private RelativeLayout functionLayout;
  private GridView gridFunction;
  private SpeexPlayer player1;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_chat);
    uidd = getIntent().getStringExtra("uidd"); //获取用户的ID
    userface = getIntent().getStringExtra("userface");
    initView();//加载控件
    list = new ArrayList< EMMessage >();
    adapter = new ChatMsgAdapter(this, list);
    chatMore.setTag(0);
    adapter.setOnItemClickListener(new ChatMsgAdapter.OnItemClickListener() {
      @Override
      public void onItemClick(View view, String position) {
        player1 = new SpeexPlayer(position, handler);
        player1.startPlay();


      }
    });
    adapter.setOnItemLongClickListener(new ChatMsgAdapter.OnItemLongClickListener() {
      @Override
      public void onItemLongClick(View view, String position) {
        SpeexPlayer player11 = new SpeexPlayer(position, handler);
        player11.startPlay();
      }
    });
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
    sendVideo.setOnTouchListener(new View.OnTouchListener() {
      @Override
      public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
          case MotionEvent.ACTION_DOWN:
            sendVideo.setBackgroundColor(Color.YELLOW);
            startVideo();
            starttime = (int) System.currentTimeMillis();
            break;
          case MotionEvent.ACTION_MOVE:
            if (event.getY() < -200) {
              recorderInstance.stopRecoding();
            }
            break;
          case MotionEvent.ACTION_UP:
//TODO  -----------------------------------------------------------------
            endtime = (int) System.currentTimeMillis();
            //filePath为语音文件路径，length为录音时间(秒)
            recorderInstance.setRecording(false);
            final EMMessage message = EMMessage.createVoiceSendMessage(fileName, endtime - starttime, uidd);
            EMClient.getInstance().chatManager().sendMessage(message);
            message.setMessageStatusCallback(new EMCallBack() {
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
            list.add(message);
            adapter.notifyDataSetChanged();
            sendVideo.setBackgroundColor(Color.WHITE);
            break;
        }
        return true;
      }
    });
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
    sendMsg = (Button) findViewById(R.id.sendmsg_btn);//发送消息
    functionLayout = (RelativeLayout) findViewById(R.id.addfunctionlayout);//加号显示的布局
    gridFunction = (GridView) findViewById(R.id.addfunction);//九宫格显示加号里的内容
    /**
     * 加号中的信息设置GridView Adapter
     */
    MoreFunctionGridAdapter moreFunctionGridAdapter = new MoreFunctionGridAdapter(ChatActivity.this);
    gridFunction.setAdapter(moreFunctionGridAdapter);
    //点击事件
    gridFunction.setOnItemClickListener(new AdapterView.OnItemClickListener() {
      @Override
      public void onItemClick(AdapterView< ? > parent, View view, int position, long id) {
//        Toast.makeText(ChatActivity.this, "语音/视频", Toast.LENGTH_SHORT).show();
//        Intent intent = new Intent(ChatActivity.this, VideoActivity.class);
//          intent.putExtra("usid",uidd);
//        startActivity(intent);

          Intent intn = new Intent(ChatActivity.this,VideoActivity.class);
          intn.putExtra("uid",uidd);
          intn.putExtra("type",1);
          startActivity(intn);



      }
    });
    /**
     * Editext 动态监听
     */
    chatMessage.addTextChangedListener(new TextWatcher() {
      @Override
      public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        if (TextUtils.isEmpty(s)) {
          sendMsg.setVisibility(View.GONE);
          chatMore.setVisibility(View.VISIBLE);
        } else {
          sendMsg.setVisibility(View.VISIBLE);
          chatMore.setVisibility(View.GONE);
        }


      }

      @Override
      public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (!TextUtils.isEmpty(s)) {
          sendMsg.setVisibility(View.VISIBLE);
          chatMore.setVisibility(View.GONE);
        } else {
          sendMsg.setVisibility(View.GONE);
          chatMore.setVisibility(View.VISIBLE);
        }

      }

      @Override
      public void afterTextChanged(Editable s) {
      }
    });
    /**
     * -----------------------------------------------------------------------------------------------------------------------------------
     */
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
    mElEmotion.attachEditText(chatMessage);
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
        //加号按钮
        int tag = (Integer) chatMore.getTag();
        if (tag == 1) {
          functionLayout.setVisibility(View.GONE);
          chatMore.setTag(0);
        } else {
          functionLayout.setVisibility(View.VISIBLE);
          chatMore.setTag(1);
        }


      }
    });
    /**
     * 发送按钮
     */
    sendMsg.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        trim = chatMessage.getText().toString().trim();
        setTextMessage(trim, uidd);
        chatMessage.getText().clear();
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


  /**
   * 开始语音
   */

  public void startVideo() {
    String filePath = Environment.getExternalStorageDirectory() + File.separator + SDCardUtils.DLIAO;
    System.out.println("filePath:" + filePath);
    File file = new File(filePath + "/");
    System.out.println("file:" + file);
    if (!file.exists()) {
      file.mkdirs();

    }
    fileName = file + File.separator + System.currentTimeMillis() + ".spx";
    System.out.println("保存文件名：＝＝ " + fileName);
    recorderInstance = new SpeexRecorder(fileName, handler);
    Thread th = new Thread(recorderInstance);
    th.start();
    recorderInstance.setRecording(true);
  }

  /**
   * 移除播放
   */
  public void removeVideo() {
    recorderInstance.setRecording(false);
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
    final EMMessage emMessage = EMMessage.createTxtSendMessage(content, id);
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
    adapter.notifyDataSetChanged();
    chatRecycler.scrollToPosition(adapter.getItemCount() - 1);
    handler.sendEmptyMessage(3);

  }

  /**
   * -----------------------------------------------------------------------------------------------------------------------------------
   */

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


}
