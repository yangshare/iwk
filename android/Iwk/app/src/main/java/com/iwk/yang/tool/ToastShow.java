package com.iwk.yang.tool;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by Administrator on 2016/11/11 0011.
 */

public class ToastShow {
    private Context context;
    private Toast toast = null;

    public ToastShow(Context context) {
        this.context = context;
    }

    public void makeText(String text,int time) {
        if (toast == null) {
            toast = Toast.makeText(context, text, time);
        } else {
            toast.setText(text);
        }
        toast.show();
    }
}
