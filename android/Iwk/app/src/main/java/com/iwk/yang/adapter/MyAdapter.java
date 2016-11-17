package com.iwk.yang.adapter;


import android.content.Context;
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
    private int screenWidth;
    public ViewHolder holder;


    public MyAdapter(Context context, List<Person> list, int screenWidth) {
        this.context = context;
        this.list = list;
        this.screenWidth = screenWidth;
        this.mInflater = LayoutInflater.from(context);
    }

    public MyAdapter(Context context, List<Person> list) {
        this.context = context;
        this.list = list;
        this.screenWidth = 0;
        this.mInflater = LayoutInflater.from(context);
    }


    class ViewHolder {
        private TextView tv_title;
        private TextView tv_author;
        private TextView tv_clicks;
        private TextView tv_times;
        private ImageView iv_pic;
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
            holder.tv_title=(TextView) convertView.findViewById(R.id.tv_title);
            holder.iv_pic = (ImageView) convertView.findViewById(R.id.iv_pic);
            holder.tv_author = (TextView) convertView.findViewById(R.id.tv_author);
            holder.tv_clicks = (TextView) convertView.findViewById(R.id.tv_clicks);
            holder.tv_times = (TextView) convertView.findViewById(R.id.tv_times);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        Person bean = list.get(position);
        holder.tv_title.setText(bean.getTitle());
        holder.tv_author.setText(bean.getAuthor());
        holder.tv_clicks.setText(bean.getClicks().toString());
        holder.tv_times.setText(bean.getTime());

        MyVolley.getImage(bean.getPic(), holder.iv_pic, R.mipmap.ic_launcher, R.mipmap.ic_launcher, screenWidth, screenWidth);
        return convertView;
    }


}
