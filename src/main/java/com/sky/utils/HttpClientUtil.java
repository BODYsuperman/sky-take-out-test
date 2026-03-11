package com.sky.utils;

import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class HttpClientUtil {

    static final int TIMEOUT_MSEC = 5 * 1000;

    public static String doGet(String url, Map<String, String> paramMap) {
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            URIBuilder uriBuilder = new URIBuilder(url);
            if (paramMap != null) {
                for (String key : paramMap.keySet()) {
                    uriBuilder.addParameter(key, paramMap.get(key));
                }
            }
            RequestConfig requestConfig = RequestConfig.custom()
                    .setConnectTimeout(TIMEOUT_MSEC)
                    .setConnectionRequestTimeout(TIMEOUT_MSEC)
                    .setSocketTimeout(TIMEOUT_MSEC)
                    .build();
            HttpGet httpGet = new HttpGet(uriBuilder.build());
            httpGet.setConfig(requestConfig);
            
            try (CloseableHttpResponse response = httpClient.execute(httpGet)) {
                int status = response.getStatusLine().getStatusCode();
                if (status == 200) {
                    return EntityUtils.toString(response.getEntity());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String doPost(String url, Map<String, String> paramMap) throws IOException {
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpPost httpPost = new HttpPost(url);
            List<NameValuePair> params = new ArrayList<>();
            if (paramMap != null) {
                for (String key : paramMap.keySet()) {
                    params.add(new BasicNameValuePair(key, paramMap.get(key)));
                }
            }
            httpPost.setEntity(new UrlEncodedFormEntity(params));
            
            RequestConfig requestConfig = RequestConfig.custom()
                    .setConnectTimeout(TIMEOUT_MSEC)
                    .setConnectionRequestTimeout(TIMEOUT_MSEC)
                    .setSocketTimeout(TIMEOUT_MSEC)
                    .build();
            httpPost.setConfig(requestConfig);
            
            try (CloseableHttpResponse response = httpClient.execute(httpPost)) {
                int status = response.getStatusLine().getStatusCode();
                if (status == 200) {
                    return EntityUtils.toString(response.getEntity());
                }
            }
        }
        return null;
    }

    public static String doPostJson(String url, String json) throws IOException {
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpPost httpPost = new HttpPost(url);
            RequestConfig requestConfig = RequestConfig.custom()
                    .setConnectTimeout(TIMEOUT_MSEC)
                    .setConnectionRequestTimeout(TIMEOUT_MSEC)
                    .setSocketTimeout(TIMEOUT_MSEC)
                    .build();
            httpPost.setConfig(requestConfig);
            httpPost.setEntity(new StringEntity(json, "UTF-8"));
            
            try (CloseableHttpResponse response = httpClient.execute(httpPost)) {
                int status = response.getStatusLine().getStatusCode();
                if (status == 200) {
                    return EntityUtils.toString(response.getEntity());
                }
            }
        }
        return null;
    }
}
