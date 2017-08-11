package com.bwie.test.chatdemo.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bwie.test.chatdemo.R;
import com.bwie.test.chatdemo.bean.ZhuboBean;
import com.bwie.test.chatdemo.core.JNICore;
import com.bwie.test.chatdemo.core.SortUtils;
import com.bwie.test.chatdemo.live.ZhiboActivity;
import com.bwie.test.chatdemo.live.SWCameraStreamingActivity;
import com.bwie.test.chatdemo.network.BaseObserver;
import com.bwie.test.chatdemo.network.RetrofitManager;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;


public class TheFourFragment extends Fragment {

  private static final String ARG_PARAM1 = "param1";
  private static final String ARG_PARAM2 = "param2";


  private String mParam1;
  private String mParam2;
  private TextView mPlayer;
  private TextView mFans;
  private String url;


  public TheFourFragment() {

  }


  public static TheFourFragment newInstance(String param1, String param2) {
    TheFourFragment fragment = new TheFourFragment();
    Bundle args = new Bundle();
    args.putString(ARG_PARAM1, param1);
    args.putString(ARG_PARAM2, param2);
    fragment.setArguments(args);
    return fragment;
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    if (getArguments() != null) {
      mParam1 = getArguments().getString(ARG_PARAM1);
      mParam2 = getArguments().getString(ARG_PARAM2);
    }
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
View view= inflater.inflate(R.layout.fragment_the_four, container, false);
    //主播
    mPlayer = (TextView) view.findViewById(R.id.four_player);
    //观众
    mFans = (TextView) view.findViewById(R.id.four_fans);
    getinfo();
    return view;
  }

  @Override
  public void onActivityCreated(@Nullable Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);

    mPlayer.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        //主播
        Intent intent = new Intent(getActivity(),SWCameraStreamingActivity.class);

        intent.putExtra("stream_json_str",url);

        startActivity(intent);
      }
    });

    mFans.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        //观众

        Intent intent = new Intent(getActivity(),ZhiboActivity.class);
        startActivity(intent);

      }
    });
  }

  public void getinfo(){
    final Map map = new HashMap<String, String>();
    map.put("live.type", 1 + "");

    String sign =  JNICore.getSign(SortUtils.getMapResult(SortUtils.sortString(map))) ;
    map.put("user.sign", sign);
    RetrofitManager.post("http://qhb.2dyt.com/MyInterface/userAction_live.action", map, new BaseObserver() {
      @Override
      public void onSuccess(String result) {

        Gson gson=new Gson();
        ZhuboBean zhuboBean = gson.fromJson(result, ZhuboBean.class);
        url = zhuboBean.getUrl();
//                creatChatroom();//主播自动创建聊天室

      }

      @Override
      public void onFailed(int code) {


      }
    });
  }
}
