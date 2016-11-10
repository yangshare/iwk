package com.iwk.yang.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.widget.ListView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.iwk.yang.adapter.MyAdapter;
import com.iwk.yang.bean.Person;
import com.iwk.yang.iwk.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class VideoListLoaderActivity extends ActionBarActivity {
    private ListView listView;
    private List<Person> list = new ArrayList<>();
    private MyAdapter adapter;

    private RequestQueue mRequestQueue;
    private JsonObjectRequest mJsonObjectRequest;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_loader);
        listView = (ListView) findViewById(R.id.listView);
        // 1 创建RequestQueue对象
        mRequestQueue = Volley.newRequestQueue(this);

        LoadVideoList();
    }


    /**
     * 加载视频列表
     */
    public void LoadVideoList() {
        String url = "http://picp.zicp.net/iwk/wVideo_queryPopVideo";
        // 2 创建StringRequest对象
        mJsonObjectRequest = new JsonObjectRequest(url,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Log.i("返回：", response.getJSONArray("json").toString());
                            jsonArray2List(response.getJSONArray("json"));
                        } catch (Exception e) {
                            Log.e("TAG", e.getMessage(), e);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("TAG", error.getMessage(), error);
                    }
                }
        );
        //将StringRequest添加到RequestQueue
        mRequestQueue.add(mJsonObjectRequest);
    }

    /**
     * jsonArray转换为List
     */
    public void jsonArray2List(JSONArray jsonArray) {
        for (int i = 0; i < jsonArray.length(); i++) {
            Person p = new Person();
            try {
                p.setImgUrl(getResources().getText(R.string.serverURL) + ((JSONObject) jsonArray.get(i)).getString("pic"));
                p.setName(((JSONObject) jsonArray.get(i)).getString("author"));
                p.setClicks(((JSONObject) jsonArray.get(i)).getString("clicks"));
                p.setTimes(((JSONObject) jsonArray.get(i)).getString("time"));
            } catch (JSONException e) {
                e.printStackTrace();
            }

            list.add(p);

        }
        getView(list);

    }

    /**
     * 将内容添加到适配器去展示
     *
     * @param list
     */
    public void getView(List list) {
        adapter = new MyAdapter(this, list);
        listView.setAdapter(adapter);
    }


}
