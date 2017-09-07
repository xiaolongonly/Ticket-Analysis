package cn.xiaolong.ticketsystem.utils;

import android.text.TextUtils;

import okhttp3.MediaType;

/**
 * Created by LC on 2017/2/23.
 */

public class FileUtils {
    public static MediaType getImageMediaTypeFromFileName(String fileName) {
        if (!TextUtils.isEmpty(fileName)) {
            int index = fileName.lastIndexOf(".");
            if (index > 0) {
                String fileType = fileName.substring(index, fileName.length());
                switch (fileType) {
                    case ".png":
                        return MediaType.parse("image/png");
                    case ".jpg":
                    case ".jpeg":
                        return MediaType.parse("image/jpeg");
                    case ".gif":
                        return MediaType.parse("image/gif");
                    case ".bmp":
                        return MediaType.parse("image/bmp");
                    case ".wbmp":
                        return MediaType.parse("image/vnd.wap.wbmp");
                }
            }
        }
        return MediaType.parse("application/octet-stream");
    }
}
