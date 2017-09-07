package com.standards.library.cache;

import android.content.Context;
import android.content.SharedPreferences;

import com.standards.library.app.AppContext;


public class SPHelp {

    public enum SP_FILE {
        APP("app"), USER("user");

        private String ns;

        SP_FILE(String ns) {
            this.ns = ns;
        }

        public String getNameSpace() {
            return this.ns;
        }
    }

    private static final SharedPreferences getPref(SP_FILE pref) {
        return getPref(AppContext.getContext().getApplicationContext(), pref);
    }

    private static final SharedPreferences getPref(Context context, SP_FILE pref) {
        return context.getSharedPreferences(pref.getNameSpace(), Context.MODE_PRIVATE);
    }

    public static void setAppParam(String key, Object object) {
        setParam(AppContext.getContext().getApplicationContext(), false, key, object);
    }

    public static void setAppParam(Context context, String key, Object object) {
        setParam(context, false, key, object);
    }

    public static void setUserParam(String key, Object object) {
        setParam(AppContext.getContext().getApplicationContext(), true, key, object);
    }

    public static void setUserParam(Context context, String key, Object object) {
        setParam(context, true, key, object);
    }

    public static void setAccessParam(String key, Object object) {
        setParam(AppContext.getContext().getApplicationContext(), true, key, object);
    }

    public static void setAccessParam(Context context, String key, Object object) {
        setParam(context, true, key, object);
    }

    public static Object getAppParam(String key, Object defaultObject) {
        return getAppParam(AppContext.getContext(), key, defaultObject);
    }

    public static Object getAppParam(Context context, String key, Object defaultObject) {
        return getParam(context, false, key, defaultObject);
    }

    public static Object getUserParam(String key, Object defaultObject) {
        return getUserParam(AppContext.getContext(), key, defaultObject);
    }

    public static Object getUserParam(Context context, String key, Object defaultObject) {
        return getParam(context, true, key, defaultObject);
    }

    public static Object getAccessParam(String key, Object defaultObject) {
        return getAccessParam(AppContext.getContext(), key, defaultObject);
    }

    public static Object getAccessParam(Context context, String key, Object defaultObject) {
        return getParam(context, true, key, defaultObject);
    }

    /**
     * 保存数据的方法，我们需要拿到保存数据的具体类型，然后根据类型调用不同的保存方法
     *
     * @param key
     * @param object
     */
    private static void setParam(Context context, boolean isUserParams, String key, Object object) {
        String type = object.getClass().getSimpleName();
        SharedPreferences.Editor editor = getPref(context, isUserParams ? SP_FILE.USER : SP_FILE.APP).edit();

        if ("String".equals(type)) {
            editor.putString(key, (String) object);
        } else if ("Integer".equals(type)) {
            editor.putInt(key, (Integer) object);
        } else if ("Boolean".equals(type)) {
            editor.putBoolean(key, (Boolean) object);
        } else if ("Float".equals(type)) {
            editor.putFloat(key, (Float) object);
        } else if ("Long".equals(type)) {
            editor.putLong(key, (Long) object);
        }

        editor.commit();
    }


    /**
     * 得到保存数据的方法，我们根据默认值得到保存的数据的具体类型，然后调用相对于的方法获取值
     *
     * @param key
     * @param defaultObject
     * @return
     */
    private static Object getParam(Context context, boolean isUserParams, String key, Object defaultObject) {
        String type = defaultObject.getClass().getSimpleName();
        SharedPreferences sp = getPref(context, isUserParams ? SP_FILE.USER : SP_FILE.APP);

        if ("String".equals(type)) {
            return sp.getString(key, (String) defaultObject);
        } else if ("Integer".equals(type)) {
            return sp.getInt(key, (Integer) defaultObject);
        } else if ("Boolean".equals(type)) {
            return sp.getBoolean(key, (Boolean) defaultObject);
        } else if ("Float".equals(type)) {
            return sp.getFloat(key, (Float) defaultObject);
        } else if ("Long".equals(type)) {
            return sp.getLong(key, (Long) defaultObject);
        }

        return null;
    }

}
