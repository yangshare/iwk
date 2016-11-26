package com.iwk.yang.tool;

import android.net.Uri;

import java.util.Map;

/**
 * Created by Administrator on 2016/11/18 0018.
 */

public class UrlAppendParameter {
    public static String appendParameter(String url,Map<String,String> params){
        Uri uri = Uri.parse(url);
        Uri.Builder builder = uri.buildUpon();
        for(Map.Entry<String,String> entry:params.entrySet()){
            builder.appendQueryParameter(entry.getKey(),entry.getValue());
        }
        return builder.build().getQuery();
    }
}
