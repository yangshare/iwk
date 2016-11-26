package com.iwk.yang.iwk;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.iwk.yang.activity.UploadVideoActivity;
import com.iwk.yang.activity.VideoListLoaderActivity;
import com.iwk.yang.activity.XListViewActivity;
import com.iwk.yang.tool.ToastShow;
import com.iwk.yang.volley.MyVolley;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener{

    private TextView textView;
    private long mExitTime;
    private ToastShow toastShow;
    private FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //初始化MyVolley
        MyVolley.init(MainActivity.this);
        textView = (TextView) findViewById(R.id.textView);
        toastShow = new ToastShow(MainActivity.this);

        fab = (FloatingActionButton) findViewById(R.id.fab);

        fab.setOnClickListener(MyOnClick);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent mIntent = new Intent(Settings.ACTION_SETTINGS);
            startActivity(mIntent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if (id == R.id.nav_video) {//跳转到视频
            Intent intent = new Intent(MainActivity.this, VideoListLoaderActivity.class);
            startActivity(intent);
//            Toast.makeText(MainActivity.this, "" + id, Toast.LENGTH_LONG).show();
        } else if (id == R.id.nav_manage) {
            XListViewActivity.launch(this);
            toastShow.makeText("" + id, Toast.LENGTH_LONG);

        } else if (id == R.id.nav_info) {
            toastShow.makeText("" + id, Toast.LENGTH_LONG);

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if ((System.currentTimeMillis() - mExitTime) > 2000) {
                toastShow.makeText("再按一次退出程序", Toast.LENGTH_SHORT);
                mExitTime = System.currentTimeMillis();

            } else {
                finish();
            }
            return true;//去掉就会直接退出
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * 自定义点击函数
     */
    public View.OnClickListener  MyOnClick =new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.fab:
                   Intent intent=new Intent(MainActivity.this, UploadVideoActivity.class);
                    startActivity(intent);
                    break;
                default:
                    break;
            }
        }
    };

}
