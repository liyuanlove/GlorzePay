package com.glorze.util;

import javax.servlet.http.HttpServletRequest;

/**
 * IP处理工具类
 * @ClassName IpUtil 
 * @author: 高老四 
 * @since: 2018年8月27日 下午10:51:19
 */
public class IpUtil {
	
	/**
	 * 获取终端IP地址
	 * @Title: getRemortIP
	 * @param request
	 * @return String
	 * @author: 高老四
	 * @since: 2018年8月27日 下午10:51:42
	 */
	public static String getRemortIP(HttpServletRequest request) {  
        if (request.getHeader("x-forwarded-for") == null) {  
            return request.getRemoteAddr();  
        }  
        return request.getHeader("x-forwarded-for");  
    }
}
