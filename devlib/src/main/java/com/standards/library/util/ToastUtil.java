package com.standards.library.util;

import android.content.Context;
import android.widget.Toast;

import com.standards.library.app.AppContext;


public class ToastUtil {
    private static Toast sToast;


    public static void showToast(String msg) {
        showToast(AppContext.getContext(), msg);
    }

    public static void showToast(int msgResId) {
        showToast(AppContext.getContext(),AppContext.getContext().getResources().getString(msgResId));
    }

    public static void showToast(Context context, int msgResId) {
        showToast(context, context.getResources().getString(msgResId));
    }

    public static void showToast(Context context, String msg) {
        try {
            if (msg == null || msg.trim().length() == 0) {
                return;
            }
            if (sToast == null) {
                sToast = Toast.makeText(context.getApplicationContext(), msg, Toast.LENGTH_SHORT);
                sToast.show();
            } else {
                sToast.setText(msg);
                sToast.setDuration(Toast.LENGTH_SHORT);
                sToast.show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void showLongToast(Context context, int msgResId) {
        showLongToast(context, context.getResources().getString(msgResId));
    }

    public static void showLongToast(Context context, String msg) {
        try {
            if (msg == null || msg.trim().length() == 0) {
                return;
            }
            if (sToast == null) {
                sToast = Toast.makeText(context.getApplicationContext(), msg, Toast.LENGTH_LONG);
                sToast.show();
            } else {
                sToast.setText(msg);
                sToast.setDuration(Toast.LENGTH_LONG);
                sToast.show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void cancelToast() {
        if (sToast != null) {
            sToast.cancel();
        }
    }
}
