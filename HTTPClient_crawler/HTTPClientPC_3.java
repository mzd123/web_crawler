package pachong;


import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;

public class HTTPClientPC_3 {
    @Test
    public void doTest_GET() {
        CloseableHttpClient closeableHttpClient = HttpClients.custom().build();
        HttpGet httpget = new HttpGet("http://echarts.baidu.com/echarts2/doc/example/geoJson/china-main-city/330600.json");
        try {
            CloseableHttpResponse response1 = closeableHttpClient.execute(httpget);
            HttpEntity entity = response1.getEntity();
            InputStreamReader inputStreamReader = new InputStreamReader(entity.getContent(), "utf-8");
            BufferedReader bf = new BufferedReader(inputStreamReader);
            String len = "";
            while ((len = bf.readLine()) != null) {
                System.out.println(len);
            }
            bf.close();
            inputStreamReader.close();
            response1.close();
            closeableHttpClient.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Test
    public void doTest_post() {
        CloseableHttpClient closeableHttpClient = HttpClients.custom().build();
        //抓包获取需要内容的url链接，即网址
        try {
            RequestBuilder requestBuilder = RequestBuilder.post().setUri(new URI("http://httpbin.org/post"));
            HttpUriRequest login = requestBuilder
                    .addParameter("custname", "1")
                    .addParameter("custtel", "1")
                    .addParameter("custemail", "1@qq.com")
                    .addParameter("size", "medium")
                    .addParameter("topping", "bacon")
                    .addParameter("delivery", "14:45")
                    .addParameter("comments", "11111")
                    .build();
            CloseableHttpResponse response2 = closeableHttpClient.execute(login);
            HttpEntity entity = response2.getEntity();
            InputStreamReader inputStreamReader = new InputStreamReader(entity.getContent(), "utf-8");
            BufferedReader bf = new BufferedReader(inputStreamReader);
            String len = "";
            while ((len = bf.readLine()) != null) {
                System.out.println(len);
            }
            bf.close();
            inputStreamReader.close();
            response2.close();
            closeableHttpClient.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Test
    public void doTest_post3() {
        CloseableHttpClient closeableHttpClient = HttpClients.custom().build();
        //抓包获取需要内容的url链接，即网址
        try {
            RequestBuilder requestBuilder = RequestBuilder.post().setUri(new URI("http://xzqh.mca.gov.cn/selectJson"));
            HttpUriRequest login = requestBuilder
                    // .addParameter("diji", "绍兴市")
                    .addParameter("shengji", "浙江省(浙)")
                    .build();
            CloseableHttpResponse response2 = closeableHttpClient.execute(login);
            HttpEntity entity = response2.getEntity();
            InputStreamReader inputStreamReader = new InputStreamReader(entity.getContent(), "utf-8");
            BufferedReader bf = new BufferedReader(inputStreamReader);
            String len = "";
            while ((len = bf.readLine()) != null) {
                System.out.println(len);
            }
            bf.close();
            inputStreamReader.close();
            response2.close();
            closeableHttpClient.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

