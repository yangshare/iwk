package com.iwk.yang.adapter;


import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.iwk.yang.bean.Person;
import com.iwk.yang.iwk.R;
import com.iwk.yang.volley.MyVolley;

import java.util.List;

public class MyAdapter extends BaseAdapter {
    private Context context;
    private List<Person> list;
    private LayoutInflater mInflater;
    public ViewHolder holder;

    public MyAdapter(Context context, List<Person> list) {
        this.context = context;
        this.list = list;
        this.mInflater = LayoutInflater.from(context);
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

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        holder = null;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.itemone, null);
            holder = new ViewHolder();
            holder.iv_image = (ImageView) convertView.findViewById(R.id.iv_image);
            holder.tv_name = (TextView) convertView.findViewById(R.id.tv_name);
            holder.tv_clicks = (TextView) convertView.findViewById(R.id.tv_clicks);
            holder.tv_times = (TextView) convertView.findViewById(R.id.tv_times);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        Person bean = list.get(position);
        holder.tv_name.setText(bean.getName());
        holder.tv_clicks.setText(bean.getClicks());
        holder.tv_times.setText(bean.getTimes());

        MyVolley.getImage(bean.getImgUrl(), holder.iv_image, R.mipmap.ic_launcher, R.mipmap.ic_launcher,0,0);
        return convertView;
    }

    class ViewHolder {
        private TextView tv_name;
        private TextView tv_clicks;
        private TextView tv_times;
        private ImageView iv_image;
    }
}
