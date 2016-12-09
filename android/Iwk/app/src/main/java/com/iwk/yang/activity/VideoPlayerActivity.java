package com.iwk.yang.activity;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.beardedhen.androidbootstrap.BootstrapButton;
import com.beardedhen.androidbootstrap.BootstrapEditText;
import com.iwk.yang.bean.Person;
import com.iwk.yang.impl.UserDaoImpl;
import com.iwk.yang.iwk.R;
import com.iwk.yang.tool.DoubleClick;
import com.iwk.yang.tool.ToastShow;
import com.iwk.yang.volley.MyVolley;
import com.iwk.yang.volley.UploadJsonObjectRequest;
import com.user.entity.User;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2016/11/15 0015.
 * 本activity用于视频的播放
 */

public class VideoPlayerActivity extends Activity implements View.OnClickListener {

    private VideoView videoView;
    private ToastShow toastShow;
    private ImageView imageView;
    private ImageButton imageButton;
    private TextView tv_videoIntroduce;
    private BootstrapButton bt_comment_submit;
    private BootstrapEditText et_comment_content;

    private String video_Id;

    //本layout
    private LinearLayout mRLayout;
    private LinearLayout ll_videoplayer_txt;

    private RequestQueue mRequestQueue;
    private UserDaoImpl userDao;
    private Person person;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_LEFT_ICON);//去掉标题栏 第一种方法
        setContentView(R.layout.activity_videoplayer);

        // 1 创建RequestQueue对象
        mRequestQueue = Volley.newRequestQueue(this);
        userDao = new UserDaoImpl(this);

        videoView = (VideoView) findViewById(R.id.vv_fullplayer);
        imageView = (ImageView) findViewById(R.id.iv_videopic);
        tv_videoIntroduce = (TextView) findViewById(R.id.tv_videoIntroduce);
        imageButton = (ImageButton) findViewById(R.id.ib_videoPlay);
        mRLayout = (LinearLayout) findViewById(R.id.ll_videoplayer);
        ll_videoplayer_txt = (LinearLayout) findViewById(R.id.ll_videoplayer_txt);

        bt_comment_submit = (BootstrapButton) findViewById(R.id.bt_comment_submit);
        bt_comment_submit.setOnClickListener(this);
        et_comment_content = (BootstrapEditText) findViewById(R.id.et_comment_content);

        toastShow = new ToastShow(this);

        initView();


    }

    /**
     * 初始化
     */
    public void initView() {
        //从bundle取传过来的数据
        Bundle bundle = getIntent().getExtras();
        person = (Person) bundle.getSerializable("person");
        //设置封面
        MyVolley.getImage(person.getPic(), imageView);
        //设置视频路径
        playVideo(person.getSrcs());
        video_Id = person.getId().toString();
        //设置视频简介
        tv_videoIntroduce.setText("         " + person.getIntroduce());
        //设置事件监听
        videoView.setOnTouchListener(onTouchListener);
        imageView.setOnClickListener(this);
        imageButton.setOnClickListener(this);

        submitClicks();
    }

    public void playVideo(String url) {
        Log.e("视频路径", url);
        Uri uri = Uri.parse(url);
        videoView.setMediaController(new MediaController(this));
        videoView.setVideoURI(uri);
        videoView.requestFocus();
    }

    public View.OnTouchListener onTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            //横竖屏转换
            if (DoubleClick.doubleClick()) {
                if (VideoPlayerActivity.this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
                    chang2PORTRAIT();
                } else if (VideoPlayerActivity.this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
                    chang2LANDSCAPE();

                }

                toastShow.makeText("双击", Toast.LENGTH_SHORT);
            }

            return false;
        }
    };


    @Override
    protected void onStop() {
        super.onStop();
    }

    /**
     * 判断横屏回退竖屏，竖屏返回
     */
    @Override
    public void onBackPressed() {
        if (videoView.getVisibility() == View.GONE) {
            super.onBackPressed();
        } else {
            ll_videoplayer_txt.setVisibility(View.VISIBLE);
            videoView.stopPlayback();
            videoView.setVisibility(View.GONE);
        }


        if (VideoPlayerActivity.this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            chang2PORTRAIT();
        }

    }

    /**
     * 重写onConfigurationChanged使得activity不重调onCreate
     *
     * @param newConfig
     */
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    public void chang2PORTRAIT() {
        Log.e("info", "landscape"); // 横屏
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);//显示状态栏
    }

    public void chang2LANDSCAPE() {
        Log.e("info", "portrait"); // 竖屏
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);//隐藏状态栏
    }

    /**
     * 本activity点击事件
     *
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ib_videoPlay:
                ll_videoplayer_txt.setVisibility(View.GONE);
                videoView.setVisibility(View.VISIBLE);
                videoView.start();
                break;
            case R.id.iv_videopic:
                break;
            case R.id.bt_comment_submit:
                submitComment();
                break;
            default:
                break;
        }
    }

    /**
     * 增加点击次数
     */
    public void submitClicks() {
        //获取视频列表action
        String url = getResources().getText(R.string.serverURL) + "wVideo_addClicks";
        try {
            Map<String, String> map = new HashMap<>();
            map.put("video_Id", video_Id);
            UploadJsonObjectRequest submitJsonObjectRequest = new UploadJsonObjectRequest(url,
                    map,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                Log.i("submit返回：", response.toString());
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
            mRequestQueue.add(submitJsonObjectRequest);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    /**
     * 提交评论
     */
    public void submitComment() {
        User user = userDao.listOneUser(1);
        if (et_comment_content.getText().length() > 0) {
            //获取视频列表action
            String url = getResources().getText(R.string.serverURL) + "wComments_addComments";
            try {
                Map<String, String> map = new HashMap<>();
                map.put("contents", et_comment_content.getText().toString());
                map.put("author", user.getName());
                map.put("objId", user.getId().toString());
                map.put("types", "视频");
                map.put("typesId", person.getId().toString());// 视频编号或帖子编号
                UploadJsonObjectRequest scJsonObjectRequest = new UploadJsonObjectRequest(url,
                        map,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                try {
                                    Log.i("submit返回：", response.toString());
                                    toastShow.makeText(response.get("jsonStr").toString(), Toast.LENGTH_LONG);
                                    if (response.get("jsonStr").equals("评论添加成功")) {
                                        et_comment_content.setText("");
                                    }
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
                mRequestQueue.add(scJsonObjectRequest);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            toastShow.makeText("先写点再提交", Toast.LENGTH_SHORT);
        }


    }


}
