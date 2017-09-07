package com.standards.library.util;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.ObjectOutputStream;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.List;

public class Util {

    /**
     * 安装一个apk的安装包
     */
    public static void installApk(Context context, String filePath) {
        if (TextUtils.isEmpty(filePath)) {
            return;
        }
        File file = new File(filePath);
        if (!file.exists()) {
            return;
        }
        if (file.exists()) {
            Intent intent = new Intent();
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setAction(Intent.ACTION_VIEW);
            Uri uri = Uri.fromFile(file);
            intent.setDataAndType(uri, "application/vnd.android.package-archive");
            context.startActivity(intent);
        }
    }

    /**
     * 字节转换成相应大小的MB,KB
     *
     * @param bytes
     * @return
     */
    public static String bytes2Convert(long bytes) {
        BigDecimal filesize = new BigDecimal(bytes);
        BigDecimal gbyte = new BigDecimal(1024 * 1024 * 1024);
        float returnValue = filesize.divide(gbyte, 2, BigDecimal.ROUND_UP).floatValue();
        if (returnValue >= 1) {
            return (returnValue + "GB");
        }
        BigDecimal megabyte = new BigDecimal(1024 * 1024);
        returnValue = filesize.divide(megabyte, 2, BigDecimal.ROUND_UP).floatValue();
        if (returnValue >= 1) {
            return (returnValue + "MB");
        }
        BigDecimal kilobyte = new BigDecimal(1024);
        returnValue = filesize.divide(kilobyte).intValue();
        return (returnValue + "KB");
    }

    /**生日转换年龄
     *
     * @param s 生日，仅支持四位数字年份开头的生日，如“1980”
     * @return
     */
    public static int birth2Age(String s) {
        if(!TextUtils.isEmpty(s)){
            return 0;
        }
        Calendar mycalendar = Calendar.getInstance();// 获取现在时间
        String year = String.valueOf(mycalendar.get(Calendar.YEAR));
        int now = Integer.parseInt(year);
        int birthYear = Integer.parseInt(s.substring(0, 4));
        if (now < birthYear) {
            return 0;
        }
        return now - birthYear;

    }

    /**
     * 对象转化成字节数组
     * @param obj
     * @return
     */
    public static byte[] ObjectToByte(Object obj){
        byte[] bytes;
        try {
            //object to bytearray
            ByteArrayOutputStream bo = new ByteArrayOutputStream();
            ObjectOutputStream oo = new ObjectOutputStream(bo);
            oo.writeObject(obj);

            bytes = bo.toByteArray();

            bo.close();
            oo.close();
            return(bytes);
        }catch(Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    /**
	 * 判断后台服务是否在运行
	 *
	 * @param className
	 *
	 * @return
	 */
    public static boolean isServiceRunning(Context context, String className) {

        ActivityManager activityManager = (ActivityManager) context
                .getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningServiceInfo> serviceList = activityManager
                .getRunningServices(Integer.MAX_VALUE);

        if (!(serviceList.size() > 0)) {
            return false;
        }

        for (int i = 0; i < serviceList.size(); i++) {
            ActivityManager.RunningServiceInfo serviceInfo = serviceList.get(i);
            ComponentName serviceName = serviceInfo.service;

            if (serviceName.getClassName().equals(className)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 返回格式化字符串
     * @param format
     * @param arg
     * @return
     */
    public static String getFormatStr(String format,String arg){
        String result = "";
        result = String.format(format,arg);
        return result;
    }
}
