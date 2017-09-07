package cn.xiaolong.ticketsystem.utils;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import cn.xiaolong.ticketsystem.R;

/**
 * <请描述这个类是干什么的>
 * 状态栏适配
 * @data: 2016/6/29 15:49
 * @version: V1.0
 */
public class StatusBarCompat {
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public static void compat(Activity activity, LinearLayout linearLayout, StatusBarValue statusBarValue) {
        if (statusBarValue.isStatusBarOccupying && Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            View statusView = createStatusView(activity, activity.getResources().getColor(statusBarValue.statusBarColor));
            linearLayout.addView(statusView, 0);
//            setStatusBarTextColor(activity, statusBarValue);
        }
    }

    private static void setStatusBarTextColor(Activity activity, StatusBarValue statusBarValue) {
        if (statusBarValue.statusBarColor == android.R.color.white
                || statusBarValue.statusBarColor == android.R.color.transparent
                || statusBarValue.statusBarColor == R.color.windowBackground) {
            setStatusBarTextColorBlack(activity, true);
        }
    }

    private static View createStatusView(Activity activity, int color) {
        int statusBarHeight = getStatusBarHeight(activity);
        View statusView = new View(activity);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, statusBarHeight);
        statusView.setLayoutParams(params);
        statusView.setBackgroundColor(color);
        return statusView;
    }

    public static int getStatusBarHeight(Context context) {
        int result = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    /**
     * 只支持MIUI V6
     * 沉浸式菜单栏只能在MIUI 6的系统上实现，其他安卓系统没有效果。
     * 沉浸式效果对非MIUI系统的兼容性不会有任何影响。
     * google的actionbar存在bug，不支持沉浸代码。
     *
     * @param dark true黑色字体 false清除黑色字体 0--只需要状态栏透明 1-状态栏透明且黑色字体 2-清除黑色字体
     */
    public static void setStatusBarTextColorBlack(Activity activity, boolean dark) {
        Class clazz = activity.getWindow().getClass();
        try {
            try {
                int darkModeFlag = 0;
                Class<?> layoutParams = Class.forName("android.view.MiuiWindowManager$LayoutParams");
                Field field = layoutParams.getField("EXTRA_FLAG_STATUS_BAR_DARK_MODE");
                darkModeFlag = field.getInt(layoutParams);
                Method extraFlagField = clazz.getMethod("setExtraFlags", int.class, int.class);
//                extraFlagField.invoke(activity.getWindow(), true ? darkModeFlag : type, darkModeFlag);
                extraFlagField.invoke(activity.getWindow(), dark ? darkModeFlag : 0, darkModeFlag);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {

        }
    }


    /**
     * 设置状态栏图标为深色和魅族特定的文字风格
     * 可以用来判断是否为Flyme用户
     *
     * @param window 需要设置的窗口
     * @param dark   是否把状态栏字体及图标颜色设置为深色
     * @return boolean 成功执行返回true
     */
    public static boolean FlymeSetStatusBarLightMode(Window window, boolean dark) {
        boolean result = false;
        if (window != null) {
            try {
                WindowManager.LayoutParams lp = window.getAttributes();
                Field darkFlag = WindowManager.LayoutParams.class
                        .getDeclaredField("MEIZU_FLAG_DARK_STATUS_BAR_ICON");
                Field meizuFlags = WindowManager.LayoutParams.class
                        .getDeclaredField("meizuFlags");
                darkFlag.setAccessible(true);
                meizuFlags.setAccessible(true);
                int bit = darkFlag.getInt(null);
                int value = meizuFlags.getInt(lp);
                if (dark) {
                    value |= bit;
                } else {
                    value &= ~bit;
                }
                meizuFlags.setInt(lp, value);
                window.setAttributes(lp);
                result = true;
            } catch (Exception e) {

            }
        }
        return result;
    }

    /**官方在Android6.0中提供了亮色状态栏模式，配置很简单：
     *  if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
     *      activity.getWindow().getDecorView().setSystemUiVisibility( View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN|View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
     *  }
     */
}
