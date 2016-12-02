package com.iwk.yang.iwk;

import android.content.Intent;
import android.net.Uri;
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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.beardedhen.androidbootstrap.BootstrapLabel;
import com.iwk.yang.activity.LoginActivity;
import com.iwk.yang.activity.UploadVideoActivity;
import com.iwk.yang.activity.VideoListLoaderActivity;
import com.iwk.yang.activity.XListViewActivity;
import com.iwk.yang.impl.UserDaoImpl;
import com.iwk.yang.tool.ToastShow;
import com.iwk.yang.volley.MyVolley;
import com.user.entity.User;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private TextView user_introduce;
    private TextView user_nickname;
    private BootstrapButton bt_login;
    private BootstrapButton bt_regist;
    private long mExitTime;
    private ToastShow toastShow;
    private FloatingActionButton fab;

    private BootstrapLabel index_name;

    private UserDaoImpl userDao;
    private View headerView;
    private ImageView leftImageView;

    private String obj_id;//用户id

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //实例化数据库操作
        userDao = new UserDaoImpl(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //初始化MyVolley
        MyVolley.init(MainActivity.this);

        index_name = (BootstrapLabel) findViewById(R.id.index_name);
        bt_login = (BootstrapButton) findViewById(R.id.bt_login);
        bt_regist = (BootstrapButton) findViewById(R.id.bt_regist);
        toastShow = new ToastShow(MainActivity.this);

        fab = (FloatingActionButton) findViewById(R.id.fab);

        fab.setOnClickListener(MyOnClick);
        bt_login.setOnClickListener(MyOnClick);
        bt_regist.setOnClickListener(MyOnClick);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        //右侧菜单头部
        headerView = navigationView.getHeaderView(0);
        leftImageView = (ImageView) headerView.findViewById(R.id.head_img);
        user_introduce = (TextView) headerView.findViewById(R.id.user_introduce);
        user_nickname = (TextView) headerView.findViewById(R.id.user_nickname);
        navigationView.setOnClickListener(MyOnClick);
        navigationView.setNavigationItemSelectedListener(this);
        //初始化本地用户数据
        initData();

    }

    //取本地数据填用户信息
    private void initData() {
        User user = userDao.listOneUser(1);
        if (user != null) {
            index_name.setText(user.getName());
            user_nickname.setText(user.getName());
            user_introduce.setText(user.getIntroduce());
            obj_id=user.getId().toString();
        }

    }

    //查看是否有用戶登陸過
    private boolean isLogin() {
        boolean flag;
        flag = ((userDao.queryRecords() == 0) ? false : true);
        return flag;
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
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
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
        //判斷登陸状态
        if (isLogin()) {
            int id = item.getItemId();
            if (id == R.id.nav_video) {//跳转到视频
                Intent intent = new Intent(MainActivity.this, VideoListLoaderActivity.class);
                startActivity(intent);
            } else if (id == R.id.nav_manage) {
                Intent mgIntent = new Intent(MainActivity.this, XListViewActivity.class);
                Bundle bundle=new Bundle();
                bundle.putString("obj_id",obj_id);
                mgIntent.putExtras(bundle);
                startActivity(mgIntent);

            } else if (id == R.id.nav_info) {
                toastShow.makeText("" + id, Toast.LENGTH_LONG);

            }

            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            drawer.closeDrawer(GravityCompat.START);
        } else {
            toastShow.makeText("请先登陆", Toast.LENGTH_LONG);
        }
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
    public View.OnClickListener MyOnClick = new View.OnClickListener() {
        Intent intent = null;

        @Override
        public void onClick(View v) {
            //判斷登陆状态
            switch (v.getId()) {
                case R.id.fab:
                    if (isLogin()) {
                        intent = new Intent(MainActivity.this, UploadVideoActivity.class);
                        startActivity(intent);
                    } else {
                        toastShow.makeText("请先登陆", Toast.LENGTH_LONG);
                    }
                    break;
                case R.id.bt_login:
                    intent = new Intent(MainActivity.this, LoginActivity.class);
                    //监听返回的startActivity
                    startActivityForResult(intent, 0);
                    break;
                case R.id.bt_regist:
                    //跳转到浏览器
                    String url = getResources().getText(R.string.serverURL) + "reg.html";
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse(url));
                    startActivity(intent);
                    break;
                case R.id.nav_view:
                    toastShow.makeText("侧滑菜单", Toast.LENGTH_SHORT);
                    break;
                default:
                    break;
            }
        }

    };

    /**
     * 接受第二个Activity返回的数据时调用
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == 0) {//返回键返回
            toastShow.makeText("普通返回", Toast.LENGTH_SHORT);
        }
        if (resultCode == 1) {//登陆成功
            Bundle bundle = data.getBundleExtra("data");
            index_name.setText(bundle.getString("nickname"));
            user_nickname.setText(bundle.getString("nickname"));
            user_introduce.setText(bundle.getString("introduce"));
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
