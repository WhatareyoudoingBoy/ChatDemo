package com.bwie.test.chatdemo.live;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;


import com.bwie.test.chatdemo.R;
import com.bwie.test.chatdemo.bean.GuanZhongBean;
import com.bwie.test.chatdemo.core.JNICore;
import com.bwie.test.chatdemo.core.SortUtils;
import com.bwie.test.chatdemo.network.BaseObserver;
import com.bwie.test.chatdemo.network.RetrofitManager;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



public class ZhiboActivity extends AppCompatActivity {

    private GridView grid_id_view;
    private List<GuanZhongBean.ListBean> list1;
    List<GuanZhongBean.ListBean> list=new ArrayList<>();
    private Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zhibo);

        grid_id_view = (GridView) findViewById(R.id.grid_id_view);

        getGianzhong();


        adapter = new Adapter(list,ZhiboActivity.this);
        grid_id_view.setAdapter(adapter);

     grid_id_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
         @Override
         public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
             Intent intent1 = new Intent(ZhiboActivity.this, PLVideoViewActivity.class);
             intent1.putExtra("videoPath",list.get(position).getPublishUrl());
             startActivity(intent1);
         }
     });


    }


    public void getGianzhong(){
        final Map map = new HashMap<String, String>();
        map.put("live.type", 2 + "");
        String sign =  JNICore.getSign(SortUtils.getMapResult(SortUtils.sortString(map))) ;
        map.put("user.sign", sign);

        RetrofitManager.post("http://qhb.2dyt.com/MyInterface/userAction_live.action", map, new BaseObserver() {
            @Override
            public void onSuccess(String result) {
                System.out.println("8888888888888888888888888"+result);
                Gson gson=new Gson();
                GuanZhongBean guanZhongBean = gson.fromJson(result, GuanZhongBean.class);
                list1 = guanZhongBean.getList();

                    list.addAll(list1);
                  adapter.notifyDataSetChanged();

                            }

            @Override
            public void onFailed(int code) {

            }
        });
    }

}
