package com.standards.library.cache;

import android.content.Context;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Disker {
    protected File rootDir, cacheDir, imageDir;

    public Disker(Context context, String path) {
        initDir(context, path);
    }

    private void initDir(Context context, String path) {
        rootDir = DirContext.getInstance().getRootDir();
        cacheDir = DirContext.getInstance().getDir(DirContext.DirEnum.CACHE);
        imageDir = DirContext.getInstance().getDir(DirContext.DirEnum.IMAGE);
    }

    public synchronized void putStringToDisker(String content, String name) {
        File file = getFile(cacheDir, name);
        FileWriter fw = null;
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
            fw = new FileWriter(file);
            fw.write(content);
            fw.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (fw != null) {
                    fw.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public synchronized String getStringFromDisk(String name) throws IOException {
        StringBuffer sb = new StringBuffer();
        File file = getFile(cacheDir, name);
        if (!file.exists()) {
            return null;
        }
        FileReader fr = null;
        try {
            fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line + "\n");
            }
            return sb.toString();
        } finally {
            if (fr != null) {
                fr.close();
            }
        }
    }

    private File getFile(File dir, String name) {
        return new File(dir, name);
    }

    private void deleteFile(File dir) {
        File[] files = dir.listFiles(new FileFilter() {
            @Override
            public boolean accept(File pathname) {
                return pathname.lastModified() < System.currentTimeMillis() - 24 * 3600 * 1000;
            }
        });
        if (files == null) {
            return;
        }
        for (int i = 0; i < files.length; i++) {
            files[i].delete();
        }
    }

    public long getSize() {
        return getFileSize(cacheDir) + getFileSize(imageDir);
    }

    private long getFileSize(File file) {
        if (file.exists()) {
            if (file.isDirectory()) {
                long size = 0;
                File[] list = file.listFiles();
                for (File f : list) {
                    size += getFileSize(f);
                }
                return size;
            } else {
                return file.length();
            }
        }
        return 0;
    }

    public void deleteCache() {
        deleteCache(cacheDir, imageDir);
//        deleteCache(imageDir);
    }

    public void deleteCache(final File... dirs) {
        for (File dir : dirs) {
            File[] files = dir.listFiles();
            if (files == null) {
                return;
            }
            for (int i = 0; i < files.length; i++) {
                files[i].delete();
            }
        }
    }
}
