package com.standards.library.util;

import com.standards.library.BuildConfig;
import com.orhanobut.logger.Logger;

public final class LogUtil {
    private static boolean sDebug = BuildConfig.NETWORK_LOG;

    public static synchronized void init(boolean debug, String tag) {
        sDebug = debug;
        if (debug) {
            Logger.init(tag).hideThreadInfo().methodCount(0);
        }
    }

    public static void d(String tag, String msg) {
        if (sDebug) {
            Logger.t(tag).d(msg);
        }
    }

    public static void d(String msg) {
        if (sDebug) {
            Logger.d(msg);
        }
    }

    public static void e(String msg) {
        if (sDebug) {
            Logger.e(msg);
        }
    }

    public static void json(String msg) {
        if (sDebug) {
            Logger.json(msg);
        }
    }
}
