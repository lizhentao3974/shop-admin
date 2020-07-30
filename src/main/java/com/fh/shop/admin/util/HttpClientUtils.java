package com.fh.shop.admin.util;

import com.alibaba.fastjson.JSONObject;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.*;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class HttpClientUtils {

    public static final String JSON = "JSON";

    public static final String FORM = "FORM";

    public static String sendDelete(String url, Map<String, String> params) {

        CloseableHttpClient client = null;

        HttpDelete httpDelete = null;

        String result = null;

        CloseableHttpResponse response = null;

        try {
            client = HttpClientBuilder.create().build();

            httpDelete = new HttpDelete(url);

            if (null != params && params.size() > 0) {
                List<BasicNameValuePair> pairList = new ArrayList<>();
                params.forEach((x, y) -> pairList.add(new BasicNameValuePair(x, y)));
                UrlEncodedFormEntity urlEncodedFormEntity = new UrlEncodedFormEntity(pairList, "utf-8");
                String paramStr = EntityUtils.toString(urlEncodedFormEntity, "utf-8");
                url += "?" + paramStr;
            }

            response = client.execute(httpDelete);

            result = EntityUtils.toString(response.getEntity(), "utf-8");
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            close(response, httpDelete, client);
        }
        return result;
    }

    /*public static String sendPostJson(String url, Map<String,String> params){

        CloseableHttpClient client = null;
        HttpPost httpPost = null;
        CloseableHttpResponse response = null;
        String result = null;

        try {
            client = HttpClientBuilder.create().build();

            httpPost = new HttpPost(url);

            if(null != params && params.size() > 0) {

                String jsonString = JSONObject.toJSONString(params);

                StringEntity stringEntity = new StringEntity(jsonString, "utf-8");

                stringEntity.setContentType("application/json;charset=utf-8");

                httpPost.setEntity(stringEntity);
            }

            response = client.execute(httpPost);

            result = EntityUtils.toString(response.getEntity(), "utf-8");
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            close(response,httpPost,client);
        }
        return result;
    }*/

    public static String sendPut(String url, Map<String, String> params) {

        CloseableHttpClient client = null;
        HttpPut httpPut = null;
        CloseableHttpResponse response = null;
        String result = null;

        try {
            client = HttpClientBuilder.create().build();

            httpPut = new HttpPut(url);

            if (null != params && params.size() > 0) {

                String jsonString = JSONObject.toJSONString(params);

                StringEntity stringEntity = new StringEntity(jsonString, "utf-8");

                stringEntity.setContentType("application/json;charset=utf-8");

                httpPut.setEntity(stringEntity);
            }

            response = client.execute(httpPut);

            result = EntityUtils.toString(response.getEntity(), "utf-8");
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            close(response, httpPut, client);
        }
        return result;
    }

    private static void close(CloseableHttpResponse response, HttpRequestBase requestBase, CloseableHttpClient client) {
        if (null != response) {
            try {
                response.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (null != requestBase) {
            requestBase.releaseConnection();
        }
        if (null != client) {
            try {
                client.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static String sendPost(String url, Map<String, String> params, String type) {

        CloseableHttpClient client = null;
        HttpPost httpPost = null;
        CloseableHttpResponse response = null;
        String result = null;

        try {
            client = HttpClientBuilder.create().build();

            httpPost = new HttpPost(url);

            if (null != params && params.size() > 0) {
                if (type.equals(FORM)) {
                    List<BasicNameValuePair> pairList = new ArrayList<>();

                    params.forEach((x, y) -> pairList.add(new BasicNameValuePair(x, y)));

                    UrlEncodedFormEntity urlEncodedFormEntity = new UrlEncodedFormEntity(pairList, "utf-8");

                    httpPost.setEntity(urlEncodedFormEntity);
                } else if (type.equals(JSON)) {
                    String jsonString = JSONObject.toJSONString(params);

                    StringEntity stringEntity = new StringEntity(jsonString, "utf-8");

                    stringEntity.setContentType("application/json;charset=utf-8");

                    httpPost.setEntity(stringEntity);
                }
            }

            response = client.execute(httpPost);
            result = EntityUtils.toString(response.getEntity(), "utf-8");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(response, httpPost, client);
        }
        return result;
    }

    public static String sendGet(String url, Map<String, String> params) {
        CloseableHttpClient client = null;
        HttpGet httpGet = null;
        CloseableHttpResponse response = null;
        String result = null;

        try {

            client = HttpClientBuilder.create().build();

            List<BasicNameValuePair> pairList = new ArrayList<>();

            if (null != params && params.size() > 0) {
                params.forEach((x, y) -> pairList.add(new BasicNameValuePair(x, y)));

                UrlEncodedFormEntity urlEncodedFormEntity = new UrlEncodedFormEntity(pairList, "utf-8");

                String paramStr = EntityUtils.toString(urlEncodedFormEntity, "utf-8");

                url += "?" + paramStr;
            }

            httpGet = new HttpGet(url);
            response = client.execute(httpGet);
            result = EntityUtils.toString(response.getEntity(), "utf-8");

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            close(response, httpGet, client);
        }
        return result;
    }
}
