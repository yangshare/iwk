package com.iwk.yang.iwk;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


/**
 * Created by Administrator on 2016/9/28.
 */

public class HttpThread extends Thread {//线程类

    private String url;
    private Handler handler;


    public HttpThread(String url, Handler handler) {//带参构造
        this.url = url;
        this.handler = handler;
    }

    @Override
    public void run() {
        String result = null;
        URL url = null;
        HttpURLConnection connection = null;
        InputStreamReader in = null;
        try {
            url = new URL("http://www.baidu.com");
            connection = (HttpURLConnection) url.openConnection();
            in = new InputStreamReader(connection.getInputStream());
            BufferedReader bufferedReader = new BufferedReader(in);
            StringBuffer strBuffer = new StringBuffer();
            String line = null;
            while ((line = bufferedReader.readLine()) != null) {
                strBuffer.append(line);
            }
            result = strBuffer.toString();
            Log.e("网页", "" + result);
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("异常", "" + e.getMessage());
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
        Message message = new Message();
        Bundle bundle = new Bundle();
        Log.e("executeHttpGet==", "executeHttpGet为空" + result);
        bundle.putString("message", result);
        message.setData(bundle);
        handler.sendMessage(message);
    }


}
