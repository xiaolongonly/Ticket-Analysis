package com.standards.library.util;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;

public class PermissionUtil {
    public static final int REQUEST_CODE_REQUEST_PERMISSION = 1027;
    private static final String PACKAGE_URL_SCHEME = "package:";
    private static final int REQUEST_CODE_REQUEST_SETTING = 1028;

    public interface PermissionListener {
        void allGranted();                                          //申请的权限已全部被允许;
    }

    public static class PermissionTool {

        public String[] lackPermission;
        /**
         * 需要进行检测的权限数组
         */
        public String[] requestPermissions = {
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.CAMERA};


        /**
         * 需要进行检测的权限数组
         */
        public String[] qrCodeNeedPermissions = {       //获取相机权限
                Manifest.permission.CAMERA
        };
        /**
         * 需要进行检测的权限数组
         */
        public String[] requestNeedPermissions = {       //获取手机设备号用
                Manifest.permission.READ_PHONE_STATE
        };

        private PermissionListener mListener;

        public PermissionTool(PermissionListener listener) {
            mListener = listener;
        }

        public void checkAndRequestPermission(Activity activity, String... needPermissions) {
            boolean isAllGranted = true;
            for (String needPermission : needPermissions) {
                if (ActivityCompat.checkSelfPermission(activity, needPermission)
                        != PackageManager.PERMISSION_GRANTED) {
                    isAllGranted = false;
                }
            }
            if (isAllGranted) {
                mListener.allGranted();
            } else {
                ActivityCompat.requestPermissions(activity, needPermissions, REQUEST_CODE_REQUEST_PERMISSION);
            }

        }

        public void onRequestPermissionResult(final Activity activity,
                                              int requestCode, String[] permissions, int[] grantResults) {
            if (requestCode == REQUEST_CODE_REQUEST_PERMISSION) {
                boolean allGrant = true;
                for (int i = 0; i < grantResults.length; i++) {
                    if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                        allGrant = false;
                    }
                    LogUtil.d("PermissionTool#onRequestPermissionResult(): " + grantResults[i] + "permission" + permissions[i]);
                }
                if (allGrant) {
                    mListener.allGranted();
                } else {
                    ToastUtil.showToast("部分所需权限未开启，将影响功能正常使用，请开启权限！");
                    startAppSettings(activity);
                }
            }
        }

        public void startAppSettings(Activity activity) {
            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
            intent.setData(Uri.parse(PACKAGE_URL_SCHEME + activity.getPackageName()));
            activity.startActivityForResult(intent, REQUEST_CODE_REQUEST_SETTING);
        }

    }


}
