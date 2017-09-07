package com.standards.library.util;

import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.provider.Settings.Secure;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import com.standards.library.BuildConfig;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.UUID;

public class DeviceUidGenerator {

    private static class Monitor {
        private static StringBuilder log;

        private static void start() {
            log = new StringBuilder("start:\n");
        }

        private static void log(String how) {
            log.append(how).append("\n");
        }

        private static void finish(String uid) {
            log.append("end. device uid is:" + uid);
        }

        private static String getLog() {
            return log.toString();
        }
    }

    public static String trace() {
        return Monitor.getLog();
    }

    public static String generate(Context context) throws Exception {
        Monitor.start();
        String uid;
        while (true) {
            TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            uid = tm.getDeviceId();
            if (!TextUtils.isEmpty(uid)) {
                Monitor.log("from imei");
                break;
            }

            WifiManager wifi = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
            WifiInfo info = wifi.getConnectionInfo();
            uid = info.getMacAddress();
            if (!TextUtils.isEmpty(uid)) {
                Monitor.log("from mac");
                break;
            }

            if (Build.VERSION.SDK_INT >= 9 && !TextUtils.isEmpty(Build.SERIAL)
                    && !"unknown".equalsIgnoreCase(Build.SERIAL)) {
                uid = Build.SERIAL;
                Monitor.log("from serial id");
                break;
            }

            String androidId = Secure.getString(context.getContentResolver(), Secure.ANDROID_ID);
            if (!TextUtils.isEmpty(androidId) && !BuildConfig.DEVICE_ID.equals(androidId)) {
                uid = androidId;
                Monitor.log("from android id");
                break;
            }

            try {
                uid = fromUuid(context);
                Monitor.log("from uuid");
                break;
            } catch (Exception e) {
                Monitor.finish("Could not generate uid from device!!!");
                throw new RuntimeException("Could not generate uid from device!!!");
            }
        }

		/*//uid 服务器长度
		if (uid.length() > 14) {
			uid = uid.substring(0, 13);
		}*/
        Monitor.finish(uid);
        return uid;
    }

    private static final String INSTALLATION = "install";

    private synchronized static String fromUuid(Context context) {
        String uid = null;
        if (uid == null) {
            File installation = new File(context.getFilesDir(), INSTALLATION);
            try {
                if (!installation.exists())
                    writeInstallationFile(installation);
                uid = readInstallationFile(installation);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return uid;
    }

    private static String readInstallationFile(File installation) throws IOException {
        RandomAccessFile f = new RandomAccessFile(installation, "r");
        byte[] bytes = new byte[(int) f.length()];
        f.readFully(bytes);
        f.close();
        return new String(bytes);
    }

    private static void writeInstallationFile(File installation) throws IOException {
        FileOutputStream out = new FileOutputStream(installation);
        String id = UUID.randomUUID().toString();
        out.write(id.getBytes());
        out.close();
    }
}
