package com.glorze.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 支付宝支付配置文件映射组件
 * @ClassName AlipayProperties 
 * @author: 高老四 
 * @since: 2018年8月28日 下午9:34:17
 */
@Component
@ConfigurationProperties(prefix="ali-pay-config")
public class AlipayProperties {

	/**
	 * 商户appid
	 */
	private String appId;
	
	/**
	 * 私钥 pkcs8格式的
	 */
	private String rsaPrivateKey;
	
	/**
	 * 服务器异步通知页面地址,不能加参数
	 */
	private String notifyUrl;
	
	/**
	 * 页面跳转同步通知地址,不能加参数
	 */
	private String returnUrl;
	
	/**
	 * 支付宝网关地址
	 */
	private String url;
	
	/**
	 * 编码
	 */
	private String charset;
	
	/**
	 * 返回格式
	 */
	private String format;
	
	/**
	 * 支付宝公钥
	 */
	private String alipayPublicKey;
	
	/**
	 * 签名类型: RSA2
	 */
	private String signType;

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getRsaPrivateKey() {
		return rsaPrivateKey;
	}

	public void setRsaPrivateKey(String rsaPrivateKey) {
		this.rsaPrivateKey = rsaPrivateKey;
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

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getCharset() {
		return charset;
	}

	public void setCharset(String charset) {
		this.charset = charset;
	}

	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
	}

	public String getAlipayPublicKey() {
		return alipayPublicKey;
	}

	public void setAlipayPublicKey(String alipayPublicKey) {
		this.alipayPublicKey = alipayPublicKey;
	}

	public String getSignType() {
		return signType;
	}

	public void setSignType(String signType) {
		this.signType = signType;
	}

	@Override
	public String toString() {
		return "AlipayProperties [appId=" + appId + ", rsaPrivateKey=" + rsaPrivateKey + ", notifyUrl=" + notifyUrl
				+ ", returnUrl=" + returnUrl + ", url=" + url + ", charset=" + charset + ", format=" + format
				+ ", alipayPublicKey=" + alipayPublicKey + ", signType=" + signType + "]";
	}

}
