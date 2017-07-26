package com.bwie.test.chatdemo.activitys;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;

import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.bwie.test.chatdemo.MyApplication;
import com.bwie.test.chatdemo.MyTost;

import com.bwie.test.chatdemo.R;
import com.bwie.test.chatdemo.base.BaseMvpActivity;
import com.bwie.test.chatdemo.bean.RegisterBean;
import com.bwie.test.chatdemo.bean.UploadPhotoBean;
import com.bwie.test.chatdemo.core.JNICore;
import com.bwie.test.chatdemo.core.SortUtils;
import com.bwie.test.chatdemo.mpersenter.RegistedPersenter;
import com.bwie.test.chatdemo.mview.RegistedView;
import com.bwie.test.chatdemo.network.BaseObserver;
import com.bwie.test.chatdemo.network.RetrofitManager;
import com.bwie.test.chatdemo.utils.ImageResizeUtils;
import com.bwie.test.chatdemo.utils.PreferencesUtils;
import com.bwie.test.chatdemo.utils.SDCardUtils;
import com.google.gson.Gson;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnNeverAskAgain;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.OnShowRationale;
import permissions.dispatcher.PermissionRequest;
import permissions.dispatcher.RuntimePermissions;

import static com.bwie.test.chatdemo.utils.ImageResizeUtils.copyStream;

/**
 * 作者： 武星宇
 * 日期： 2017/7/6.
 * 类用途：
 */
@RuntimePermissions
public class RegistedFour extends BaseMvpActivity< RegistedView, RegistedPersenter > implements RegistedView {

  private Button faceCrame;
  private Button faceLocation;

  static final int INTENTFORCAMERA = 1;
  static final int INTENTFORPHOTO = 2;
  public static final int RESIZE_PIC = 720;


  public String LocalPhotoName;
  private ImageView imageFac;
  private Bitmap bitmap;
  private Bitmap decodeFile;
  private File file;
  private Bitmap decodeFile1;
  private Button completeFace;

