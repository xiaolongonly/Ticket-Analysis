package com.standards.library.util;

import android.annotation.SuppressLint;
import android.text.TextUtils;

import java.util.Locale;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;


public class DES {
    private static final String TAG = "TAG";
    private static final byte[] iv = {0, 0, 0, 0, 0, 0, 0, 0};

    /**
     * DES加密
     *
     * @param encryptString
     * @return
     * @throws Exception
     */
    @SuppressLint("TrulyRandom")
    public static String encryptDES(String KEY_DES, String encryptString) throws Exception {
        IvParameterSpec zeroIv = new IvParameterSpec(iv);
        SecretKeySpec key = new SecretKeySpec(KEY_DES.getBytes(), "DES");
        Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, key, zeroIv);
        byte[] encryptedData = cipher.doFinal(encryptString.getBytes());
        return parseByte2HexStr(encryptedData);
    }

    @SuppressLint("TrulyRandom")
    public static String encryptDES(String keyString, int keyPos, String encryptString, String ivString, int ivPos) throws Exception {
        LogUtil.d(TAG, "encryptString=" + encryptString);
        String keyValue = keyString.substring(keyPos - 1, 17);
        LogUtil.d(TAG, "key=" + keyValue);
        String ivValue = ivString.substring(ivPos - 1, 25);
        LogUtil.d(TAG, "iv=" + ivValue);
        IvParameterSpec zeroIv = new IvParameterSpec(ivValue.getBytes());
        SecretKeySpec key = new SecretKeySpec(keyValue.getBytes(), "DES");
        Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, key, zeroIv);
        byte[] encryptedData = cipher.doFinal(encryptString.getBytes());
        String encryptedStr = parseByte2HexStr(encryptedData);
        LogUtil.d(TAG, "encryptedStr=" + encryptedStr);
        return encryptedStr;
    }

    /**
     * 将二进制转换成十六进制
     *
     * @param buf
     * @return String
     */
    public static String parseByte2HexStr(byte buf[]) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < buf.length; i++) {
            String hex = Integer.toHexString(buf[i] & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            Locale loc = Locale.getDefault();
            sb.append(hex.toUpperCase(loc));
        }
        return sb.toString();
    }

    /**
     * 将十六进制转换成二进制
     *
     * @param
     * @return String
     */
    public static byte[] hexStringToBytes(String hexString) {
        if (hexString == null || hexString.equals("")) {
            return null;
        }
        Locale loc = Locale.getDefault();
        hexString = hexString.toUpperCase(loc);
        int length = hexString.length() / 2;
        char[] hexChars = hexString.toCharArray();
        byte[] d = new byte[length];
        for (int i = 0; i < length; i++) {
            int pos = i * 2;
            d[i] = (byte) (charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]));
        }
        LogUtil.d(TAG, "hexStringToBytes byte = " + d);
        return d;
    }

    /**
     * Convert char to byte
     *
     * @param c char
     * @return byte
     */
    private static byte charToByte(char c) {
        return (byte) "0123456789ABCDEF".indexOf(c);
    }

    /**
     * DES解密
     *
     * @param decryptString
     * @return
     * @throws Exception
     */
    public static String decryptDES(String keyString, String decryptString) throws Exception {
        if (!TextUtils.isEmpty(decryptString)) {

            byte[] byteMi = hexStringToBytes(decryptString);
            IvParameterSpec zeroIv = new IvParameterSpec(iv);
            byte[] byteKey = keyString.getBytes();
            SecretKeySpec key = new SecretKeySpec(byteKey, "DES");
            Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, key, zeroIv);
            byte decryptedData[] = cipher.doFinal(byteMi);
            return new String(decryptedData);
        }
        return "";
    }

    /**
     * DES解密
     *
     * @param keyString
     * @param keyPos
     * @param decryptString
     * @param ivString
     * @param ivPos
     * @return String
     * @throws Exception
     * @throw
     */
    @SuppressLint("TrulyRandom")
    public static String decryptDES(String keyString, int keyPos, String decryptString, String ivString, int ivPos) throws Exception {
        if (!TextUtils.isEmpty(decryptString)) {
            String keyValue = keyString.substring(keyPos - 1, 16);
            LogUtil.d(TAG, "key=" + keyValue);
            String ivValue = ivString.substring(ivPos - 1, 24);
            LogUtil.d(TAG, "iv=" + ivValue);
            byte[] byteMi = hexStringToBytes(decryptString);
            IvParameterSpec zeroIv = new IvParameterSpec(ivValue.getBytes());
            SecretKeySpec key = new SecretKeySpec(keyValue.getBytes(), "DES");
            Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, key, zeroIv);
            byte decryptedData[] = cipher.doFinal(byteMi);
            return new String(decryptedData);
        }
        return "";
    }

}
