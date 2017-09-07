package com.standards.library.cache;

import android.content.Context;

import com.standards.library.util.Utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class DirContext {

    private static DirContext sInstance = null;

    private String mCacheDir;

    public enum DirEnum {

        ROOT_dir("School"), CACHE("cache"), IMAGE("image"), DOWNLOAD("download");

        private String value;

        DirEnum(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

    private DirContext() {
        initDirContext();
    }

    public static DirContext getInstance() {
        if (sInstance == null) {
            sInstance = new DirContext();
        }
        return sInstance;
    }

    private void initDirContext() {

    }

    public void initCacheDir(Context context) {
        this.mCacheDir = Utils.getDiskCacheDir(context, "").getAbsolutePath();
    }

    public File getRootDir() {
        File file = new File(
                android.os.Environment.getExternalStorageDirectory(),
                DirEnum.ROOT_dir.getValue());

        if (!file.exists() || !file.isDirectory()) {
            file.mkdirs();
        }

        return file;
    }

    public File getDir(DirEnum dirEnum) {
        File file = new File(getRootDir(), dirEnum.getValue());
        if (!file.exists() || !file.isDirectory()) {
            file.mkdirs();
        }
        return file;
    }


    public static void copyFile(File sourceFile, File targetFile)
            throws IOException {
        // 新建文件输入流并对它进行缓冲
        FileInputStream input = new FileInputStream(sourceFile);
        BufferedInputStream inBuff = new BufferedInputStream(input);

        // 新建文件输出流并对它进行缓冲
        FileOutputStream output = new FileOutputStream(targetFile);
        BufferedOutputStream outBuff = new BufferedOutputStream(output);

        // 缓冲数组
        byte[] b = new byte[1024 * 5];
        int len;
        while ((len = inBuff.read(b)) != -1) {
            outBuff.write(b, 0, len);
        }
        // 刷新此缓冲的输出流
        outBuff.flush();

        //关闭流
        inBuff.close();
        outBuff.close();
        output.close();
        input.close();
    }
}