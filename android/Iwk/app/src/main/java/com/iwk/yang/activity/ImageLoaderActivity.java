package com.iwk.yang.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.widget.ListView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.iwk.yang.adapter.MyAdapter;
import com.iwk.yang.bean.Person;
import com.iwk.yang.iwk.R;

import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.ArrayList;
import java.util.List;

public class ImageLoaderActivity extends ActionBarActivity {
    private ListView listView;
    private List<Person> list = new ArrayList<>();
    private List<String> listUrl = new ArrayList<>();
    private List<String> listName = new ArrayList<>();
    private MyAdapter adapter;


    private RequestQueue mRequestQueue;
    private StringRequest mStringRequest;
    private Context mContext;
    private JsonObjectRequest mJsonObjectRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_loader);
        listView = (ListView) findViewById(R.id.listView);

//        loadPicture();
        LoadVideoList();
    }

    private void loadPicture() {
        //添加测试头像地址
        listUrl.add("http://files.jb51.net/file_images/article/201512/2015122209220934.jpg");
        listUrl.add("https://ss1.baidu.com/6ONXsjip0QIZ8tyhnq/it/u=2319614035,1459920758&fm=58");
        listUrl.add("http://pic1.nipic.com/2008-08-12/200881211331729_2.jpg");
        listUrl.add("http://pic28.nipic.com/20130402/9252150_190139450381_2.jpg");
        listUrl.add("http://pic9.nipic.com/20100812/3289547_144304019987_2.jpg");
        listUrl.add("https://ss1.baidu.com/6ONXsjip0QIZ8tyhnq/it/u=2319614035,1459920758&fm=58");
        listUrl.add("http://www.th7.cn/Article/UploadFiles/200801/2008012120273536.jpg");
        listUrl.add("http://img.sucai.redocn.com/attachments/images/201012/20101213/20101211_0e830c2124ac3d92718fXrUdsYf49nDl.jpg");
        listUrl.add("http://a2.att.hudong.com/38/59/300001054794129041591416974.jpg");
        listUrl.add("http://pic13.nipic.com/20110415/1347158_132411659346_2.jpg");
        listUrl.add("http://down.tutu001.com/d/file/20101129/2f5ca0f1c9b6d02ea87df74fcc_560.jpg");
        listUrl.add("http://pic24.nipic.com/20121022/9252150_193011306000_2.jpg");
        listUrl.add("http://pic1.nipic.com/2008-08-12/200881211331729_2.jpg");
        listUrl.add("http://pic28.nipic.com/20130402/9252150_190139450381_2.jpg");
        listUrl.add("http://pic9.nipic.com/20100812/3289547_144304019987_2.jpg");
        listUrl.add("http://img.mukewang.com/53e1d0470001ad1e06000338-228-128.jpg");
        listUrl.add("http://www.th7.cn/Article/UploadFiles/200801/2008012120273536.jpg");
        listUrl.add("http://img.sucai.redocn.com/attachments/images/201012/20101213/20101211_0e830c2124ac3d92718fXrUdsYf49nDl.jpg");
        listUrl.add("http://a2.att.hudong.com/38/59/300001054794129041591416974.jpg");
        listUrl.add("http://pic13.nipic.com/20110415/1347158_132411659346_2.jpg");
        //添加测试名字
        listName.add("武松");
        listName.add("吴用");
        listName.add("林冲");
        listName.add("李逵");
        listName.add("华荣");
        listName.add("宋江");
        listName.add("卢俊义");
        listName.add("鲁智深");
        listName.add("杨志");
        listName.add("柴进");
        listName.add("武松");
        listName.add("吴用");
        listName.add("林冲");
        listName.add("李逵");
        listName.add("华荣");
        listName.add("宋江");
        listName.add("卢俊义");
        listName.add("鲁智深");
        listName.add("杨志");
        listName.add("柴进");
        for (int i = 0; i < 19; i++) {
            Person p = new Person();
            p.setImgUrl(listUrl.get(i));
            p.setName(listName.get(i));
            list.add(p);
        }
        adapter = new MyAdapter(this, list);
        listView.setAdapter(adapter);
    }

    /**
     * 加载视频列表
     */
    public void LoadVideoList() {
        String url = "http://picp.zicp.net/iwk/wVideo_queryPopVideo";
        volley_StringRequest_GET(url);
        //添加测试头像地址
//        for (int i = 0; i < 19; i++) {
//            Person p = new Person();
//            p.setImgUrl(listUrl.get(i));
//            p.setName(listName.get(i));
//            list.add(p);
//        }
//        adapter = new MyAdapter(this, list);
//        listView.setAdapter(adapter);
    }

    /**
     * 利用StringRequest实现Get请求
     */
    private void volley_StringRequest_GET(String url) {
        mContext = this;
        // 1 创建RequestQueue对象
        mRequestQueue = Volley.newRequestQueue(mContext);
        // 2 创建StringRequest对象
        mStringRequest = new StringRequest(url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i("Hanjh", "get请求结果:" + response);
                        try {
                            JSONTokener jsonParser = new JSONTokener(response);
                            JSONObject js = (JSONObject) jsonParser.nextValue();
                            js = (JSONObject) js.getJSONArray("json").get(0);
                            Log.i("Gson", "转换结果:" + js.getString("author").toString());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("Hanjh", "get请求错误:" + error.toString());
            }
        }
        );
        // 3 将StringRequest添加到RequestQueue
        mRequestQueue.add(mStringRequest);
    }

}
