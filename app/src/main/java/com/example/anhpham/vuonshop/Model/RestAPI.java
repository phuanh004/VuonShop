package com.example.anhpham.vuonshop.Model;

/**
 * Created by Anh Pham on 19/06/2016.
 */

public class RestAPI {
    private String host = "https://shop-vuon.c9users.io";
    private String path = "/wp-json/wc/v1/";
    private String key = "consumer_key=ck_78a0320fcc201ca37cef2632def0f781e71c33b9&consumer_secret=cs_aa6eb5eb87851d87fb3eabf6e8c67d945fd789e4";

    public RestAPI() {
    }

    public String getHost() {
        return host;
    }

    public String getPath() {
        return path;
    }

    public String getKey() {
        return key;
    }
}
