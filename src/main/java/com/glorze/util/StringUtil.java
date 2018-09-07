package com.glorze.util;

import java.util.Random;

/**
 * 字符串工具类
 * @ClassName StringUtil 
 * @author: 高老四 
 * @since: 2018年8月22日 下午11:18:59
 */
public class StringUtil {

	/**
	 * 判断字符串是否为空
	 * @Title: isEmpty
	 * @param str
	 * @return boolean
	 * @author: 高老四
	 * @since: 2018年8月22日 下午11:19:17
	 */
	public static boolean isEmpty(String str){
		if(str==null||"".equals(str.trim())){
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * 判断字符串是否不为空
	 * @Title: isNotEmpty
	 * @param str
	 * @return 
	 * @return boolean
	 * @author: 高老四
	 * @since: 2018年8月22日 下午11:19:33
	 */
	public static boolean isNotEmpty(String str){
		if((str!=null)&&!"".equals(str.trim())){
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * 生成由[A-Z,0-9]生成的随机字符串
	 * @Title: getRandomString
	 * @param length 字符串长度
	 * @return 
	 * @return String
	 * @author: 高老四
	 * @since: 2018年8月22日 下午11:19:46
	 */
    public static String getRandomString(int length){
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for(int i = 0; i < length; ++i){
            int number = random.nextInt(2);
            long result = 0;
            switch(number){
                case 0:
                    result = Math.round(Math.random() * 25 + 65);
                    sb.append(String.valueOf((char)result));
                    break;
                case 1:
                    sb.append(String.valueOf(new Random().nextInt(10)));
                    break;
                default:
                	break;
             
            }
        }
        return sb.toString();
    }
    
    public static void main(String[] args) {
    	System.out.println(getRandomString(32));
	}
}
