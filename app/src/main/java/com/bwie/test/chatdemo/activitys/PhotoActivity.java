package com.bwie.test.chatdemo.activitys;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;


import com.bwie.test.chatdemo.R;
import com.squareup.picasso.Picasso;

public class PhotoActivity extends AppCompatActivity {

  private ImageView pinch;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_photo);

    String photoPath = getIntent().getStringExtra("photoPath");
    pinch = (ImageView) findViewById(R.id.pinch); //图片加载
    Picasso.with(this).load(photoPath).into(pinch);
  }
}
