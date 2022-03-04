package com.example.dtt.utils.http;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

public class HttpReqUtils {

    public static JSONObject getPostToken(String url,Map<String, Object> map) throws Exception{
        HttpClient httpClient = HttpClientBuilder.create().build();
        HttpPost httpPost = new HttpPost(url);
        httpPost.addHeader(HTTP.CONTENT_TYPE, "application/json;charset=utf-8");
        httpPost.addHeader("Content-type", "application/json; charset=utf-8");
        httpPost.setHeader("Accept", "application/json");
        httpPost.setEntity(new StringEntity(JSON.toJSONString(map), Charset.forName("UTF-8")));
        HttpResponse response = httpClient.execute(httpPost);
        if (null == response || response.getStatusLine() == null) {
            throw new Exception("Post Request For Url[{}] is not ok. Response is null");
        } else if (response.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
            throw new Exception("Post Request For Url[{}] is not ok. Response Status Code is {}");
        }
        String resultString = EntityUtils.toString(response.getEntity());
        JSONObject jsonObj = JSONObject.parseObject(resultString);
        return jsonObj;
    }


    public static JSONObject getPostUser(String url,Map<String, Object> map,String token) throws Exception{
        HttpClient httpClient = HttpClientBuilder.create().build();
        HttpPost httpPost = new HttpPost(url);
        httpPost.addHeader(HTTP.CONTENT_TYPE, "application/json;charset=utf-8");
        httpPost.addHeader("Content-type", "application/json; charset=utf-8");
        httpPost.setHeader("Accept", "application/json");
        httpPost.setHeader("token",token);
        httpPost.setEntity(new StringEntity(JSON.toJSONString(map), Charset.forName("UTF-8")));
        HttpResponse response = httpClient.execute(httpPost);
        if (null == response || response.getStatusLine() == null) {
            throw new Exception("Post Request For Url[{}] is not ok. Response is null");
        } else if (response.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
            throw new Exception("Post Request For Url[{}] is not ok. Response Status Code is {}");
        }
        String resultString = EntityUtils.toString(response.getEntity());
        JSONObject jsonObj = JSONObject.parseObject(resultString);
        return jsonObj;
    }

/*
    public static void main(String[] args) {
        String url = "http://10.42.1.51:9094/cango-mid-account/accountApp/token";
        Map<String,Object> params = new HashMap<>();
        params.put("appKey","appManager");
        params.put("appSecret","appManager");

        try {
            JSONObject  tokenResult = getPostToken(url,params);
            String token  = JSONObject.parseObject(tokenResult.getString("obj")).getString("token");
            System.out.println(token);
        }catch (Exception e){
            e.printStackTrace();
        }
    }*/
}
