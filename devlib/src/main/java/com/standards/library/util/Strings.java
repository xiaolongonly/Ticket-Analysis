package com.standards.library.util;

public final class Strings {
	private static final String TAG = "Strings";
	private Strings() {}
	
	/**
	 * Judge if a String with a null value.
	 * @param src
	 * @return
	 */
	public final static boolean isNull(String src) {
		return src == null;
	}
	
	/**
	 * Judge if a String with a null value or with zero length.
	 * @param src
	 * @return
	 */
	public final static boolean isEmpty(String src) {
		return src == null || src.length() == 0;
	}
	
	/**
	 * Judge if a String with all whitespace.
	 * @param src
	 * @return
	 */
	public final static boolean isBlank(String src) {
		return src != null && "".equals(trimAll(src));
	}
	
	/**
	 * Judge if a String equals value "null".
	 * @param src
	 * @return
	 */
	public final static boolean equalsNull(String src) {
		return src != null && "null".equalsIgnoreCase(trimAll(src));
	}
	
	/**
	 * Ignore all whitespace in a String.
	 * @param src
	 * @return
	 */
	public final static String trimAll(String src) {
		if (src == null) {
			return null;
		}
		return src.replaceAll(" ", "");
	}
	
	/**
	 * Judge if a String with a detail meaning.
	 * @param src
	 * @return
	 */
	public final static boolean isMeaningful(String src) {
		return !isNull(src) && !isBlank(src) && !equalsNull(src);
	}
	
	/**
	 * Change bytes to Hex String in lowercase.
	 * @param bytes
	 * @return
	 */
	public final static String bytes2Hex(byte[] bytes) {
		return byte2Hex(bytes);
	}
	
	public final static String byte2Hex(byte[] src) {
		if (src == null) {   //remove src.length<=0, just for new byte[0] -> ""
            return null;   
        }
		StringBuilder sb = new StringBuilder();   
        for (int i = 0; i < src.length; i++) {   
            int v = src[i] & 0xFF;   
            String hv = Integer.toHexString(v);   
            if (hv.length() < 2) {   
                sb.append(0);   
            }   
            sb.append(hv);   
        }   
        return sb.toString();   
    }
	
	/**
	 * Parse a String into an int value, with default value 0 if exception(s) occur.
	 * @param input
	 * @return
	 */
	public final static int toInt(String input) {
		try {
			return Integer.parseInt(input);
		} catch (Exception e) {
			LogUtil.e(e.toString());
		}
		return 0;
	}
	
	/**
	 * Parse a String into a long value, with default value 0L if exception(s) occur.
	 * @param input
	 * @return
	 */
	public final static long toLong(String input) {
		try {
			return Long.parseLong(input);
		} catch (Exception e) {
			LogUtil.e(e.toString());
		}
		return 0;
	}
}
