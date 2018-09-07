package com.glorze.util;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

/**
 * xml工具类
 * @ClassName XmlUtil 
 * @author: 高老四 
 * @since: 2018年8月22日 下午11:21:55
 */
public class XmlUtil {
	
	/**
	 * 生成xml格式的字符串
	 * @Title: genXml
	 * @param map
	 * @return String
	 * @author: 高老四
	 * @since: 2018年8月22日 下午11:22:10
	 */
	public static String genXml(Map<String,Object> map){
		StringBuffer sb=new StringBuffer();
		sb.append("<xml>");
		for(String k:map.keySet()){
			Object value=map.get(k);
			sb.append("<"+k+">"+value+"</"+k+">");
		}
		sb.append("</xml>");
		try {
			return new String(sb.toString().getBytes(),"ISO8859-1");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 解析xml,返回第一级元素键值对。如果第一级元素有子节点,则此节点的值是子节点的xml数据。     
	 * @Title: doXMLParse
	 * @param strxml
	 * @throws JDOMException
	 * @throws IOException 
	 * @return Map
	 * @author: 高老四
	 * @since: 2018年8月23日 下午9:39:51
	 */
    public static Map doXMLParse(String strxml) throws JDOMException, IOException {    
        strxml = strxml.replaceFirst("encoding=\".*\"", "encoding=\"UTF-8\"");    
        if(null == strxml || "".equals(strxml)) {    
            return null;    
        }    
        Map m = new HashMap(16);    
        InputStream in = new ByteArrayInputStream(strxml.getBytes("UTF-8"));    
        SAXBuilder builder = new SAXBuilder();    
        Document doc = builder.build(in);    
        Element root = doc.getRootElement();    
        List list = root.getChildren();    
        Iterator it = list.iterator();    
        while(it.hasNext()) {    
            Element e = (Element) it.next();    
            String k = e.getName();    
            String v = "";    
            List children = e.getChildren();    
            if(children.isEmpty()) {    
                v = e.getTextNormalize();    
            } else {    
                v = XmlUtil.getChildrenText(children);    
            }    
            m.put(k, v);    
        }    
        //关闭流    
        in.close();    
        return m;    
    }
    
    /**
     * 获取子结点的xml   
     * @Title: getChildrenText
     * @param children
     * @return String
     * @author: 高老四
     * @since: 2018年8月23日 下午9:40:15
     */
    public static String getChildrenText(List children) {    
        StringBuffer sb = new StringBuffer();    
        if(!children.isEmpty()) {    
            Iterator it = children.iterator();    
            while(it.hasNext()) {    
                Element e = (Element) it.next();    
                String name = e.getName();    
                String value = e.getTextNormalize();    
                List list = e.getChildren();    
                sb.append("<" + name + ">");    
                if(!list.isEmpty()) {    
                    sb.append(XmlUtil.getChildrenText(list));    
                }    
                sb.append(value);    
                sb.append("</" + name + ">");    
            }    
        }    
        return sb.toString();    
    }
}
