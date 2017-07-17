package com.bwie.test.chatdemo.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


import com.bwie.test.chatdemo.R;

import java.util.List;


/**
 * 所有的 activity 都继承
 */
public class IActivity extends FragmentActivity implements View.OnClickListener{

    private TextView textViewTitle;
    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_i);
        fragmentManager = getSupportFragmentManager();


        AppManager.getAppManager().addActivity(this);




    }


    /**
     * 切换fragment
     * @param pos
     * @param list
     */
    public void switchIFragment(int pos, List<Fragment> list,int containerId){
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        if (!list.get(pos).isAdded()) {
            fragmentTransaction.add(containerId, list.get(pos),list.get(pos).getClass().getSimpleName());
        }
        for(int i=0;i<list.size();i++){
            if(i == pos){
                fragmentTransaction.show(list.get(pos));
            }else {
                fragmentTransaction.hide(list.get(i));
            }
        }
        fragmentTransaction.commit();
    }




    /**
     *
     * @param clazz
     * @param bundle
     * @param requestCode
     */
    public void toIActivity(Class clazz , Bundle bundle, int requestCode){
        Intent intent = new Intent(this, clazz);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        if(requestCode == 0){
            startActivity(intent);
        }else {
            startActivityForResult(intent,requestCode);
        }
    }


    @Override
    public void onClick(View v) {

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        System.out.println("onDestroy = " + this);
        AppManager.getAppManager().finishActivity(this);


    }
}
