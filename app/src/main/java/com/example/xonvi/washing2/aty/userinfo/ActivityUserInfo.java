package com.example.xonvi.washing2.aty.userinfo;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.PermissionGroupInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.xonvi.washing2.Entity.MyEvent;
import com.example.xonvi.washing2.Entity.UploadPortraitResEntity;
import com.example.xonvi.washing2.R;
import com.example.xonvi.washing2.app.MyApplication;
import com.example.xonvi.washing2.aty.BaseActivity;
import com.example.xonvi.washing2.presenter.UploadPortraitPresenter;
import com.example.xonvi.washing2.util.ContentUriUtil;
import com.example.xonvi.washing2.util.PermissionUtils;
import com.example.xonvi.washing2.util.ToastUtil;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.view.SimpleDraweeView;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.http.Field;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import uk.co.senab.photoview.PhotoView;

/**
 * Created by xonvi on 2017/2/20.
 */


//显示用户信息的页面
public class ActivityUserInfo extends BaseActivity implements View.OnClickListener {

       // 要申请的权限
       private String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
      private AlertDialog dialog;
    //申请写入sd卡权限的请求标记
    private final int REQUEST_WRITE_EXTERNAL_SDCARD=0x111;

    //裁切后的图片
    private Bitmap bitmap=null;
    //选取图片的uri
    private Uri picked_photo=null;
    //从本地相册选取图片的请求码标记
    private final int PICK_ALBUM_PHOTO = 0x1;
    //裁剪图片请求的标记
    private final int CUT_PHOTO = 0x2;
    private View popview;
    private PopupWindow popupWindow;
    private TextView tv_popwin_cancel;
    private TextView tv_popwin_pick_album;
    @BindView(R.id.rl_when_pick_finish)RelativeLayout rl_when_pick_finish;
    @BindView(R.id.rl_aty_userinfo_top)RelativeLayout rl_aty_userinfo_top;
    @BindView(R.id.pv_aty_userinfo_portrait)PhotoView pv_aty_userinfo_portrait;
    @OnClick({R.id.iv_aty_userinfo_back,R.id.iv_aty_userinfo_more,R.id.tv_aty_userinfo_when_pick_finish_cancel,
    R.id.tv_aty_userinfo_when_pick_finish_confirm,R.id.tv_aty_userinfo_when_pick_finish_cut})
    public void click(View view){
        switch (view.getId()){
            case R.id.iv_aty_userinfo_back:
                this.finish();
                break;
            //选取图片
            case R.id.iv_aty_userinfo_more:
                popWindow();
                break;
            //取消选择的图片
            case R.id.tv_aty_userinfo_when_pick_finish_cancel:
                rl_when_pick_finish.setVisibility(View.GONE);
                Glide.with(this).load(MyApplication.getInstance().myUser.getPortrait()).into(pv_aty_userinfo_portrait);
                break;
            //确认设置选择的图片
            //确认就将裁剪后的图片上传到后台
            case R.id.tv_aty_userinfo_when_pick_finish_confirm:
                if(bitmap==null&&picked_photo==null){//允许裁剪后上传或者直接上传
                    ToastUtil.toast("未获取到图片");
                    return;
                }

                uploadPortrait(bitmap,picked_photo);
                break;
            //裁切
            case R.id.tv_aty_userinfo_when_pick_finish_cut:
                if(picked_photo==null){
                    ToastUtil.toast("请从新选取图片");
                    return;
                }
                cut(picked_photo);
                break;
        }

    }


