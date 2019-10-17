package com.example.smartbutler.fragment;
/*
 * 项目名:  SmartButler
 * 包名:    com.example.smartbutler.fragment
 * 文件名:  UserFragment
 * 创建者:  AllenMistake
 * 创建时间: 2019/10/11 21:52
 * 描述:    TODO
 */

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.FileUtils;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import com.example.smartbutler.R;
import com.example.smartbutler.entity.MyUser;
import com.example.smartbutler.ui.CourierActivity;
import com.example.smartbutler.ui.LoginActivity;
import com.example.smartbutler.utils.L;
import com.example.smartbutler.utils.ShareUtils;
import com.example.smartbutler.view.CustomDialog;
import com.yalantis.ucrop.UCrop;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;
import de.hdodenhof.circleimageview.CircleImageView;

import static android.app.Activity.RESULT_OK;
import static com.example.smartbutler.utils.UtilsTools.getImageToShare;
import static com.example.smartbutler.utils.UtilsTools.putImageToShare;

public class UserFragment extends Fragment implements View.OnClickListener {

    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    private Button btn_exit_user;
    private TextView edit_user;
    private EditText et_username;
    private EditText et_sex;
    private EditText et_age;
    private EditText et_desc;

    private Button btn_update_ok;

    private CircleImageView profile_image;

    private CustomDialog dialog;

    private Button btn_camera;
    private Button btn_picture;
    private Button btn_cancel;

    private TextView tv_courier;



