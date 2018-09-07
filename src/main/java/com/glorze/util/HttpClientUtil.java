package com.glorze.util;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;

/**
 * HttpClient工具类
 * @ClassName HttpClientUtil 
 * @author: 高老四 
 * @since: 2018年8月22日 下午11:16:52
 */
public class HttpClientUtil {

	/**
	 * 发送xml数据
	 * @Title: sendXMLDataByPost
	 * @param url
	 * @param xmlData
	 * @throws ClientProtocolException
	 * @throws IOException 
	 * @return HttpResponse
	 * @author: 高老四
	 * @since: 2018年8月22日 下午11:17:04
	 */
	public static HttpResponse sendXMLDataByPost(String url, String xmlData)
            throws ClientProtocolException, IOException {
        HttpClient httpClient = HttpClients.createDefault();
        HttpPost httppost = new HttpPost(url);
        StringEntity entity = new StringEntity(xmlData);
        httppost.setEntity(entity);
        httppost.setHeader("Content-Type", "text/xml;charset=UTF-8");
        HttpResponse response = httpClient.execute(httppost);
        return response;
    }
	
	/**
	 * 发送xml数据 https
	 * @Title: sendXMLDataByHttpsPost
	 * @param url
	 * @param xmlData
	 * @throws ClientProtocolException
	 * @throws IOException 
	 * @return HttpResponse
	 * @author: 高老四
	 * @since: 2018年8月23日 下午11:49:47
	 */
	public static HttpResponse sendXMLDataByHttpsPost(String url, String xmlData)
            throws ClientProtocolException, IOException {
        HttpPost httppost = new HttpPost(url);
        StringEntity entity = new StringEntity(xmlData);
        httppost.setEntity(entity);
        httppost.setHeader("Content-Type", "text/xml;charset=UTF-8");
        HttpResponse response=null;
		try {
			// response = HttpClients.custom().setSSLSocketFactory(CertUtil.initCert()).build().execute(httppost);
		} catch (Exception e) {
			e.printStackTrace();
		}
        return response;
    }
}