    //上传图片到后台-----------------------------------------------------------------------------------------
    private void uploadPortrait(Bitmap bitmap,Uri uri) {

        int uid = MyApplication.getInstance().myUser.getId();
        RequestBody file = null;
        if(bitmap!=null){//如果裁剪了图片 上传裁剪后的图片文件
             file = RequestBody.create(MediaType.parse("multipart/form-data"),getUpFile(bitmap));
            Log.e("paramupload",uid+"  "+file.contentType().type());
        }else{//如果没有裁剪 上传原图文件
             file = RequestBody.create(MediaType.parse("multipart/form-data"),new File(ContentUriUtil.getPath(this,uri)));
        }



        new UploadPortraitPresenter().uploadPortrait(uid,file)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .subscribe(new Subscriber<UploadPortraitResEntity>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("uploaderror",e.toString());
                    }

                    @Override
                    public void onNext(UploadPortraitResEntity entity) {

                        ToastUtil.toast(entity.getMsg());
                        if(entity.getCode()==1){//设置头像成功
                            EventBus.getDefault().post(new MyEvent(MyEvent.PORTRAIT_UPLOAD_SUCCESS,entity.getPortrait()));
                            ActivityUserInfo.this.finish();
                        }
                    }
                });


    }



    //获取上传的文件----------------------------------------------------------------------
    private File getUpFile(Bitmap bitmap) {

        String path = Environment.getExternalStorageDirectory().getPath();
            Bitmap.CompressFormat format = Bitmap.CompressFormat.JPEG;
            File mypic = new File(path + "/mypic.jpeg");
            Log.e("pathfile",mypic.getAbsolutePath().toString());
            try {
                boolean b = bitmap.compress(format, 100, new FileOutputStream(mypic));
                if(b){
                    ToastUtil.toast("构造文件成功:"+path+"mypic.jpeg");
                  return mypic;
                }else {
                    return  null;
                }
            } catch (FileNotFoundException e) {
                return null;
            }


    }




    //动态权限申请----------------------------------------------------------------------



    //检查当前版本
    private void checkVersion(){
        // 版本判断。当手机系统大于 23 时，才有必要去判断权限是否获取
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

                        // 检查该权限是否已经获取
                        int i = ContextCompat.checkSelfPermission(this, permissions[0]);
                      // 权限是否已经 授权 GRANTED---授权  DINIED---拒绝
                        if (i != PackageManager.PERMISSION_GRANTED) {
                               // 如果没有授予该权限，就去提示用户请求
                              showDialogTipUserRequestPermission();
                            }
                    }
    }

    //提示用户要获取权限
    private void showDialogTipUserRequestPermission() {
        new AlertDialog.Builder(this)
                        .setTitle("存储权限不可用")
                        .setMessage("由于Compus需要获取存储空间，为你存储个人信息；\n否则，您将无法正常使用本软件")
                        .setPositiveButton("立即开启", new DialogInterface.OnClickListener() {
                                @Override
                              public void onClick(DialogInterface dialog, int which) {
                                        startRequestPermission();
                                    }
                             })
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                @Override
                              public void onClick(DialogInterface dialog, int which) {
                                      finish();
                                   }
                            }).setCancelable(false).show();

    }

    //开始请求权限
    private void startRequestPermission() {
        ActivityCompat.requestPermissions(this, permissions, REQUEST_WRITE_EXTERNAL_SDCARD);
    }

    //申请权限请求返回结果
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == REQUEST_WRITE_EXTERNAL_SDCARD) {
                         if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                               if (grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                                        // 判断用户是否 点击了不再提醒。(检测该权限是否还可以申请)
                                       boolean b = shouldShowRequestPermissionRationale(permissions[0]);
                                         if (!b) {
                                               // 用户还是想用我的 APP 的
                                               // 提示用户去应用设置界面手动开启权限
                                                showDialogTipUserGoToAppSettting();
                                            } else
                                                finish();
                                     } else {
                                        ToastUtil.toast("获取权限成功");
                                    }
                           }
                     }
    }

    //提示用户手动开启权限
    private void showDialogTipUserGoToAppSettting() {
        dialog = new AlertDialog.Builder(this)
                        .setTitle("存储权限不可用")
                         .setMessage("请在-应用设置-权限-中，允许Compus使用存储权限来保存用户数据")
                         .setPositiveButton("立即开启", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                      // 跳转到应用设置界面
                                         goToAppSetting();
                                   }


                         })
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                 @Override
                                 public void onClick(DialogInterface dialog, int which) {
                                        finish();
                                    }
                            }).setCancelable(false).show();


    }

    //  跳转到应用设置界面
    private void goToAppSetting() {
        Intent intent = new Intent();
                intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                Uri uri = Uri.fromParts("package", getPackageName(), null);
                intent.setData(uri);
                startActivityForResult(intent, 123);

    }




    //请求裁切图片------------------------------------------------------------------------------------------------------
    private void cut(Uri uri) {
        // 裁剪图片意图
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        // 裁剪框的比例，1：1
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // 裁剪后输出图片的尺寸大小
        intent.putExtra("outputX", 250);
        intent.putExtra("outputY", 250);

        intent.putExtra("outputFormat", "jpeg");// 图片格式
        intent.putExtra("noFaceDetection", true);// 取消人脸识别
        intent.putExtra("return-data", true);
        // 开启一个带有返回值的Activity，请求码为PHOTO_REQUEST_CUT
        startActivityForResult(intent, CUT_PHOTO);
    }


    //弹出窗----------------------------------------------------------------------
    private void popWindow() {
        if(popupWindow==null||popupWindow==null) {
            popview = getLayoutInflater().inflate(R.layout.popwin_aty_userinfo, null);
            popupWindow = new PopupWindow(popview, WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT,true);
        }
        tv_popwin_cancel = (TextView) popview.findViewById(R.id.tv_popwin_cancel);
        tv_popwin_pick_album = (TextView) popview.findViewById(R.id.tv_popwin_pick_album);
        tv_popwin_cancel.setOnClickListener(this);
        tv_popwin_pick_album.setOnClickListener(this);
        popupWindow.setTouchable(true);
        popupWindow.setInputMethodMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);//防止软件盘被弹窗遮挡
        popupWindow.setOutsideTouchable(true);
        popupWindow.setBackgroundDrawable(new BitmapDrawable(getResources(),(Bitmap) null));
        popupWindow.showAtLocation(rl_aty_userinfo_top,Gravity.BOTTOM,0,0);

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userinfo);
        ButterKnife.bind(this);

        initView();
    }


    @Override
    protected void onResume() {
        super.onResume();
        //在用户可见的时候进行权限检查
        checkVersion();
    }

    //初始化视图----------------------------------------------------------------------
    private void initView() {
        pv_aty_userinfo_portrait.setZoomable(true);
        Glide.with(this).load(MyApplication.getInstance().myUser.getPortrait()).into(pv_aty_userinfo_portrait);

    }


    //弹出窗的菜单点击事件----------------------------------------------------------------------
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            //从本地相册选取图片
            case R.id.tv_popwin_pick_album:
                ToastUtil.toast("选取本地图片");
                Intent pick = new Intent();
                pick.setType("image/*");
                pick.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(pick,PICK_ALBUM_PHOTO);
                break;
            //取消弹出窗
            case R.id.tv_popwin_cancel:
                popupWindow.dismiss();
                break;
        }
    }

    //请求选取图片和裁剪图片action的返回结果----------------------------------------------------------------------
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //选取本地图片的返回结果
        if(requestCode==PICK_ALBUM_PHOTO&&resultCode==RESULT_OK){
            //保存选取的图片uri
            picked_photo = data.getData();
            Log.e("pick",data.getData().toString());
            //显示选取的照片
            Glide.with(this).load(data.getData()).into(pv_aty_userinfo_portrait);
            rl_when_pick_finish.setVisibility(View.VISIBLE);
            if(popupWindow.isShowing()){
                popupWindow.dismiss();
            }

        }
        //裁剪图片的返回结果
        else if(requestCode==CUT_PHOTO&&resultCode==RESULT_OK) {

            //裁剪返回的是一个bitmap类型的对象 从Extraa里拿到(而非给你返回一个uri)
            bitmap = data.getExtras().getParcelable("data");
            Log.e("bitmap", (bitmap == null) + " ");
            if (popupWindow.isShowing()) {
                popupWindow.dismiss();
            }
            pv_aty_userinfo_portrait.setImageBitmap(bitmap);
        }



