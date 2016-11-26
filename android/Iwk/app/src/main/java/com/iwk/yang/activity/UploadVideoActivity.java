package com.iwk.yang.activity;

import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.beardedhen.androidbootstrap.BootstrapButton;
import com.beardedhen.androidbootstrap.BootstrapDropDown;
import com.beardedhen.androidbootstrap.BootstrapEditText;
import com.beardedhen.androidbootstrap.BootstrapThumbnail;
import com.iwk.yang.iwk.R;
import com.iwk.yang.tool.GetPathFromUri4kitkat;
import com.iwk.yang.tool.ToastShow;
import com.iwk.yang.volley.UploadJsonObjectRequest;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2016/11/18 0018.
 */

public class UploadVideoActivity extends AppCompatActivity implements View.OnClickListener, BootstrapDropDown.OnDropDownItemClickListener {

    /**
     * XML控件
     */
    private BootstrapButton bt_img_input;//选择图片
    private BootstrapThumbnail tb_showUploadImg;//显示图片
    private BootstrapButton bt_video_input;//选择文件
    private TextView videoView;//显示视频路径

    private BootstrapButton bt_file_submit;//上传文件

    private BootstrapEditText et_title;//视频标题
    private BootstrapDropDown dd_types;//视频类型
    private BootstrapEditText et_introduce;//视频简介

    private BootstrapButton bt_upload_submit;

    private RequestQueue mRequestQueue;

    private String filePath = null;//图片相對路徑
    private Bitmap imgBitmap = null;//图片bitmap
    private String videoFilePath = null;//视频相對路徑
    private InputStream videoBitmap = null;//视频bitmap
    //视频字段
    private String title=null;//标题
    private String types=null;//类型
    private String introduce=null;//简介
    private String author=null;//作者
    private String obj_id=null;//作者id
    private String backImgPath=null;//封面
    private String backVideoPath=null;//视频路径

    /**
     * 引用
     */
    private ToastShow toastShow;

    /**
     * 创建activity
     *
     * @param savedInstanceState
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uploadvideo);

        bt_img_input = (BootstrapButton) findViewById(R.id.bt_img_input);//选择图片
        tb_showUploadImg = (BootstrapThumbnail) findViewById(R.id.tb_showUploadImg);//显示图片
        bt_video_input = (BootstrapButton) findViewById(R.id.bt_video_input);//选择文件
        videoView = (TextView) findViewById(R.id.vv_showUploadVideo);

        bt_file_submit = (BootstrapButton) findViewById(R.id.bt_file_submit);//上传

        et_title = (BootstrapEditText) findViewById(R.id.et_title);//视频标题
        dd_types = (BootstrapDropDown) findViewById(R.id.dd_types);//视频类型
        et_introduce = (BootstrapEditText) findViewById(R.id.et_introduce);//视频简介
        bt_upload_submit = (BootstrapButton) findViewById(R.id.bt_upload_submit);//提交

        toastShow = new ToastShow(UploadVideoActivity.this);
        //点击事件
        bt_img_input.setOnClickListener(this);//选择图片
        bt_video_input.setOnClickListener(this);//选择文件
        dd_types.setOnDropDownItemClickListener(this);//选择视频类型
        bt_file_submit.setOnClickListener(this);//上传文件
        bt_upload_submit.setOnClickListener(this);//提交

        // 1 创建RequestQueue对象
        mRequestQueue = Volley.newRequestQueue(this);


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
            case R.id.bt_img_input://********************************选择图片
//                toastShow.makeText("id=" + v.getId(), Toast.LENGTH_SHORT);
                Intent intentImg = new Intent();
                /* 开启Pictures画面Type设定为image */
                intentImg.setType("image/*");
                /* 使用Intent.ACTION_GET_CONTENT这个Action */
                intentImg.setAction(Intent.ACTION_GET_CONTENT);
                /* 取得相片后返回本画面 */
                startActivityForResult(intentImg, 1);
                break;
            case R.id.bt_video_input://*******************************选择视频
//                toastShow.makeText("id=" + v.getId(), Toast.LENGTH_SHORT);
                Intent intentVideo = new Intent();
                /* 开启Pictures画面Type设定为image */
                intentVideo.setType("video/*");
                /* 使用Intent.ACTION_GET_CONTENT这个Action */
                intentVideo.setAction(Intent.ACTION_GET_CONTENT);
                /* 取得相片后返回本画面 */
                startActivityForResult(intentVideo, 2);
                break;
            case R.id.bt_file_submit://********************************上传
                Log.e("TGA", "filePath=" + filePath + ",videoFilePath=" + videoFilePath);
                if (filePath != null && videoFilePath != null) {
                    uploadImg();
                    uploadVideo();
                } else {
                    toastShow.makeText("图片和视频都不能为空", Toast.LENGTH_SHORT);
                }

