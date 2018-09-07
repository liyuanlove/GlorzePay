package com.glorze.util;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;

import javax.annotation.Resource;

import com.glorze.properties.WeixinpayProperties;

/**
 * 微信支付签名工具类
 * @ClassName SignUtil 
 * @author: 高老四 
 * @since: 2018年8月27日 下午10:11:00
 */
public class SignUtil {
	
	@Resource
	private WeixinpayProperties weixinpayProperties;
	
	/**
	 * 微信支付签名算法sign
	 * @Title: getSign
	 * @param map
	 * @return String
	 * @author: 高老四
	 * @since: 2018年8月27日 下午10:09:48
	 */
    public static String getSign(Map<String,Object> map, String key) {
        StringBuffer sb = new StringBuffer();
        String[] keyArr = (String[]) map.keySet().toArray(new String[map.keySet().size()]);
        Arrays.sort(keyArr);
        for (int i = 0, size = keyArr.length; i < size; ++i) {
            if ("sign".equals(keyArr[i])) {
                continue;
            }
            sb.append(keyArr[i] + "=" + map.get(keyArr[i]) + "&");
        }
        sb.append("key=" + key);
        String sign = Md5Util.string2MD5(sb.toString());
        return sign;
    }
    
    /**
     * 是否签名正确,规则是: 按参数名称a-z排序,遇到空值的参数不参加签名。    
     * @Title: isTenpaySign
     * @param characterEncoding
     * @param packageParams
     * @param API_KEY
     * @return boolean
     * @author: 高老四
     * @since: 2018年9月5日 上午12:02:14
     */
    public static boolean isTenpaySign(String characterEncoding, SortedMap<Object, Object> packageParams, String apiKey) {    
        StringBuffer sb = new StringBuffer();    
        Set es = packageParams.entrySet();    
        Iterator it = es.iterator();    
        while(it.hasNext()) {    
            Map.Entry entry = (Map.Entry)it.next();    
            String k = (String)entry.getKey();    
            String v = (String)entry.getValue();    
            if(!"sign".equals(k) && null != v && !"".equals(v)) {    
                sb.append(k + "=" + v + "&");    
            }    
        }    
        sb.append("key=" + apiKey);    
            
        //算出摘要    
        String mySign = Md5Util.md5Encode(sb.toString(), characterEncoding).toLowerCase();
        String tenpaySign = ((String)packageParams.get("sign")).toLowerCase();    
        return tenpaySign.equals(mySign);    
    } 
}
