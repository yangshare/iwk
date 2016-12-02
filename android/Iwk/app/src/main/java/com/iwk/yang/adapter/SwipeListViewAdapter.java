package com.iwk.yang.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.daimajia.swipe.SimpleSwipeListener;
import com.daimajia.swipe.SwipeLayout;
import com.iwk.yang.bean.Person;
import com.iwk.yang.iwk.R;
import com.iwk.yang.tool.ToastShow;
import com.iwk.yang.tool.UrlAppendParameter;
import com.iwk.yang.volley.MyJsonObjectRequest;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by yangshare on 2016/12/1 0001.
 * 邮箱717449318@qq.com
 */

public class SwipeListViewAdapter extends MyBaseSwipeAdapter {

    // 上下文对象
    private Context mContext;
    private ToastShow toastShow;
    private List<Person> list;
    private Person person;
    private RequestQueue mRequestQueue;

    // 构造函数
    public SwipeListViewAdapter(Context mContext, List<Person> list) {
        this.mContext = mContext;
        this.list = list;
        toastShow = new ToastShow(mContext);
        // 1 创建RequestQueue对象
        mRequestQueue = Volley.newRequestQueue(mContext);
    }

    // SwipeLayout的布局id
    @Override
    public int getSwipeLayoutResourceId(int position) {
        return R.id.swipe;
    }

    @Override
    public View generateView(final int position, ViewGroup parent) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.swipelistview_item,
                parent, false);
        final SwipeLayout swipeLayout = (SwipeLayout) v
                .findViewById(getSwipeLayoutResourceId(position));
        // 当隐藏的删除menu被打开的时候的回调函数
        swipeLayout.addSwipeListener(new SimpleSwipeListener() {
            @Override
            public void onOpen(SwipeLayout layout) {
                toastShow.makeText("Open的position="+position, Toast.LENGTH_SHORT);
            }
        });
        // 双击的回调函数
//        swipeLayout
//                .setOnDoubleClickListener(new SwipeLayout.DoubleClickListener() {
//                    @Override
//                    public void onDoubleClick(SwipeLayout layout,
//                                              boolean surface) {
//                        toastShow.makeText("DoubleClick",
//                                Toast.LENGTH_SHORT);
//                    }
//                });
        // 添加删除布局的点击事件
        v.findViewById(R.id.ll_menu).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                toastShow.makeText("delete", Toast.LENGTH_SHORT);
                deleteVideo(list.get(position).getId().toString());
                list.remove(position);

                //点击完成之后，关闭删除menu
                swipeLayout.close();
                notifyDataSetChanged();
            }
        });
        return v;
    }

    //对控件的填值操作独立出来了，我们可以在这个方法里面进行item的数据赋值
    @Override
    public void fillValues(int position, View convertView) {
        TextView t = (TextView) convertView.findViewById(R.id.item_position);
        t.setText((position + 1) + list.get(position).getTitle());
    }


    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    /**
     * 通过id删除单个视频
     */
    public void deleteVideo(String id) {
        //获取视频列表action
        String url = mContext.getResources().getText(R.string.serverURL)+"wVideo_deleteVideoById";
        System.out.println("deleteVideo的url="+url);
        // 2 创建StringRequest对象
        Map<String,String> map=new HashMap<>();
        map.put("video_Id",id);
        String params = UrlAppendParameter.appendParameter(url,map);
        MyJsonObjectRequest LoadVideoListRequest = new MyJsonObjectRequest(url,
                params,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Log.i("response返回：", response.toString());
                            Log.i("返回：", response.get("jsonStr").toString());
                            if (response.get("jsonStr").toString().equals("true")){
                                toastShow.makeText("刪除成功", Toast.LENGTH_SHORT);
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
        mRequestQueue.add(LoadVideoListRequest);


    }
}
