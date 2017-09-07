package com.standards.library.app;

import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import com.standards.library.R;
import com.standards.library.BuildConfig;
import com.standards.library.cache.DirContext;
import com.standards.library.util.DeviceUidGenerator;
import com.standards.library.util.Utils;


public class AppContext {
    private static Context context;
    private static volatile AppContext appContext;
    public static String APP_VERSION_CODE;
    public static String APP_VERSION_NAME;
    public static String DEVICE_ID = BuildConfig.DEVICE_ID;
    public static String APP_PACKAGE_NAME = "com.yl.attendancesystem";

    private AppContext() {
    }

    public static Context getContext() {
        return context;
    }

    public static String getString(int resId) {
        return getContext().getResources().getString(resId);
    }

    public synchronized static AppContext getInstance() {
        if (appContext == null) {
            synchronized (AppContext.class) {
                if (appContext == null) {
                    appContext = new AppContext();
                }
            }
        }
        return appContext;
    }

    public synchronized void init(Context context) {
        AppContext.context = context;
        APP_VERSION_CODE = String.valueOf(Utils.getAppVersionCode(context));
        APP_VERSION_NAME = Utils.getAppVersionName(context);
//        try {
//            DEVICE_ID = DeviceUidGenerator.generate(context);
//        } catch (Exception e) {
//            e.printStackTrace();
//            DEVICE_ID = BuildConfig.DEVICE_ID;
//        }
        DirContext.getInstance().initCacheDir(context);
    }

    public static boolean isNetworkAvailable() {
        return Utils.isNetworkAvailable(context);
    }

    public static boolean isWifiActive() {
        return NetworkReceiver.isWifiActive;
    }

    private static boolean hasAvailableNetwork() {
        return NetworkReceiver.hasAvailableNetwork;
    }

    private static class NetworkReceiver extends BroadcastReceiver {
        private static boolean isWifiActive = false;
        private static boolean hasAvailableNetwork = false;

        @Override
        public void onReceive(Context context, Intent intent) {
            //存在不同进程，同时接收广播，造成多次Toast，所以需要判断
            if (APP_PACKAGE_NAME.equals(getCurProcessName(getContext()))) {
                return;
            }
            onReceiveDo(intent);
        }

        private synchronized void onReceiveDo(Intent intent) {
            String action = intent.getAction();
            if (ConnectivityManager.CONNECTIVITY_ACTION.equals(action)) {
                updateNetwork();
            }
        }

        private void updateNetwork() {
            ConnectivityManager connectivity = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            if (connectivity != null) {
                NetworkInfo mNetworkInfo = connectivity.getActiveNetworkInfo();
                if (mNetworkInfo != null && mNetworkInfo.isConnected()) {
                    hasAvailableNetwork = mNetworkInfo.isAvailable();
                    String type = mNetworkInfo.getTypeName();
                    if (type.equalsIgnoreCase("WIFI")) {
                        isWifiActive = true;
                    } else if (type.equalsIgnoreCase("MOBILE")) {
                        isWifiActive = false;
                    } else {
                        isWifiActive = false;
                    }
                } else {
                    hasAvailableNetwork = false;
                    isWifiActive = false;
                }
            }
        }

        private static String getCurProcessName(Context context) {
            int pid = android.os.Process.myPid();
            ActivityManager mActivityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
            for (ActivityManager.RunningAppProcessInfo appProcess : mActivityManager.getRunningAppProcesses()) {
                if (appProcess.pid == pid) {
                    return appProcess.processName;
                }
            }
            return null;
        }
    }


}
