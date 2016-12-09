package com.iwk.yang.activity;

import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.beardedhen.androidbootstrap.BootstrapButton;
import com.beardedhen.androidbootstrap.BootstrapEditText;
import com.beardedhen.androidbootstrap.BootstrapThumbnail;
import com.iwk.yang.impl.UserDaoImpl;
import com.iwk.yang.iwk.R;
import com.iwk.yang.tool.GetPathFromUri4kitkat;
import com.iwk.yang.tool.ToastShow;
import com.iwk.yang.volley.MyVolley;
import com.iwk.yang.volley.UploadJsonObjectRequest;
import com.user.entity.User;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2016/11/18 0018.
 */

public class UserInfoActivity extends AppCompatActivity implements View.OnClickListener{

    /**
     * XML控件
     */
    private BootstrapButton bt_img_input;//选择图片
    private BootstrapThumbnail tb_showUploadImg;//显示图片

    private BootstrapButton bt_file_submit;//上传文件

    private BootstrapEditText et_nickname;//用户昵称
    private BootstrapEditText et_pwd;//用户密码
    private BootstrapEditText et_email;//用户邮箱
    private BootstrapEditText et_introduce;//用户简介

    private BootstrapButton bt_submit;//提交

    private RequestQueue mRequestQueue;

    private String filePath = null;//图片相對路徑
    private Bitmap imgBitmap = null;//图片bitmap
    //用户字段
    private String backImgPath=null;//封面
    private String nickname;//用户昵称
    private String pwd;//用户密码
    private String email;//用户邮箱
    private String introduce;//用户简介

    /**
     * 引用
     */
    private ToastShow toastShow;
    private UserDaoImpl userDao;
    private User user;
    private View mProgressView;

    /**
     * 创建activity
     *
     * @param savedInstanceState
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        bt_img_input = (BootstrapButton) findViewById(R.id.info_bt_img_input);//选择图片
        tb_showUploadImg = (BootstrapThumbnail) findViewById(R.id.info_tb_showUploadImg);//显示图片

        bt_file_submit = (BootstrapButton) findViewById(R.id.info_bt_file_submit);//上传

        et_nickname = (BootstrapEditText) findViewById(R.id.info_et_nickname);//用户昵称
        et_pwd = (BootstrapEditText) findViewById(R.id.info_et_pwd);//用户密码
        et_email = (BootstrapEditText) findViewById(R.id.info_et_email);//用户邮箱
        et_introduce = (BootstrapEditText) findViewById(R.id.info_et_introduce);//用户简介
        bt_submit = (BootstrapButton) findViewById(R.id.info_bt_submit);//提交

        //加载动画
        mProgressView = findViewById(R.id.info_loading_progress);

        toastShow = new ToastShow(UserInfoActivity.this);
        //点击事件
        bt_img_input.setOnClickListener(this);//选择图片
        bt_file_submit.setOnClickListener(this);//上传文件
        bt_submit.setOnClickListener(this);//提交

        // 1 创建RequestQueue对象
        mRequestQueue = Volley.newRequestQueue(this);
        userDao = new UserDaoImpl(this);

        initView();

    }

    public void initView(){
        user=userDao.listOneUser(1);
        et_nickname.setText(user.getName());//用户昵称
        et_pwd.setText(user.getPwd());//用户密码
        et_email.setText(user.getEmail());//用户邮箱
        et_introduce.setText(user.getIntroduce());//用户简介
        MyVolley.getImage(getResources().getText(R.string.serverURL)+user.getIcons(),tb_showUploadImg);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mRequestQueue.cancelAll(null);
    }

    /**
     * activity点击事件
     *
     * @param v
     */

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.info_bt_img_input://********************************选择图片
//                toastShow.makeText("id=" + v.getId(), Toast.LENGTH_SHORT);
                Intent intentImg = new Intent();
                /* 开启Pictures画面Type设定为image */
                intentImg.setType("image/*");
                /* 使用Intent.ACTION_GET_CONTENT这个Action */
                intentImg.setAction(Intent.ACTION_GET_CONTENT);
                /* 取得相片后返回本画面 */
                startActivityForResult(intentImg, 1);
                break;
            case R.id.info_bt_file_submit://********************************上传
                Log.e("TGA", "filePath=" + filePath);
                if (filePath != null) {
                    uploadImg();
                } else {
                    toastShow.makeText("图片和用户都不能为空", Toast.LENGTH_SHORT);
                }

                break;

