package com.fh.shop.admin;

import com.fh.shop.admin.util.HttpClientUtils;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HttpclientTest {

    @Test
    public void test1() {
        //打开浏览器
        CloseableHttpClient client = HttpClientBuilder.create().build();

        //输入URL地址
        HttpGet httpGet = new HttpGet("http://localhost:8082/cate/list");

        CloseableHttpResponse response = null;
        try {
            //发送请求，获取响应
            response = client.execute(httpGet);
            String result = EntityUtils.toString(response.getEntity(), "utf-8");
            System.out.println(result);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            if (null != response) {
                try {
                    response.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (null != httpGet) {
                httpGet.releaseConnection();
            }
            if (null != client) {
                try {
                    client.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Test
    public void test2() {

        Map<String, String> map = new HashMap<>();
        map.put("minPrice", "20");
        map.put("maxPrice", "25");
        String s = HttpClientUtils.sendGet("http://localhost:8082/cars", map);
        System.out.println(s);

    }

    @Test
    public void test4() {

        Map<String, String> map = new HashMap<>();
        map.put("carName", "丰田凯美瑞77");
        map.put("price", "27");
        map.put("stock", "67");
        String s = HttpClientUtils.sendPost("http://localhost:8082/cars/info", map, HttpClientUtils.JSON);
        System.out.println(s);

    }

   /* @Test
    public void test5(){

        Map<String,String> map = new HashMap<>();
        map.put("carName","奇瑞QQ");
        map.put("price","3");
        map.put("stock","77");
        String s = HttpClientUtils.sendPostJson("http://localhost:8082/cars/info", map);
        System.out.println(s);

    }*/

    @Test
    public void test6() {

        String s = HttpClientUtils.sendDelete("http://localhost:8082/cars/8", null);
        System.out.println(s);

    }

    @Test
    public void test3() {

        CloseableHttpClient client = null;
        HttpPost httpPost = null;
        CloseableHttpResponse response = null;

        try {
            client = HttpClientBuilder.create().build();
            httpPost = new HttpPost("http://localhost:8082/cars");

            List<BasicNameValuePair> params = new ArrayList<>();
            params.add(new BasicNameValuePair("carName", "大众途观"));
            params.add(new BasicNameValuePair("price", "22"));
            params.add(new BasicNameValuePair("stock", "55"));

            UrlEncodedFormEntity urlEncodedFormEntity = new UrlEncodedFormEntity(params, "utf-8");
            httpPost.setEntity(urlEncodedFormEntity);
            response = client.execute(httpPost);
            EntityUtils.toString(response.getEntity());

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            if (null != response) {
                try {
                    response.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (null != httpPost) {
                httpPost.releaseConnection();
            }
            if (null != client) {
                try {
                    client.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }


    }

    @Test
    public void test7() {
        Map<String, String> map = new HashMap<>();
        map.put("id", "5");
        map.put("carName", "雪佛兰悍界者");
        map.put("price", "33");
        map.put("stock", "100");
        String s = HttpClientUtils.sendPut("http://localhost:8082/cars", map);
        System.out.println(s);

    }
}
