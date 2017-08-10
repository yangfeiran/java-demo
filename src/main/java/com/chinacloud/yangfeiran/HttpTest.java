package com.chinacloud.yangfeiran;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.IOException;
import java.net.*;

/**
 * Created by Administrator on 2017/7/19.
 */
public class HttpTest {
     public static void main(String[] args){
         String s = "http://tp-restapi.amap.com/gate?sid=30002&serviceKey=5463C4C83002F66F1859812811CF5F4F&resType=json&encode=utf-8&reqData={\"city\":\"510100\",\"dateType\":2}";
//         HttpClient httpClient = new HttpClient();
//         GetMethod getMethod = new GetMethod("http://tp-restapi.amap.com/gate?sid=30002&serviceKey=5463C4C83002F66F1859812811CF5F4F&resType=json&encode=utf-8&reqData={\"city\":\"510100\",\"dateType\":2}");
//         try {
//             int i = httpClient.executeMethod(getMethod);
//             String responseBodyAsString = getMethod.getResponseBodyAsString();
//             System.out.println(responseBodyAsString);
//         } catch (IOException e) {
//             e.printStackTrace();
//         }
//         HttpGet httpGet = new HttpGet("http://tp-restapi.amap.com/gate?sid=30002&serviceKey=5463C4C83002F66F1859812811CF5F4F&resType=json&encode=utf-8&reqData={\\\"city\\\":\\\"510100\\\",\\\"dateType\\\":2}".replaceAll("&","%26").replaceAll("=","%3D"));
         System.out.println(URLEncoder.encode("http://tp-restapi.amap.com/gate?sid=30002&serviceKey=5463C4C83002F66F1859812811CF5F4F&resType=json&encode=utf-8&reqData={\"city\":\"510100\",\"dateType\":2}"));
         URL url1;
         URI uri = null;
         try {
             url1 = new URL(s);
             uri = new URI(url1.getProtocol(), url1.getHost(), url1.getPath(), url1.getQuery(), null);
         } catch (URISyntaxException e) {
             e.printStackTrace();
         } catch (MalformedURLException e) {
             e.printStackTrace();
         }
         HttpGet httpGet = new HttpGet(s);
         HttpClientBuilder clientBuilder = HttpClientBuilder.create();
         CloseableHttpClient client = clientBuilder.build();
         try {
             HttpResponse execute = client.execute(httpGet);
             byte[] b = new byte[4096];
             StringBuffer out = new StringBuffer();
             execute.getEntity().getContent().read(b);
             out.append(new String(b, 0, 4096));
             System.out.println(out.toString());
         } catch (IOException e) {
             e.printStackTrace();
         }
     }
}
