package com.iwk.yang.volley;

import com.android.volley.NetworkResponse;
import com.android.volley.Response;

import org.json.JSONObject;

/**
 * Created by Administrator on 2016/11/16 0016.
 */

public class JsonObjectRequest extends com.android.volley.toolbox.JsonObjectRequest {
    public JsonObjectRequest(int method, String url, JSONObject jsonRequest, Response.Listener<JSONObject> listener, Response.ErrorListener errorListener) {
        super(method, url, jsonRequest, listener, errorListener);
    }

    public JsonObjectRequest(String url, JSONObject jsonRequest, Response.Listener<JSONObject> listener, Response.ErrorListener errorListener) {
        super(url, jsonRequest, listener, errorListener);
    }

    @Override
    protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
        return super.parseNetworkResponse(response);
    }
}
