package com.standards.library.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;

public class Files {
    public static interface Type {
        String getExtension();
    }

    public enum FileType implements Type {
        JPEG("FFD8FF", "jpg"),
        PNG("89504E47", "png"),
        GIF("47494638", "gif"),
        BMP("424D", "bmp");

        private FileType(String symbol, String ext) {
            this.symbol = symbol;
            this.ext = ext;
        }

        private final String symbol;
        private final String ext;

        @Override
        public String getExtension() {
            return this.ext;
        }
    }

    /**
     * Get the type of a image file.
     * {@link #getImageFileType(File)}
     *
     * @return
     * @throws IllegalStateException if file is null/not exist/can not be read.
     */
    public static final String getImageFileType(String filepath) {
        return getImageFileType(new File(filepath));
    }

    /**
     * Get the type of a image file.
     *
     * @param file
     * @return
     * @throws IllegalStateException if file is null/not exist/can not be read.
     */
    public static final String getImageFileType(File file) {
        if (file == null || !file.exists() || !file.canRead()) {
            throw new IllegalStateException("file is not found or can not read");
        }

        try {
            String symbol = getSymbolFromFile(file);
            for (FileType type : FileType.values()) {
                if (type.getExtension().equals(symbol)) {
                    return symbol;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Check if a file is gif pic.
     * {@link #isGif(File)}
     *
     * @return
     * @throws IllegalStateException if file is null/not exist/can not be read.
     */
    public static final boolean isGif(String filepath) {
        return isGif(new File(filepath));
    }

    /**
     * Check if a file is gif pic.
     *
     * @param file
     * @return
     * @throws IllegalStateException if file is null/not exist/can not be read.
     */
    public static final boolean isGif(File file) {
        if (file == null || !file.exists() || !file.canRead()) {
            throw new IllegalStateException("file is not found or can not read");
        }

        try {
            return FileType.GIF.symbol.equals(getSymbolFromFile(file));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static final boolean isGif(byte[] bytes) {
        if (bytes == null || bytes.length < 4) {
            return false;
        }
        byte[] b = new byte[4];
        System.arraycopy(bytes, 0, b, 0, b.length);

        return FileType.GIF.symbol.equals(Strings.bytes2Hex(b));
    }

    private static String getSymbolFromFile(File file) throws IOException {
        byte[] b = new byte[4];
        InputStream is = null;
        try {
            is = new FileInputStream(file);
            is.read(b, 0, b.length);
        } finally {
            if (is != null) {
                is.close();
            }
        }
        return Strings.bytes2Hex(b);
    }

    private static boolean isInSameDir(File src, File dest) {
        String srcParent = src.getParent();
        String destParent = dest.getParent();
        if (srcParent == null && destParent == null) {
            return true;
        }
        return srcParent != null && destParent != null && srcParent.equals(destParent);
    }

    public static boolean rename(File src, File dest) {
        boolean ret = false;
        if (!isInSameDir(src, dest)) {
            ret = move(src, dest);
        } else {
            ret = src.renameTo(dest);
        }
        return ret;
    }

    public static boolean rename(String srcPath, String destPath) {
        return rename(new File(srcPath), new File(destPath));
    }

    /**
     * 创建空文件
     *
     * @param path 待创建的文件路径
     * @param size 空文件大小
     * @throws IOException
     * @return 创建是否成功
     */
    public static boolean createEmptyFile(String path, long size) throws IOException {
        File file = new File(path);
        File parent = file.getParentFile();
        parent.mkdirs();
        RandomAccessFile raf = null;
        raf = new RandomAccessFile(file, "rw");
        raf.setLength(size);
        raf.close();
        return true;
    }

    /**
     * 删除文件或者目录
     *
     * @param path 指定路径的文件或目录
     * @return 返回操作结果
     */
    public static boolean delete(String path) {
        File file = new File(path);
        if (!file.exists()) {
            return true;
        }

        if (file.isDirectory()) {
            String[] subPaths = file.list();
            for (int i = 0; i < subPaths.length; i++) {
                if (!delete(path + File.separator + subPaths[i])) {
                    return false;
                }
            }
        }

        return file.delete();
    }

    /**
     * 创建目录，包括必要的父目录的创建，如果未创建
     *
     * @param path 待创建的目录路径
     * @return 返回操作结果
     */
    public static boolean mkdir(String path) {
        File file = new File(path);
        if (file.exists() && file.isDirectory()) {
            return true;
        }

        return file.mkdirs();
    }

    /**
     * 创建文件，包括必要的父目录的创建，如果未创建
     *
     * @param file 待创建的文件
     * @return 返回操作结果
     * @throws IOException 创建失败，将抛出该异常
     */
    public static boolean create(File file) throws IOException {
        if (file == null) {
            return false;
        }
        if (file.exists()) {
            return true;
        }

        File parent = file.getParentFile();
        if (parent != null && !parent.exists()) {
            parent.mkdirs();
        }
        return file.createNewFile();
    }

    /**
     * 复制文件
     *
     * @param srcPath  源文件路径
     * @param destPath 目标文件路径
     * @return 返回操作结果
     */
    public static boolean copy(String srcPath, String destPath) {
        return copy(new File(srcPath), new File(destPath));
    }

    /**
     * 复制文件
     *
     * @param src  源文件
     * @param dest 目标文件
     * @return 返回操作结果
     * @throws FileNotFoundException
     */
    public static boolean copy(File src, File dest) {
        if (!src.exists()) {
            return false;
        }

        FileInputStream fis = null;
        FileOutputStream fos = null;
        try {
            fis = new FileInputStream(src);
            create(dest);
            fos = new FileOutputStream(dest);

            byte[] buffer = new byte[2048];
            int bytesread = 0;
            while ((bytesread = fis.read(buffer)) != -1) {
                if (bytesread > 0) {
                    fos.write(buffer, 0, bytesread);
                }
            }

            return true;

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (Exception e2) {
                }
            }

            if (fos != null) {
                try {
                    fos.close();
                } catch (Exception e2) {
                }
            }
        }
    }

    /**
     * 移动文件
     *
     * @param srcPath  源文件路径
     * @param destPath 目标文件路径
     * @return 返回操作结果
     */
    public static boolean move(String srcPath, String destPath) {
        File src = new File(srcPath);
        File dest = new File(destPath);

        boolean ret = copy(src, dest);
        if (ret) {
            delete(srcPath);
        }

        return ret;
    }

    /**
     * 移动文件
     *
     * @param src  源文件
     * @param dest 目标文件
     * @return 返回操作结果
     */
    public static boolean move(File src, File dest) {
        boolean ret = copy(src, dest);
        if (ret) {
            ret = delete(src.getAbsolutePath());
        }

        return ret;
    }
}
