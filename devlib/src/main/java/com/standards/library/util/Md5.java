package com.standards.library.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Md5 {
	
	private Md5() {}
	
	private static synchronized MessageDigest checkAlgorithm() {
		MessageDigest messageDigest;
		try {
			messageDigest = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			throw new NullPointerException("No md5 algorithm found");
		}
		return messageDigest;
	}

	public final static String digest32(String src) {
		if (src == null) {
			return null;
		}
		MessageDigest messageDigest = checkAlgorithm();
		byte[] ret = null;
		try {
			ret = messageDigest.digest(src.getBytes("UTF-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		return ret == null ? null : bytesToHex(ret);
	}
	
	public final static String digest32(String src, String charset) {
		if (src == null) {
			return null;
		}
		MessageDigest messageDigest = checkAlgorithm();
		byte[] ret = null;
		try {
			ret = messageDigest.digest(src.getBytes(charset));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		return ret == null ? null : bytesToHex(ret);
	}
	
	public final static String digest32(File src) throws IOException {
		if (src == null) {
			return null;
		}
		MessageDigest messageDigest = checkAlgorithm();
		InputStream fis = null;
		DigestInputStream dis = null;
		try {
			fis = new FileInputStream(src);
			dis = new DigestInputStream(fis, messageDigest);
			byte[] buffer = new byte[2048];
			while (dis.read(buffer) > 0) {
				;
			}
			messageDigest = dis.getMessageDigest();
		} finally {
			if (fis != null) {
				fis.close();
			}
			if (dis != null) {
				dis.close();
			}
		}
		return bytesToHex(messageDigest.digest());
	}
	
	public final static String digest32(byte[] src) {
		if (src == null) {
			return null;
		}
		MessageDigest messageDigest = checkAlgorithm();
		byte[] ret = messageDigest.digest(src);
		return ret == null ? null : bytesToHex(ret);
	}
	
	public final static String digest16(String src) {
		String encrypt = digest32(src);
		return encrypt == null ? null : encrypt.substring(8, 24);
	}
	
	public final static String digest16(String src, String charset) {
		String encrypt = digest32(src, charset);
		return encrypt == null ? null : encrypt.substring(8, 24);
	}
	
	public final static String digest16(File src) throws IOException {
		String encrypt = digest32(src);
		return encrypt == null ? null : encrypt.substring(8, 24);
	}
	
	public final static String digest16(byte[] src) {
		String encrypt = digest32(src);
		return encrypt == null ? null : encrypt.substring(8, 24);
	}
	
	private final static String bytesToHex(byte[] bytes) {
		return Strings.bytes2Hex(bytes);
	}
}
