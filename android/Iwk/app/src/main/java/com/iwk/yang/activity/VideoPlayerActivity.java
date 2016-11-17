package com.iwk.yang.activity;

import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

import com.iwk.yang.bean.Person;
import com.iwk.yang.iwk.R;
import com.iwk.yang.tool.DoubleClick;
import com.iwk.yang.tool.ToastShow;

/**
 * Created by Administrator on 2016/11/15 0015.
 * 本activity用于视频的播放
 */

public class VideoPlayerActivity extends AppCompatActivity {

    private VideoView videoView;
    private ToastShow toastShow;

    //本layout
    private LinearLayout mRLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_videoplayer);

        videoView = (VideoView) findViewById(R.id.vv_player);
        mRLayout = (LinearLayout) findViewById(R.id.ll_videoplayer);
        toastShow = new ToastShow(this);

        //从bundle取传过来的数据
        Bundle bundle = getIntent().getExtras();
        Person person = (Person) bundle.getSerializable("person");
        //设置抬头
        setTitle(person.getTitle());
        playVideo(person.getSrcs());
        //设置事件监听
        videoView.setOnTouchListener(onTouchListener);

    }

    public void playVideo(String url) {
        Log.e("视频路径", url);
        Uri uri = Uri.parse(url);
        videoView.setMediaController(new MediaController(this));
        videoView.setVideoURI(uri);
        videoView.start();
        videoView.requestFocus();
    }

    public View.OnTouchListener onTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            //横竖屏转换
            if (DoubleClick.doubleClick()) {
                if (VideoPlayerActivity.this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
//                    Log.e("info", "landscape"); // 横屏
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                    getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);//显示状态栏
                } else if (VideoPlayerActivity.this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
//                    Log.e("info", "portrait"); // 竖屏
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                    getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);//隐藏状态栏

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
        if (VideoPlayerActivity.this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            Log.e("info", "landscape"); // 横屏
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        } else {
            super.onBackPressed();
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

}
