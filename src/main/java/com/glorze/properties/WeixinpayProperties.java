package com.glorze.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 微信支付配置文件映射组件
 * @ClassName WeixinpayProperties 
 * @author: 高老四 
 * @since: 2018年8月28日 下午9:41:50
 */
@Component
@ConfigurationProperties(prefix="weixin-pay-config")
public class WeixinpayProperties {

	/**
	 * 公众账号ID
	 */
	private String appId;
	
	/**
	 * 商户号
	 */
	private String mchId;
	
	/**
	 * 设备号
	 */
	private String deviceInfo;
	
	/**
	 * 商户的key【API密匙】
	 */
	private String key;
	
	/**
	 * api请求地址
	 */
	private String url;
	
	/**
	 * 服务器异步通知页面地址
	 */
	private String notifyUrl;
	
	/**
	 * 服务器同步通知页面地址
	 */
	private String returnUrl;

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getMchId() {
		return mchId;
	}

	public void setMchId(String mchId) {
		this.mchId = mchId;
	}

	public String getDeviceInfo() {
		return deviceInfo;
	}

	public void setDeviceInfo(String deviceInfo) {
		this.deviceInfo = deviceInfo;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getNotifyUrl() {
		return notifyUrl;
	}

	public void setNotifyUrl(String notifyUrl) {
		this.notifyUrl = notifyUrl;
	}

	public String getReturnUrl() {
		return returnUrl;
	}

	public void setReturnUrl(String returnUrl) {
		this.returnUrl = returnUrl;
	}

	@Override
	public String toString() {
		return "WeixinpayProperties [appId=" + appId + ", mchId=" + mchId + ", deviceInfo=" + deviceInfo + ", key="
				+ key + ", url=" + url + ", notifyUrl=" + notifyUrl + ", returnUrl=" + returnUrl + "]";
	}

}