//            String path = Environment.getExternalStorageDirectory().getPath();
//            Bitmap.CompressFormat format = Bitmap.CompressFormat.JPEG;
//            File mypic = new File(path + "/mypic.jpeg");
//            Log.e("pathfile",mypic.getAbsolutePath().toString());
//            try {
//                boolean b = data1.compress(format, 100, new FileOutputStream(mypic));
//                if(b){
//                    ToastUtil.toast("裁剪成功:"+path+"mypic.jpeg");
//                    Glide.with(this).load(mypic).into(pv_aty_userinfo_portrait);
//                }
//            } catch (FileNotFoundException e) {
//                e.printStackTrace();
//            }

            //---------------------------------------------动态权限申请--------------------------------------------------------------
            else if (requestCode == 123) {

                            if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                   // 检查该权限是否已经获取
                                    int i = ContextCompat.checkSelfPermission(this, permissions[0]);
                                   // 权限是否已经 授权 GRANTED---授权  DINIED---拒绝
                                    if (i != PackageManager.PERMISSION_GRANTED) {
                                         // 提示用户应该去应用设置界面手动开启权限
                                             showDialogTipUserGoToAppSettting();
                                     } else {
                                           if (dialog != null && dialog.isShowing()) {
                                                    dialog.dismiss();
                                                }
                                             ToastUtil.toast("权限获取成功");
                                      }
                              }
                        }


    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        bitmap=null;
    }
}
