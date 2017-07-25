package com.bwie.test.chatdemo.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.bwie.test.chatdemo.R;

import com.bwie.test.chatdemo.emjoutils.EaseSmileUtils;
import com.hyphenate.chat.EMMessage;

import com.hyphenate.chat.EMVoiceMessageBody;
import com.lqr.emoji.MoonUtils;


import java.util.ArrayList;
import java.util.List;




public class ChatMsgAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

  LayoutInflater inflater;
  Context context;
  private List< EMMessage > list;
  private List< String > image;

  public ChatMsgAdapter(Context context, List< EMMessage > list) {
    this.context = context;
    this.inflater = LayoutInflater.from(context);
    this.list = list;
  }

  private OnItemClickListener mOnItemClickListener;
  private OnItemLongClickListener mOnItemLongClickListener;

  public interface OnItemClickListener {
    void onItemClick(View view, String position);
  }

  public interface OnItemLongClickListener {
    void onItemLongClick(View view, String position);
  }

  public void setOnItemClickListener(OnItemClickListener mOnItemClickListener) {
    this.mOnItemClickListener = mOnItemClickListener;
  }

  public void setOnItemLongClickListener(OnItemLongClickListener mOnItemLongClickListener) {
    this.mOnItemLongClickListener = mOnItemLongClickListener;
  }

  @Override
  public int getItemViewType(int position) {
    if (list.get(position).getType() == EMMessage.Type.TXT) {
      if (list.get(position).direct() == EMMessage.Direct.RECEIVE) {
        return 1;
      } else {
        return 0;
      }
    }
    if (list.get(position).getType() == EMMessage.Type.VOICE) {
      if (list.get(position).direct() == EMMessage.Direct.RECEIVE) {
        return 2;
      } else {
        return 3;
      }
    }
    return -1;
  }

  @Override
  public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    if (viewType == 0) {
      View view = LayoutInflater.from(parent.getContext())
              .inflate(R.layout.item_chat_text_right, parent, false);
      //将创建的View注册点击事件
      return new ViewHolderRight(view);
    } else if (viewType == 1) {
      View view = LayoutInflater.from(parent.getContext())
              .inflate(R.layout.item_chat_text_left, parent, false);
      //将创建的View注册点击事件
      return new ViewHolderLeft(view);
    }
    if (viewType == 2) {
      // 语音接收方
      View view = LayoutInflater.from(parent.getContext())
              .inflate(R.layout.chatvoice_left, parent, false);
      return new VoiceViewHolderLeft(view);
    } else {
      // 语音发送方
      View view1 = LayoutInflater.from(parent.getContext())
              .inflate(R.layout.chatvoice_right, parent, false);
      return new VoiceViewHolderRight(view1);
    }

  }


    public void leftImage(String iamgePath){

        if(image == null){
            image = new ArrayList<>();
        }
        image.add(iamgePath);
        notifyDataSetChanged();

    }


    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        String s = list.get(position).getBody().toString();
        String substring = s.substring(5, s.length() - 1);
        if (holder instanceof ViewHolderRight) {
            final ViewHolderRight viewHolderRight = (ViewHolderRight) holder;
          MoonUtils.identifyFaceExpression(context,viewHolderRight.textRightImageText,substring,40); //表情转化

        }else if (holder instanceof ViewHolderLeft){
            final ViewHolderLeft viewHolderLeft = (ViewHolderLeft) holder;

            viewHolderLeft.textLeftImageText.setText(EaseSmileUtils.getSmiledText(context, substring));

        }else if(holder instanceof VoiceViewHolderLeft){
            //接收方
            final VoiceViewHolderLeft voiceViewHolderLeft = (VoiceViewHolderLeft) holder;
            final EMVoiceMessageBody body = (EMVoiceMessageBody) list.get(position).getBody();

          if(mOnItemClickListener != null){
             ((VoiceViewHolderLeft) holder).voiceLeftVoice.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                 int leftVoide = voiceViewHolderLeft.getLayoutPosition();
                 mOnItemClickListener.onItemClick(voiceViewHolderLeft.voiceLeftVoice,body.getLocalUrl());
               }
             });
           }



        }else {
            //发送方
            final VoiceViewHolderRight voiceViewHolderRight = (VoiceViewHolderRight) holder;
             final EMVoiceMessageBody body1 = (EMVoiceMessageBody) list.get(position).getBody();
          if(mOnItemLongClickListener != null){
            voiceViewHolderRight.voiceRightVoice.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                int voiceViewHolderRightLayoutPosition = voiceViewHolderRight.getLayoutPosition();
                mOnItemLongClickListener.onItemLongClick(voiceViewHolderRight.voiceRightVoice,body1.getLocalUrl());
              }
            });
          }
        }

    }


    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }




    private static class ViewHolderRight extends RecyclerView.ViewHolder{

        TextView textRightImageText;

        ImageView textRightImage;

        public ViewHolderRight(View view) {
            super(view);
            textRightImageText= (TextView) view.findViewById(R.id.text_right_image_text);
            textRightImage= (ImageView) view.findViewById(R.id.text_right_image);

        }
    }

   private static class ViewHolderLeft extends RecyclerView.ViewHolder{

        ImageView textLeftImage;
        TextView textLeftImageText;

        public ViewHolderLeft(View view) {
            super(view);

            textLeftImage= (ImageView) view.findViewById(R.id.text_left_image);
            textLeftImageText= (TextView) view.findViewById(R.id.text_left_image_text);

        }
    }

    private static class VoiceViewHolderLeft extends RecyclerView.ViewHolder{

        ImageView voiceLeftIm,voiceLeftVoice;


        public VoiceViewHolderLeft(View itemView) {
            super(itemView);

            voiceLeftIm = (ImageView) itemView.findViewById(R.id.chat_voice_left_im);
            voiceLeftVoice = (ImageView) itemView.findViewById(R.id.chat_voice_left_voice);

        }
    }
    private static class VoiceViewHolderRight extends RecyclerView.ViewHolder{

        ImageView voiceRightIm,voiceRightVoice;

        public VoiceViewHolderRight(View itemView) {
            super(itemView);

            voiceRightIm = (ImageView) itemView.findViewById(R.id.chat_voice_right_im);
            voiceRightVoice = (ImageView) itemView.findViewById(R.id.chat_voice_right_voice);

        }
    }

}
