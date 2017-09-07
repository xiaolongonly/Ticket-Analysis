package com.standards.library.network;

public class NetworkConfig {
    private static String BASE_URL;

    public static void setBaseUrl(String url) {
        NetworkConfig.BASE_URL = url;
    }


    public static String getBaseUrl() {
        return BASE_URL;
    }

}
