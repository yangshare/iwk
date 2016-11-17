package com.iwk.yang.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.iwk.yang.adapter.MyAdapter;
import com.iwk.yang.bean.Person;
import com.iwk.yang.iwk.R;
import com.iwk.yang.tool.ToastShow;
import com.iwk.yang.widget.XListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;


/**
 * Created by Administrator on 2016/11/15 0015.
 * 本activity用于视频列表的获取和显示
 */
public class VideoListLoaderActivity extends ActionBarActivity {
    private XListView listView;
    private List<Person> list;
    private MyAdapter adapter;
    private ToastShow toastShow;

    private Handler mHandler;

    private RequestQueue mRequestQueue;


    private int mIndex = 0;
    private int mRefreshIndex = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_loader);
        listView = (XListView) findViewById(R.id.listView);
        listView.setPullRefreshEnable(true);
        listView.setPullLoadEnable(true);
        listView.setAutoLoadEnable(true);
        listView.setXListViewListener(xListViewListener);

        list = new ArrayList<>();
        // 1 创建RequestQueue对象
        mRequestQueue = Volley.newRequestQueue(this);
        toastShow = new ToastShow(VideoListLoaderActivity.this);

        mHandler = new Handler();
        //获取视频页数
        queryPageNum();
        //加載視頻列表
        LoadVideoList();

        //事件监听
        listView.setOnItemClickListener(lvClickListener);
    }


    @Override
    protected void onStop() {
        super.onStop();
        mRequestQueue.cancelAll(null);
    }

    /**
     * 获取视频页数
     */
    public void queryPageNum() {
        Toast.makeText(VideoListLoaderActivity.this, "加载中.....", Toast.LENGTH_LONG).show();
        //获取视频列表action
        String url = getResources().getText(R.string.serverURL)+"wVideo_queryPageNum?types=a";
        // 2 创建StringRequest对象
        JsonObjectRequest queryPageNumRequest = new JsonObjectRequest(url,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Log.i("queryPageNum的response：", response.get("jsonStr").toString());
                            mIndex = Integer.parseInt(response.get("jsonStr").toString());
                        } catch (Exception e) {
                            Log.e("queryPageNumjson解析异常：", e.getMessage(), e);
                            Toast.makeText(VideoListLoaderActivity.this, "json解析异常", Toast.LENGTH_LONG).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
//                        byte[] htmlBodyBytes = error.networkResponse.data;
                        Log.e("请求异常：", error.getMessage(), error);
                        Toast.makeText(VideoListLoaderActivity.this, "网络或者服务器异常", Toast.LENGTH_LONG).show();

                    }
                }
        );
        //将StringRequest添加到RequestQueue
        mRequestQueue.add(queryPageNumRequest);


    }

    /**
     * 加载视频列表
     */
    public void LoadVideoList() {
        toastShow.makeText("加载中.....", Toast.LENGTH_LONG);
        //获取视频列表action
        String url = getResources().getText(R.string.serverURL)+"wVideo_queryPageVideo?curPage=" + mRefreshIndex + "&types=a";
        // 2 创建StringRequest对象
        JsonObjectRequest LoadVideoListRequest = new JsonObjectRequest(url,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Log.i("response返回：", response.toString());
                            Log.i("返回：", response.getJSONArray("json").toString());
                            if (mRefreshIndex <= 0) {
                                Log.e("监听","mRefreshIndex小于=0");
                                jsonArray2List(response.getJSONArray("json"));
                                bindView();
                            } else {
                                Log.e("监听","mRefreshIndex>0");
                                jsonArray2List(response.getJSONArray("json"));
                                adapter.notifyDataSetChanged();
                            }
                            //页数加一
                            mRefreshIndex++;
                        } catch (Exception e) {
                            Log.e("json解析异常：", e.getMessage(), e);
                            Toast.makeText(VideoListLoaderActivity.this, "json解析异常", Toast.LENGTH_LONG).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
//                        byte[] htmlBodyBytes = error.networkResponse.data;
                        Log.e("请求异常：", error.getMessage(), error);
                        Toast.makeText(VideoListLoaderActivity.this, "网络或者服务器异常", Toast.LENGTH_LONG).show();

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

            list.add(p);

        }


    }

    /**
     * 将内容添加到适配器去展示
     */
    public void bindView() {

        adapter = new MyAdapter(this, list, 300);
        listView.setAdapter(adapter);
    }

    //点击单个视频
    public AdapterView.OnItemClickListener lvClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Person person=new Person();
            person=list.get(position-1);

            Intent intent = new Intent(VideoListLoaderActivity.this,VideoPlayerActivity.class);
// 通过Bundle
            Bundle bundle = new Bundle();
            bundle.putSerializable("person",person);
            intent.putExtras(bundle);
            startActivity(intent);

            toastShow.makeText("id:" + person.getId() + ",名字:" + person.getTitle() + ",time:" + person.getTime(), Toast.LENGTH_LONG);
        }
    };

    public XListView.IXListViewListener xListViewListener = new XListView.IXListViewListener() {
        @Override
        public void onRefresh() {
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    mRefreshIndex = 0;
                    list.clear();
                    LoadVideoList();
                    onLoad();
                }
            }, 2500);
        }

        @Override
        public void onLoadMore() {
            Log.e("查看","mRefreshIndex="+mRefreshIndex+",mIndex="+mIndex);
            if (mRefreshIndex < mIndex) {
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        LoadVideoList();
                        onLoad();
                    }
                }, 2500);
            } else {
                listView.stopLoadMore();
                toastShow.makeText("没有更多", Toast.LENGTH_SHORT);

            }
        }
    };


    private void onLoad() {
        listView.stopRefresh();
        listView.stopLoadMore();
        listView.setRefreshTime(getTime());
    }

    private String getTime() {
        return new SimpleDateFormat("MM-dd HH:mm:ss", Locale.CHINA).format(new Date());
    }


}