    private  String mFile;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user, null);
        findView(view);
        return view;
    }

    private void findView(View view) {
        btn_exit_user = view.findViewById(R.id.btn_exit_user);
        btn_exit_user.setOnClickListener(this);
        edit_user = view.findViewById(R.id.edit_user);
        edit_user.setOnClickListener(this);

        tv_courier = view.findViewById(R.id.tv_courier);
        tv_courier.setOnClickListener(this);

        et_username = view.findViewById(R.id.et_username);
        et_sex = view.findViewById(R.id.et_sex);
        et_age = view.findViewById(R.id.et_age);
        et_desc = view.findViewById(R.id.et_desc);
        btn_update_ok = view.findViewById(R.id.btn_update_ok);
        btn_update_ok.setOnClickListener(this);

        profile_image = view.findViewById(R.id.profile_image);
        profile_image.setOnClickListener(this);


        getImageToShare(getActivity(), profile_image);

        dialog = new CustomDialog(getActivity(), 0, 0, R.layout.dialog_photo, R.style.pop_anim_style, Gravity.BOTTOM, 0);
        // 屏幕外点击无效
        dialog.setCancelable(false);

        btn_camera = dialog.findViewById(R.id.btn_camera);
        btn_camera.setOnClickListener(this);
        btn_picture = dialog.findViewById(R.id.btn_picture);
        btn_picture.setOnClickListener(this);
        btn_cancel = dialog.findViewById(R.id.btn_cancel);
        btn_cancel.setOnClickListener(this);
        // 默认不可以点击， 不可输入
        setEnable(false);


        // 设置具体的值
        MyUser user = BmobUser.getCurrentUser(MyUser.class);
        et_username.setText(user.getUsername());
        et_sex.setText(user.isSex() ? "男" : "女");
        et_age.setText(user.getAge() + "");
        et_desc.setText(user.getDesc());

        // 获取权限
        int permission = ActivityCompat.checkSelfPermission(getActivity(),
                Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(getActivity(), PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE);
        }
    }

    private void setEnable(boolean is) {
        et_username.setEnabled(is);
        et_age.setEnabled(is);
        et_sex.setEnabled(is);
        et_desc.setEnabled(is);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            //退出登录
            case R.id.btn_exit_user:
                MyUser.logOut();

                //Todo 弹出提示框 表示已经退出

                startActivity(new Intent(getActivity(), LoginActivity.class));
                getActivity().finish();
                break;
            // 编辑资料
            case R.id.edit_user:
                setEnable(true);
                btn_update_ok.setVisibility(View.VISIBLE);
                break;
            case R.id.btn_update_ok:
                // 1. 那到输入框的值
                String username = et_username.getText().toString().trim();
                String age = et_age.getText().toString().trim();
                String sex = et_sex.getText().toString().trim();
                String desc = et_desc.getText().toString().trim();

                //2. 判断是否为空
                if (!TextUtils.isEmpty(username) && !TextUtils.isEmpty(age)
                        && !TextUtils.isEmpty(sex)) {
                    // 更新属性
                    MyUser user = BmobUser.getCurrentUser(MyUser.class);
                    user.setUsername(username);
                    user.setAge(Integer.parseInt(age));
                    if (sex.equals("男")) {
                        user.setSex(true);
                    } else {
                        user.setSex(false);
                    }
                    // 简介
                    if (!TextUtils.isEmpty(desc)) {
                        user.setDesc(desc);
                    } else {
                        user.setDesc("这个人很懒，什么都没有留下");
                    }


                    user.update(new UpdateListener() {
                        @Override
                        public void done(BmobException e) {
                            if (e == null) {
                                setEnable(false);
                                btn_update_ok.setVisibility(View.GONE);
                                Toast.makeText(getActivity(), "更新成功", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getActivity(), "修改失败:" + e.toString(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                } else {
                    Toast.makeText(getActivity(), "输入框不能为空", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.profile_image:
                dialog.show();
                break;
            case R.id.btn_cancel:
                dialog.dismiss();
                break;
            case R.id.btn_camera:
                toCamera();
                break;
            case R.id.btn_picture:
                toPicture();
                break;
            case R.id.tv_courier:
                startActivity(new Intent(getActivity(), CourierActivity.class));
        }
    }

    public static final String PHOTO_IMAGE_FILE_NAME = "fileImage.jpg";
    public static final int CAMERA_REQUEST_CODE = 100;
    public static final int IMAGE_REQUEST_CODE = 101;
    public static final int RESULT_REQUEST_CODE = 102;

    private File tempFile = null;

    // 跳转相机
    private void toPicture() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, IMAGE_REQUEST_CODE);
        dialog.dismiss();

    }

    // 跳转相册
    private void toCamera() {

//        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
////        intent.putExtra(MediaStore.EXTRA_OUTPUT,
////                Uri.fromFile(new File(Environment.getExternalStorageDirectory(), PHOTO_IMAGE_FILE_NAME)));
//        Uri contentUri = FileProvider.getUriForFile(getActivity(), getActivity().getPackageName()+".provider", tempFile);
//        startActivityForResult(intent, CAMERA_REQUEST_CODE);
//        dialog.dismiss();
        //用于保存调用相机拍照后所生成的文件
        tempFile = new File(Environment.getExternalStorageDirectory().getPath(), System.currentTimeMillis() + ".png");
        //跳转到调用系统相机
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        //判断版本
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {   //如果在Android7.0以上,使用FileProvider获取Uri
            intent.setFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            Uri contentUri = FileProvider.getUriForFile(getActivity(), getActivity().getPackageName()+".fileprovider", tempFile);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, contentUri);
            Log.e("getPicFromCamera", contentUri.toString());
        } else {    //否则使用Uri.fromFile(file)方法获取Uri
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(tempFile));
        }
        startActivityForResult(intent, CAMERA_REQUEST_CODE);
        dialog.dismiss();


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode != getActivity().RESULT_CANCELED) {
            switch (requestCode) {
                case IMAGE_REQUEST_CODE:
                    //相册返回数据
                    startPhotoZoom(data.getData());
                    break;

                case CAMERA_REQUEST_CODE:

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        Uri contentUri = FileProvider.getUriForFile(getActivity(), getActivity().getPackageName()+".fileprovider", tempFile);
                        startPhotoZoom(contentUri);//开始对图片进行裁剪处理
                    } else {
                        startPhotoZoom(Uri.fromFile(tempFile));//开始对图片进行裁剪处理
                    }
//                    tempFile = new File(Environment.getExternalStorageDirectory(), PHOTO_IMAGE_FILE_NAME);
//                    startPhotoZoom(Uri.fromFile(tempFile));
                    //相机返回
                    break;
                case RESULT_REQUEST_CODE:

                    if (data != null) {
                        // 让刚才选择裁剪得到的图片显示在界面上
                        Bitmap photo =BitmapFactory.decodeFile(mFile);
                        profile_image.setImageBitmap(photo);
                    } else {
                        Log.e("data","data为空");
                    }

//                    L.d("获取data中:" + data.toString());
//
//                    // 有可能点击舍弃
//                    if (data == null) {
//                        Toast.makeText(getActivity(), "获取图片失败", Toast.LENGTH_SHORT).show();
//                    }else{
//                        L.d("获取图片成功！");
//                        // 拿到图片设置
//                        setImageToView(data);
//                        // 既然已经设置图片，原先应该删除
//                        if (tempFile != null) {
//                            tempFile.delete();
//                        }
//                    }
                    break;

            }

//            if (resultCode == RESULT_OK && requestCode == UCrop.REQUEST_CROP) {
//                L.d("获取data中:" + data.toString());
//                // 有可能点击舍弃
//                if (data != null) {
//                    L.d("获取图片成功");
//                    // 拿到图片设置
//                    final Uri resultUri = UCrop.getOutput(data);
//                    try {
//                        setImageToView(resultUri);
//                    } catch (FileNotFoundException e) {
//                        e.printStackTrace();
//                    }
//                    // 既然已经设置图片，原先应该删除
//                    if (tempFile != null) {
//                        tempFile.delete();
//                    }
//                }
//            }
        }

    }

    // 裁剪
    private void startPhotoZoom(Uri uri) {

        if (uri == null) {
            L.e("uri == null");
            return;
        }
//        File outDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
//        if (!outDir.exists()) {
//            outDir.mkdirs();
//        }
//        File outFile = new File(outDir, System.currentTimeMillis() + ".jpg");
//        //裁剪后图片的绝对路径
//
//        Uri destinationUri = Uri.fromFile(outFile);
//        L.d("裁剪成功");
//
//        UCrop.of(uri, destinationUri)
//                .withAspectRatio(1, 1)
//                .withMaxResultSize(20, 20)
//                .start(getActivity());
//
//        Intent intent = new Intent("com.android.camera.action.CROP");
//        // Todo: 增加 MIUI 适配
//        intent.setDataAndType(uri, "image/*");
//        // 设置裁剪
//        intent.putExtra("crop", "true");
//        // 裁剪宽高
//        intent.putExtra("aspectX", 1);
//        intent.putExtra("aspectY", 1);
//        // 裁剪图片质量
//        intent.putExtra("outputX", 20);
//        intent.putExtra("outputY", 20);
//        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
//        // 发送数据
//        intent.putExtra("return-data", true);
//        L.d("裁剪成功");
//        startActivityForResult(intent, RESULT_REQUEST_CODE );

        if (uri == null) {
            Log.i("tag", "The uri is not exist.");
        }
//        tempUri = uri;
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        intent.setDataAndType(uri, "image/*");
        // 设置裁剪
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", 100);
        intent.putExtra("outputY", 100);
        intent.putExtra("return-data", false);
        File out = new File(getPath());
        if (!out.getParentFile().exists()) {
            out.getParentFile().mkdirs();
        }
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(out));
        startActivityForResult(intent, RESULT_REQUEST_CODE);
    }

    //裁剪后的地址
    public  String getPath() {
        //resize image to thumb
        if (mFile == null) {
            mFile = Environment.getExternalStorageDirectory() + "/" +"wode/"+ "outtemp.png";
        }
        return mFile;
    }

    // 设置图片
    private void setImageToView(Intent data) {
        Bundle bundle = data.getExtras();
        if (bundle != null) {
            L.d("设置图片中");
            Bitmap bitmap = bundle.getParcelable("data");
            profile_image.setImageBitmap(bitmap);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        putImageToShare(getActivity(), profile_image);

    }
}
