package com.glorze.util;

import java.security.MessageDigest;

/**
 * Md5加密工具类
 * @ClassName Md5Util 
 * @author: 高老四 
 * @since: 2018年8月22日 下午11:17:48
 */
public class Md5Util {

	/**
	 * 盐加密 生成32位md5码
	 * @Title: string2MD5
	 * @param str
	 * @return String
	 * @author: 高老四
	 * @since: 2018年8月22日 下午11:18:10
	 */
    public static String string2MD5(String str){
        if (str == null || str.length() == 0) {
            return null;
        }
        char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                'a', 'b', 'c', 'd', 'e', 'f' };

        try {
            MessageDigest mdTemp = MessageDigest.getInstance("MD5");
            mdTemp.update(str.getBytes("UTF-8"));

            byte[] md = mdTemp.digest();
            int j = md.length;
            char buf[] = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                buf[k++] = hexDigits[byte0 >>> 4 & 0xf];
                buf[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(buf).toUpperCase();
        } catch (Exception e) {
            return null;
        }
    }
    
    /**
     * MD5验签
     * @Title: MD5Encode
     * @param origin
     * @param charsetname
     * @return String
     * @author: 高老四
     * @since: 2018年8月23日 下午9:41:18
     */
    public static String md5Encode(String origin, String charsetname) {    
        String resultString = null;    
        try {    
            resultString = new String(origin);    
            MessageDigest md = MessageDigest.getInstance("MD5");    
            if (charsetname == null || "".equals(charsetname)) {    
                resultString = byteArrayToHexString(md.digest(resultString    
                        .getBytes()));    
            }else {    
                resultString = byteArrayToHexString(md.digest(resultString    
                        .getBytes(charsetname)));    
            }
        } catch (Exception exception) {    
        }    
        return resultString;    
    }    
    
    private static String byteArrayToHexString(byte b[]) {    
        StringBuffer resultSb = new StringBuffer();    
        for (int i = 0; i < b.length; i++) {    
            resultSb.append(byteToHexString(b[i]));    
        }
        return resultSb.toString();    
    }    
    
    private static String byteToHexString(byte b) {    
        int n = b;    
        if (n < 0) {    
            n += 256;   
        }
        int d1 = n / 16;    
        int d2 = n % 16;    
        return HEX_DIGITS[d1] + HEX_DIGITS[d2];    
    }    
    
    private static final String[] HEX_DIGITS = { "0", "1", "2", "3", "4", "5",    
            "6", "7", "8", "9", "a", "b", "c", "d", "e", "f" };
}
