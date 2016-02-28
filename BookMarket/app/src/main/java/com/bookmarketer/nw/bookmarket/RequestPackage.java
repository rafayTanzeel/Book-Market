package com.bookmarketer.nw.bookmarket;

import android.util.Log;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Rafay on 27/02/2016.
 */
public class RequestPackage {
    private String uri;
    private Map<String, String> params = new HashMap<>();

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    private String method = "GET";

    public String getUri() {
        return uri;
    }

    public Map<String, String> getParams() {
        return params;
    }

    public void setParams(Map<String, String> params) {
        this.params = params;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }




    public void setParam(String key, String value){
        params.put(key, value);
    }


    public  String getEncodedParams(){

        StringBuilder sb=new StringBuilder();
        for(String key : params.keySet()){
            String value = null;
            try {
                Log.d("key", key);

                value = URLEncoder.encode(params.get(key), "UTF-8");
                Log.d("value",value);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

            if (sb.length()>0){
                sb.append('&');
            }
            Log.d("ssss","dddd");
            sb.append(key + "=" + value);
        }

        return sb.toString();

    }





}
