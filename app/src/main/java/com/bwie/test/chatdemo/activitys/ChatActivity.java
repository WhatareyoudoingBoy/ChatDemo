package com.bwie.test.chatdemo.activitys;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bwie.test.chatdemo.R;

import com.hyphenate.EMMessageListener;

import com.hyphenate.chat.EMMessage;


import java.util.List;

public class ChatActivity extends AppCompatActivity {
  EMMessageListener msgListener = new EMMessageListener() {

    @Override
    public void onMessageReceived(List<EMMessage> messages) {
      //收到消息
      Toast.makeText(ChatActivity.this, "收到", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCmdMessageReceived(List<EMMessage> messages) {
      //收到透传消息
      Toast.makeText(ChatActivity.this, "收到", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onMessageRead(List<EMMessage> messages) {
      //收到已读回执
      Toast.makeText(ChatActivity.this, "收到", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onMessageDelivered(List<EMMessage> message) {
      //收到已送达回执
      Toast.makeText(ChatActivity.this, "收到", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onMessageChanged(EMMessage message, Object change) {
      //消息状态变动
      Toast.makeText(ChatActivity.this, "收到", Toast.LENGTH_SHORT).show();
    }
  };
  private View chatTitle;
  private RecyclerView chatRecycler;
  private Button yuyin;
  private EditText chatMessage;
  private ImageView biaoqing;
  private ImageView chatMore;
  private LinearLayout moreInfo;


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_chat);
    String uidd = getIntent().getStringExtra("uidd");
    Log.e("UID=====", uidd);

    initView();//加载控件

  }

  private void initView() {
//    chatTitle =(View) findViewById(R.id.chattitle); //标题布局
//    chatRecycler = (RecyclerView) findViewById(R.id.chat_recycler); //recyclerview //消息列表
//    yuyin = (Button) findViewById(R.id.chat_yuyin); //语音
//    chatMessage = (EditText) findViewById(R.id.chat_msg);  //信息
//    biaoqing = (ImageView) findViewById(R.id.chat_expression); //表情
    chatMore = (ImageView) findViewById(R.id.chat_more); //更多
    moreInfo = (LinearLayout) findViewById(R.id.buttom_layout_view); //更多内容
    chatMore.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        moreInfo.setVisibility(View.VISIBLE);

      }
    });

  }
}
