package com.yixin.utils;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

public class HCUtils {

    public static CloseableHttpClient getClient(){
        return HttpClients.createDefault();
    }

    public static HttpGet getHttpGet(String url){
        HttpGet get = new HttpGet(url);
        get.setHeader("User-Agent","Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/62.0.3202.75 Safari/537.36");
        return get;
    }

}
