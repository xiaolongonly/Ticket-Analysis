package cn.xiaolong.ticketsystem.utils;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import cn.xiaolong.ticketsystem.base.BaseActivity;
import cn.xiaolong.ticketsystem.ui.web.WebActivity;
import cn.xiaolong.ticketsystem.ui.web.WebConfig;


/**
 * <启动Activity的工具类>
 *
 * @data: 2016/1/4 18:41
 * @version: V1.0
 */
public class LaunchUtil {

    public static boolean launchActivity(Context context, Class<? extends BaseActivity> cls) {
        return launchActivity(context, cls, null);
    }

    public static boolean launchActivity(Context context, Class<? extends BaseActivity> cls, Bundle bundle) {
        return launchActivity(context, cls, bundle, 0);
    }

    public static boolean launchActivity(Context context, Class<? extends BaseActivity> cls, Bundle bundle, int requestCode) {
        Intent intent = new Intent(context, cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        if (requestCode != 0) {
            ((BaseActivity) context).startActivityForResult(intent, requestCode);
        } else {
            context.startActivity(intent);
        }
        return true;
    }

    public static boolean launchDefaultWeb(Context context, String url, String title) {
        WebConfig webConfig = new WebConfig(url, title, true);
        return launchActivity(context, WebActivity.class, WebActivity.buildBundle(webConfig));
    }

}
