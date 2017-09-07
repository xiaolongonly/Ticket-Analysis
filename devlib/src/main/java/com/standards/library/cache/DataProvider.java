package com.standards.library.cache;

import android.content.Context;
import android.text.TextUtils;


import com.standards.library.cache.DirContext;
import com.standards.library.cache.Disker;
import com.standards.library.util.LogUtil;
import com.standards.library.util.Md5;
import com.standards.library.util.Util;

import java.io.IOException;

public class DataProvider {

    private static final String FILTER_FIELD = "srv";

    private Disker disker;

    private static DataProvider provider;
    private static Context context;

    public static void init(Context context) {
        DataProvider.context = context;
    }

    public synchronized static DataProvider getInstance() {
        if (provider == null) {
            provider = new DataProvider();
        }
        return provider;
    }

    private DataProvider() {
        disker = new Disker(context, DirContext.getInstance().getDir(DirContext.DirEnum.ROOT_dir).getName());
    }

    public void putStringToDisker(String content, String url) {
        if (TextUtils.isEmpty(content) || TextUtils.isEmpty(url)) {
            return;
        }
        int index = url.indexOf(FILTER_FIELD);
        int startIndex = index == -1 ? 0 : index;
        String key = Md5.digest32(url.substring(startIndex));
        LogUtil.d("Data", "put key : " + key + "startIndex : " + startIndex);

        disker.putStringToDisker(content, key);
    }

    public String getCacheFromDisker(String url) {
        try {
            int index = url.indexOf(FILTER_FIELD);
            int startIndex = index == -1 ? 0 : index;
            String key = Md5.digest32(url.substring(startIndex));
            return disker.getStringFromDisk(key);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void clearAllCache() {
        disker.deleteCache();
    }

    public String getDiskSize() {
        return Util.bytes2Convert(disker.getSize());
    }
}
