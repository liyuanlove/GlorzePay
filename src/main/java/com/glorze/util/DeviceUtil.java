package com.glorze.util;

/**
 * 设备工具类
 * @ClassName DeviceUtil 
 * @author: 高老四 
 * @since: 2018年9月6日 下午11:37:10
 */
public class DeviceUtil {

	/**
	 * 判断是否是移动设备
	 * @Title: isMobileDevice
	 * @param requestHeader
	 * @return boolean
	 * @author: 高老四
	 * @since: 2018年9月6日 下午11:37:16
	 */
	public static boolean isMobileDevice(String requestHeader){
		String[] deviceArray = new String[]{"android","iphone","windows phone"};  
		if(requestHeader == null) {  
            return false;
		}
		requestHeader = requestHeader.toLowerCase(); 
		for(int i=0;i<deviceArray.length;i++){  
            if(requestHeader.indexOf(deviceArray[i])>0){  
                return true;  
            }  
        }  
		return false;
	}
}