            case R.id.info_bt_submit://******************************提交
                submit();
                break;
            default:
                toastShow.makeText("id=" + v.getId(), Toast.LENGTH_SHORT);
                break;
        }
    }

    /**
     * activity回显事件
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case 1:
                showImg(resultCode, data);
                break;
            default:
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);

    }

    /**
     * 预览上传图片
     *
     * @param resultCode
     * @param data
     */
    public void showImg(int resultCode, Intent data) {
        if (resultCode == RESULT_OK && data != null) {
            Uri uri = data.getData();
            ContentResolver cr = this.getContentResolver();
            try {

                //********************************************获取图片路径
                filePath = GetPathFromUri4kitkat.getPath(this, uri);
                //********************************************获取图片路径

                imgBitmap = BitmapFactory.decodeStream(cr.openInputStream(uri));
                /* 将Bitmap设定到ImageView */
                tb_showUploadImg.setImageBitmap(imgBitmap);
            } catch (FileNotFoundException e) {
                Log.e("Exception", e.getMessage(), e);
            }
        }
    }


    /**
     * 上传封面
     */
    public void uploadImg() {
        if (filePath != null) {
            //获取用户列表action
            String url = getResources().getText(R.string.serverURL) + "wUser_androiduploadImg";
            try {
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                //将bitmap一字节流输出 Bitmap.CompressFormat.PNG 压缩格式，100：压缩率，baos：字节流
                if (imgBitmap.getByteCount() > 1 * 1024 * 1024)
                    imgBitmap.compress(Bitmap.CompressFormat.JPEG, 10, baos);
                else
                    imgBitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                baos.close();
                byte[] buffer = baos.toByteArray();
                System.out.println("图片的大小：" + buffer.length);

                //将图片的字节流数据加密成base64字符输出
                String photo = Base64.encodeToString(buffer, 0, buffer.length, Base64.DEFAULT);
                Map<String, String> map = new HashMap<>();

                map.put("imageFileName", filePath.substring(filePath.lastIndexOf("/") + 1));
                map.put("terminalImage", photo);
                UploadJsonObjectRequest uploadJsonObjectRequest = new UploadJsonObjectRequest(url,
                        map,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                try {
                                    Log.i("response返回：", response.toString());
                                    backImgPath=response.get("jsonStr").toString();
                                    if (backImgPath!=null)
                                        toastShow.makeText("上传成功",Toast.LENGTH_SHORT);
                                } catch (Exception e) {
                                    Log.e("json解析异常：", e.getMessage(), e);
                                    toastShow.makeText("json解析异常", Toast.LENGTH_LONG);
                                }
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.e("请求异常：", error.getMessage(), error);
                                toastShow.makeText("网络或者服务器异常", Toast.LENGTH_LONG);

                            }
                        }
                );
                //将StringRequest添加到RequestQueue
                mRequestQueue.add(uploadJsonObjectRequest);
            } catch (Exception e) {
                e.printStackTrace();
            }

        } else {
            toastShow.makeText("未选择图片", Toast.LENGTH_LONG);
        }


    }

    /**
     * 提交
     */
    public void submit() {
        mProgressView.setVisibility(View.VISIBLE);
        user=userDao.listOneUser(1);
        if (et_nickname.length()>0&&backImgPath!=null) {
            //获取控件输入
              nickname=et_nickname.getText().toString().trim();//用户昵称
              pwd=et_pwd.getText().toString().trim();//用户密码
              email=et_email.getText().toString().trim();//用户邮箱
              introduce=et_introduce.getText().toString().trim();//用户简介
            //获取用户列表action
            String url = getResources().getText(R.string.serverURL) + "wUser_updateUser";
            try {
                Map<String, String> map = new HashMap<>();
                map.put("user_id",user.getId().toString());
                map.put("name",nickname);
                map.put("introduce",introduce);
                map.put("pwd",pwd);
                map.put("email",email);
                map.put("terminalImage",backImgPath);
                UploadJsonObjectRequest submitJsonObjectRequest = new UploadJsonObjectRequest(url,
                        map,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                mProgressView.setVisibility(View.GONE);
                                try {
                                    if (response.get("jsonStr").equals("用户修改成功"))
                                    {
                                        user.setName(nickname);
                                        user.setPwd(pwd);
                                        user.setEmail(email);
                                        user.setIntroduce(introduce);
                                        user.setIcons(backImgPath);
                                        //更新本地对象
                                        userDao.updateUser(user);
                                    }
                                    Log.i("submit返回：", response.toString());
                                    toastShow.makeText(response.get("jsonStr").toString(), Toast.LENGTH_LONG);
                                } catch (Exception e) {
                                    Log.e("json解析异常：", e.getMessage(), e);
                                    toastShow.makeText("json解析异常", Toast.LENGTH_LONG);
                                }
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                mProgressView.setVisibility(View.GONE);
                                Log.e("请求异常：", error.getMessage(), error);
                                toastShow.makeText("网络或者服务器异常", Toast.LENGTH_LONG);

                            }
                        }
                );
                //将StringRequest添加到RequestQueue
                mRequestQueue.add(submitJsonObjectRequest);
            } catch (Exception e) {
                e.printStackTrace();
            }

        } else {
            mProgressView.setVisibility(View.GONE);
            toastShow.makeText("信息请填完整", Toast.LENGTH_LONG);
        }


    }
}
