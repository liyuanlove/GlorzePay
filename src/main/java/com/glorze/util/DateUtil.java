package com.glorze.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 日期工具类
 * @ClassName DateUtil 
 * @author: 高老四 
 * @since: 2018年8月22日 下午10:42:56
 */
public class DateUtil {

	/**
	 * 根据格式生成当前日期的字符串
	 * @Title: getCurrentDateStr
	 * @return 
	 * @return String
	 * @author: 高老四
	 * @since: 2018年8月22日 下午10:43:11
	 */
	public static String getCurrentDateStr(){
		Date date=new Date();
		SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMddhhmmssSSS");
		return sdf.format(date);
	}
	
	public static void main(String[] args) throws Exception {
		System.out.println(getCurrentDateStr());
	}
	
}
