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


import java.util.ArrayList;
import java.util.List;




public class ChatMsgAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    LayoutInflater inflater;
    Context context;
    private List<EMMessage> list;
    private List<String> image;

    public ChatMsgAdapter(Context context, List<EMMessage> list) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.list = list;
    }

    private OnItemClickListener mOnItemClickListener;
    private OnItemLongClickListener mOnItemLongClickListener;

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public interface OnItemLongClickListener {
        void onItemLongClick(View view, int position);
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


        return -1;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (viewType == 0) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_chat_text_right, parent, false);
            //将创建的View注册点击事件

            return new ViewHolderRight(view);
        } else  {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_chat_text_left, parent, false);
            //将创建的View注册点击事件

            return new ViewHolderLeft(view);
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
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        String s = list.get(position).getBody().toString();
        String substring = s.substring(5, s.length() - 1);
        if (holder instanceof ViewHolderRight) {
            final ViewHolderRight viewHolderRight = (ViewHolderRight) holder;

            viewHolderRight.textRightImageText.setText(EaseSmileUtils.getSmiledText(context, substring));
            if (mOnItemClickListener != null) {
                //为ItemView设置监听器
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int position = viewHolderRight.getLayoutPosition(); // 1
                        mOnItemClickListener.onItemClick(viewHolderRight.itemView, position); // 2
                    }
                });
            }
            if (mOnItemLongClickListener != null) {
                holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        int position = viewHolderRight.getLayoutPosition();
                        mOnItemLongClickListener.onItemLongClick(viewHolderRight.itemView, position);
                        //返回true 表示消耗了事件 事件不会继续传递
                        return true;
                    }
                });
            }
        }else if (holder instanceof ViewHolderLeft){
            final ViewHolderLeft viewHolderLeft = (ViewHolderLeft) holder;

            viewHolderLeft.textLeftImageText.setText(EaseSmileUtils.getSmiledText(context, substring));
            if (mOnItemClickListener != null) {
                //为ItemView设置监听器
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int position = viewHolderLeft.getLayoutPosition(); // 1
                        mOnItemClickListener.onItemClick(viewHolderLeft.itemView, position); // 2
                    }
                });
            }
            if (mOnItemLongClickListener != null) {
                holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        int position = viewHolderLeft.getLayoutPosition();
                        mOnItemLongClickListener.onItemLongClick(viewHolderLeft.itemView, position);
                        //返回true 表示消耗了事件 事件不会继续传递
                        return true;
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
//            ButterKnife.bind(this, view);
            textRightImageText= (TextView) view.findViewById(R.id.text_right_image_text);
            textRightImage= (ImageView) view.findViewById(R.id.text_right_image);

        }
    }

   private static class ViewHolderLeft extends RecyclerView.ViewHolder{

        ImageView textLeftImage;
        TextView textLeftImageText;

        public ViewHolderLeft(View view) {
            super(view);
//            ButterKnife.bind(this, view);

            textLeftImage= (ImageView) view.findViewById(R.id.text_left_image);
            textLeftImageText= (TextView) view.findViewById(R.id.text_left_image_text);

        }
    }
}