  public String createLocalPhotoName() {
    LocalPhotoName = System.currentTimeMillis() + "wxy.jpg";
    return LocalPhotoName;
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.upface);
    initView();//加载控件
    onclickevent(); //点击事件


  }

  private void onclickevent() {
    faceCrame.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        /**
         * 打开相机
         */
        toCheckPermissionCamera();
      }
    });
    /**
     * 本地
     */
    faceLocation.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        toPhoto();
      }
    });
    completeFace.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Toast.makeText(RegistedFour.this, "正在跳转到登陆页面请稍后", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(RegistedFour.this, Login.class));
      }
    });
  }

  private void initView() {
    faceCrame = (Button) findViewById(R.id.face_carema); //拍照上传
    faceLocation = (Button) findViewById(R.id.face_location); //本地上传
    imageFac = (ImageView) findViewById(R.id.registed_face);//头像
    completeFace = (Button) findViewById(R.id.face_complete);//完成


  }

  public void toCheckPermissionCamera() {
    RegistedFourPermissionsDispatcher.toCameraWithCheck(this);
  }

  @Override
  public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
    super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    RegistedFourPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
  }

  /**
   * 打开系统相机
   */
  @NeedsPermission({Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA})
  public void toCamera() {
    try {
      Intent intentNow = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
      intentNow.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(SDCardUtils.getMyFaceFile(createLocalPhotoName())));
      startActivityForResult(intentNow, INTENTFORCAMERA);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @OnShowRationale({Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA})
  public void showRationaleForCamera(final PermissionRequest request) {
    new AlertDialog.Builder(this)
            .setMessage("需要打开您的相机来上传照片并保存照片")
            .setPositiveButton("ok", new DialogInterface.OnClickListener() {
              @Override
              public void onClick(DialogInterface dialog, int which) {
                request.proceed();
              }
            })
            .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
              @Override
              public void onClick(DialogInterface dialog, int which) {
                request.cancel();
              }
            })
            .show();
  }


  @OnPermissionDenied({Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA})
  public void onDenied() {
    Toast.makeText(this, "权限被拒绝", Toast.LENGTH_SHORT).show();

  }


  @OnNeverAskAgain({Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA})
  public void onNeverAsyAgain() {
    Toast.makeText(this, "不再提示", Toast.LENGTH_SHORT).show();
  }

  /**
   * 打开相册
   */
  public void toPhoto() {
    try {
      createLocalPhotoName();
      Intent getAlbum = new Intent(Intent.ACTION_GET_CONTENT);
      getAlbum.setType("image/*");
      startActivityForResult(getAlbum, INTENTFORPHOTO);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    switch (requestCode) {
      case INTENTFORPHOTO:
        //相册
        try {
          Uri originalUri = data.getData();
          File f = null;
          if (originalUri != null) {
            f = new File(SDCardUtils.photoCacheDir, LocalPhotoName);
            String[] proj = {MediaStore.Images.Media.DATA};
            Cursor actualimagecursor = this.getContentResolver().query(originalUri, proj, null, null, null);
            if (actualimagecursor == null) {
              if (originalUri.toString().startsWith("file:")) {
                File file = new File(originalUri.toString().substring(7, originalUri.toString().length()));
                if (!file.exists()) {
                  //地址包含中文编码的地址做utf-8编码
                  originalUri = Uri.parse(URLDecoder.decode(originalUri.toString(), "UTF-8"));
                  file = new File(originalUri.toString().substring(7, originalUri.toString().length()));
                }
                FileInputStream inputStream = new FileInputStream(file);
                FileOutputStream outputStream = new FileOutputStream(f);
                copyStream(inputStream, outputStream);
              }
            } else {
              // 系统图库
              int actual_image_column_index = actualimagecursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
              actualimagecursor.moveToFirst();
              String img_path = actualimagecursor.getString(actual_image_column_index);
              if (img_path == null) {
                InputStream inputStream = this.getContentResolver().openInputStream(originalUri);
                FileOutputStream outputStream = new FileOutputStream(f);
                copyStream(inputStream, outputStream);
              } else {
                file = new File(img_path);
                FileInputStream inputStream = new FileInputStream(file);
                FileOutputStream outputStream = new FileOutputStream(f);
                copyStream(inputStream, outputStream);
              }

            }
            Bitmap bitmap = ImageResizeUtils.resizeImage(f.getAbsolutePath(), RESIZE_PIC);
            decodeFile1 = BitmapFactory.decodeFile("" + file);
            FileOutputStream fos = new FileOutputStream(f.getAbsolutePath());
            if (bitmap != null) {
              if (bitmap.compress(Bitmap.CompressFormat.JPEG, 85, fos)) {
                fos.close();
                fos.flush();
              }
              if (!bitmap.isRecycled()) {
                bitmap.isRecycled();
              }
              uploadFile(f, decodeFile1);

            }

          }
        } catch (Exception e) {
          e.printStackTrace();

        }
        break;
      case INTENTFORCAMERA:
//                相机
        try {
          //file 就是拍照完 得到的原始照片
          File file = new File(SDCardUtils.photoCacheDir, LocalPhotoName);
          bitmap = ImageResizeUtils.resizeImage(file.getAbsolutePath(), RESIZE_PIC);
          decodeFile = BitmapFactory.decodeFile(file + "");
          FileOutputStream fos = new FileOutputStream(file.getAbsolutePath());
          if (this.bitmap != null) {
            if (this.bitmap.compress(Bitmap.CompressFormat.JPEG, 85, fos)) {
              fos.close();
              fos.flush();
            }
            if (!this.bitmap.isRecycled()) {
              //通知系统 回收bitmap
              this.bitmap.isRecycled();
            }
            uploadFile(file, decodeFile);
          }

        } catch (Exception e) {
          e.printStackTrace();
        }
        break;
    }

  }

  private void uploadFile(File file, final Bitmap mbitmap) {
    if (!file.exists()) {
      MyTost.makeText(this, " 照片不存在", Toast.LENGTH_SHORT);
      return;
    }
    String[] arr = file.getAbsolutePath().split("/");
    RequestBody requestFile =
            RequestBody.create(MediaType.parse("multipart/form-data"), file);
    long ctimer = System.currentTimeMillis();
   if(bitmap!=null){
     int width = bitmap.getWidth();
     int height = bitmap.getHeight();
     final Map< String, String > map = new HashMap< String, String >();
     map.put("user.currenttimer", ctimer + "");
     map.put("user.picWidth", PreferencesUtils.getValueByKey(RegistedFour.this, "picWidth", width + ""));
     map.put("user.picHeight", PreferencesUtils.getValueByKey(RegistedFour.this, "picHeight", height + ""));
     String sign = JNICore.getSign(SortUtils.getMapResult(SortUtils.sortString(map)));
     map.put("user.sign", sign);
     MultipartBody body = new MultipartBody.Builder()
             .addFormDataPart("image", arr[arr.length - 1], requestFile)
             .build();
     RetrofitManager.uploadPhoto(body, map, new BaseObserver() {
       @Override
       public void onSuccess(String result) {
         try {
           Gson gson = new Gson();
           UploadPhotoBean bean = gson.fromJson(result, UploadPhotoBean.class);
           if (bean.getResult_code() == 200) {
             MyTost.makeText(RegistedFour.this, "上传成功", Toast.LENGTH_SHORT);
             imageFac.setImageBitmap(mbitmap);


           }
//          finish();
         } catch (Exception e1) {
           e1.printStackTrace();
         }


       }

       @Override
       public void onFailed(int code) {
         try {
           MyTost.makeText(MyApplication.getApplication(), "", Toast.LENGTH_SHORT);
         } catch (Exception e) {
           e.printStackTrace();
         }

       }
     });
   }else{

     final Map< String, String > map = new HashMap< String, String >();
     map.put("user.currenttimer", ctimer + "");
     map.put("user.picWidth", PreferencesUtils.getValueByKey(RegistedFour.this, "picWidth", 150+""));
     map.put("user.picHeight", PreferencesUtils.getValueByKey(RegistedFour.this, "picHeight", 200 + ""));
     String sign = JNICore.getSign(SortUtils.getMapResult(SortUtils.sortString(map)));
     map.put("user.sign", sign);
     MultipartBody body = new MultipartBody.Builder()
             .addFormDataPart("image", arr[arr.length - 1], requestFile)
             .build();
     RetrofitManager.uploadPhoto(body, map, new BaseObserver() {
       @Override
       public void onSuccess(String result) {
         try {
           Gson gson = new Gson();
           UploadPhotoBean bean = gson.fromJson(result, UploadPhotoBean.class);
           if (bean.getResult_code() == 200) {
             MyTost.makeText(RegistedFour.this, "上传成功", Toast.LENGTH_SHORT);
             imageFac.setImageBitmap(mbitmap);


           }
//          finish();
         } catch (Exception e1) {
           e1.printStackTrace();
         }


       }

       @Override
       public void onFailed(int code) {
         try {
           MyTost.makeText(MyApplication.getApplication(), "", Toast.LENGTH_SHORT);
         } catch (Exception e) {
           e.printStackTrace();
         }

       }
     });
   }


  }


  @Override
  public void checkPhoneNumber(int type) {
  }

  @Override
  public void showTimer() {
  }

  @Override
  public void regithedSueecssView(RegisterBean registerBean) {
  }

  @Override
  public void registedFailed() {
  }

  @Override
  public void viewOnSuccess(String result) {
  }

  @Override
  public void viewOnFailed(int code) {
  }

  @Override
  public RegistedPersenter initPersenter() {
    return new RegistedPersenter();
  }
}
