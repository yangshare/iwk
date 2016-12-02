package com.iwk.yang.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.iwk.yang.adapter.SwipeListViewAdapter;
import com.iwk.yang.bean.Person;
import com.iwk.yang.iwk.R;
import com.iwk.yang.tool.ToastShow;
import com.iwk.yang.tool.UrlAppendParameter;
import com.iwk.yang.volley.MyJsonObjectRequest;
import com.iwk.yang.widget.XListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * XListView demo
 *
 * @author markmjw
 * @date 2013-10-08
 */
public class XListViewActivity extends AppCompatActivity implements XListView.IXListViewListener {
    private XListView mListView;

    private ArrayAdapter<String> mAdapter;
    private SwipeListViewAdapter swipeListViewAdapter;
    private ArrayList<Person> items = new ArrayList<Person>();
    private Handler mHandler;
    private ToastShow toastShow;

    private View mProgressView;//加载动画
    private RequestQueue mRequestQueue;
    private String obj_id;//用户id


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_list_view);
        Bundle bundle=getIntent().getExtras();
        obj_id=bundle.getString("obj_id");
        //加载动画
        mProgressView = findViewById(R.id.manager_loading_progress);
        toastShow = new ToastShow(this);

        // 1 创建RequestQueue对象
        mRequestQueue = Volley.newRequestQueue(this);
        LoadVideoList();
        initView();
    }

    private void initView() {
        mHandler = new Handler();

        mListView = (XListView) findViewById(R.id.list_view);
        mListView.setPullRefreshEnable(true);
        mListView.setPullLoadEnable(true);
        mListView.setAutoLoadEnable(true);
        mListView.setXListViewListener(this);
        mListView.setRefreshTime(getTime());

    }

//    @Override
//    public void onWindowFocusChanged(boolean hasFocus) {
//        super.onWindowFocusChanged(hasFocus);
//
//        if (hasFocus) {
//            mListView.autoRefresh();
//        }
//    }

    @Override
    public void onRefresh() {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                items.clear();
                LoadVideoList();
//                mAdapter = new ArrayAdapter<String>(XListViewActivity.this, R.layout.vw_list_item,
//                        items);
//                mListView.setAdapter(mAdapter);
                swipeListViewAdapter = new SwipeListViewAdapter(XListViewActivity.this, items);
                mListView.setAdapter(swipeListViewAdapter);
                onLoad();
            }
        }, 2500);
    }

    @Override
    public void onLoadMore() {
        mListView.stopLoadMore();
        toastShow.makeText("没有更多", Toast.LENGTH_SHORT);
//        mHandler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//
//                toastShow.makeText("没有更多", Toast.LENGTH_SHORT);
////                geneItems();
////                swipeListViewAdapter.notifyDataSetChanged();
////                onLoad();
//            }
//        }, 2500);
    }


    private void onLoad() {
        mListView.stopRefresh();
        mListView.stopLoadMore();
        mListView.setRefreshTime(getTime());
    }

    private String getTime() {
        return new SimpleDateFormat("MM-dd HH:mm", Locale.CHINA).format(new Date());
    }

    /**
     * 加载视频列表
     */
    public void LoadVideoList() {
        toastShow.makeText("加载中.....", Toast.LENGTH_LONG);
        //获取视频列表action
        String url = getResources().getText(R.string.serverURL) + "wVideo_queryPageVideoByObjid";
        // 2 创建StringRequest对象
        Map<String, String> map = new HashMap<>();
        map.put("obj_id",obj_id);
        String params = UrlAppendParameter.appendParameter(url, map);
        MyJsonObjectRequest LoadVideoListRequest = new MyJsonObjectRequest(url,
                params,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        showProgress(false);
                        try {
                            Log.i("response返回：", response.toString());
                            Log.i("返回：", response.getJSONArray("json").toString());
                            jsonArray2List(response.getJSONArray("json"));
                            bindView();
                        } catch (Exception e) {
                            Log.e("json解析异常：", e.getMessage(), e);
                            toastShow.makeText("json解析异常", Toast.LENGTH_LONG);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        showProgress(false);
                        Log.e("请求异常：", error.getMessage(), error);
                        toastShow.makeText("网络或者服务器异常", Toast.LENGTH_LONG);

                    }
                }
        );
        //将StringRequest添加到RequestQueue
        mRequestQueue.add(LoadVideoListRequest);


    }

    /**
     * jsonArray转换为List
     */
    public void jsonArray2List(JSONArray jsonArray) {
        for (int i = 0; i < jsonArray.length(); i++) {
            Person p = new Person();
            try {
                p.setAuthor(((JSONObject) jsonArray.get(i)).getString("author"));
                p.setClicks(Integer.parseInt(((JSONObject) jsonArray.get(i)).getString("clicks")));
                p.setId(Integer.parseInt(((JSONObject) jsonArray.get(i)).getString("id")));
                p.setIntroduce(((JSONObject) jsonArray.get(i)).getString("introduce"));
                p.setObjId(((JSONObject) jsonArray.get(i)).getString("objId"));

                p.setPic(getResources().getText(R.string.serverURL) + ((JSONObject) jsonArray.get(i)).getString("pic"));
                p.setSrcs(getResources().getText(R.string.serverURL) + ((JSONObject) jsonArray.get(i)).getString("srcs"));
                p.setTime(((JSONObject) jsonArray.get(i)).getString("time"));
                p.setTitle(((JSONObject) jsonArray.get(i)).getString("title"));
                p.setTypes(((JSONObject) jsonArray.get(i)).getString("types"));
            } catch (JSONException e) {
                e.printStackTrace();
            }

            items.add(p);

        }


    }

    /**
     * 将内容添加到适配器去展示
     */
    public void bindView() {

        swipeListViewAdapter = new SwipeListViewAdapter(XListViewActivity.this, items);
        mListView.setAdapter(swipeListViewAdapter);
    }

    /**
     * 加载动画现隐藏和显示
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mListView.setVisibility(show ? View.GONE : View.VISIBLE);
            mListView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mListView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mListView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }
}