                break;
            case R.id.dd_types://********************************选择视频类型
                toastShow.makeText("id=" + v.getId(), Toast.LENGTH_SHORT);
                break;
            case R.id.bt_upload_submit://******************************提交
                toastShow.makeText("id=" + v.getId(), Toast.LENGTH_SHORT);
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
            case 2:
                showVideo(resultCode, data);
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
                et_title.setText(filePath.substring(filePath.lastIndexOf("/") + 1));
            } catch (FileNotFoundException e) {
                Log.e("Exception", e.getMessage(), e);
            }
        }
    }

    /**
     * 预览上传视频
     *
     * @param resultCode
     * @param data
     */
    public void showVideo(int resultCode, Intent data) {
        if (resultCode == RESULT_OK && data != null) {
            Uri uri = data.getData();
            ContentResolver cr = this.getContentResolver();
            try {

                //********************************************获取视频路径
                videoFilePath = GetPathFromUri4kitkat.getPath(this, uri);
                //********************************************获取视频路径
                videoBitmap = cr.openInputStream(uri);

                videoView.setText(videoFilePath);
            } catch (Exception e) {
                Log.e("Exception", e.getMessage(), e);
            }
        }
    }

    /**
     * 上传封面
     */
    public void uploadImg() {
        if (filePath != null) {
            //获取视频列表action
            String url = getResources().getText(R.string.serverURL) + "wVideo_uploadImg";
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
     * 上传视频
     */
    public void uploadVideo() {
        if (videoFilePath != null) {
            //获取视频列表action
            String url = getResources().getText(R.string.serverURL) + "wVideo_uploadVideo";
            try {
                File file = new File(videoFilePath);
                String file_name = file.getName();
                FileInputStream in = new FileInputStream(file);
                byte[] buffer = new byte[(int) file.length()];
                int length=in.read(buffer);
                in.close();

                //将图片的字节流数据加密成base64字符输出
                String videoStr = Base64.encodeToString(buffer, 0, length, Base64.DEFAULT);
                Map<String, String> map = new HashMap<>();

                map.put("uploadFileName", videoFilePath.substring(videoFilePath.lastIndexOf("/") + 1));
                map.put("terminalUpload", videoStr);
                UploadJsonObjectRequest uploadJsonObjectRequest1 = new UploadJsonObjectRequest(url,
                        map,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                try {
                                    Log.i("response返回：", response.toString());
                                    backVideoPath=response.get("jsonStr").toString();
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
                mRequestQueue.add(uploadJsonObjectRequest1);
            } catch (Exception e) {
                e.printStackTrace();
            }

        } else {
            toastShow.makeText("未选择视频", Toast.LENGTH_LONG);
        }


    }

    /**
     * 下拉框项点击事件
     *
     * @param parent
     * @param v
     * @param id
     */

    @Override
    public void onItemClick(ViewGroup parent, View v, int id) {
        dd_types.setText(dd_types.getDropdownData()[id + 1]);
    }

    public static String getMimeType(String filePath) {
        MediaMetadataRetriever mmr = new MediaMetadataRetriever();
        String mime = "text/plain";
        if (filePath != null) {
            try {
                mmr.setDataSource(filePath);
                mime = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_MIMETYPE);
            } catch (IllegalStateException e) {
                return mime;
            } catch (IllegalArgumentException e) {
                return mime;
            } catch (RuntimeException e) {
                return mime;
            }
        }
        return mime;
    }
}
