package com.glorze.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradePayModel;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.alipay.api.request.AlipayTradeWapPayRequest;
import com.glorze.entity.Order;
import com.glorze.properties.AlipayProperties;
import com.glorze.service.OrderService;
import com.glorze.util.DateUtil;
import com.glorze.util.DeviceUtil;
import com.glorze.util.StringUtil;

/**
 * 支付宝支付控制器
 * @ClassName AlipayController 
 * @author: 高老四 
 * @since: 2018年8月28日 下午10:55:34
 */
@Controller
@RequestMapping("/aliPay")
public class AlipayController {

	@Resource
	private AlipayProperties alipayProperties;
	
	@Resource
	private OrderService orderService;
	
	private static Logger logger=Logger.getLogger(AlipayController.class);
	
	/**
	 * 支付请求
	 * @Title: pay
	 * @param order
	 * @param request
	 * @param response
	 * @throws Exception 
	 * @return void
	 * @author: 高老四
	 * @since: 2018年8月28日 下午11:12:45
	 */
	@RequestMapping("/pay")
	public void pay(Order order,HttpServletRequest request,HttpServletResponse response)throws Exception{
		
		// 生成订单号
		String orderNo=DateUtil.getCurrentDateStr(); 
		
		// 支付总金额
		String totalAmount=""; 
		
		// 订单名称
		String subject="";
		
		// 商品描述
		String goodsDesc=""; 
		switch(order.getGoodsNo()){
			case 1:
				totalAmount="8";
				subject="请四哥喝杯饮料";
				goodsDesc="08元-请四哥喝杯饮料";
				break;
			case 2:
				totalAmount="18";
				subject="给四哥买盒烟";
				goodsDesc="18元-给四哥买盒烟";
				break;
			case 3:
				totalAmount="28";
				subject="给四哥买盒杜蕾斯";
				goodsDesc="28元-给四哥买盒杜蕾斯";
				break; 
			default:
				totalAmount="88";
				subject="请四哥吃顿烧烤";
				goodsDesc="88元-请四哥吃顿烧烤";
				break;
		}
		order.setOrderNo(orderNo);
		order.setSubject(subject);
		order.setTotalAmount(totalAmount);
		order.setGoodsDesc(goodsDesc);
		
		orderService.save(order);
		
		// 封装请求客户端
		AlipayClient client=new DefaultAlipayClient(alipayProperties.getUrl(), alipayProperties.getAppId(), alipayProperties.getRsaPrivateKey(), alipayProperties.getFormat(), 
				alipayProperties.getCharset(), alipayProperties.getAlipayPublicKey(), alipayProperties.getSignType());
		
		// 生成的支付表单
		String form="";
		String userAgent=request.getHeader("user-agent");
		// 移动设备
		if(DeviceUtil.isMobileDevice(userAgent)) { 
			AlipayTradeWapPayRequest alipayRequest=new AlipayTradeWapPayRequest();
			// 支付请求
			alipayRequest.setReturnUrl(alipayProperties.getReturnUrl());
			alipayRequest.setNotifyUrl(alipayProperties.getNotifyUrl());
			AlipayTradePayModel model=new AlipayTradePayModel();
			// 设置销售产品码
			model.setProductCode("FAST_INSTANT_TRADE_PAY"); 
			// 设置订单号
			model.setOutTradeNo(orderNo); 
			// 订单名称
			model.setSubject(subject); 
			// 支付总金额
			model.setTotalAmount(totalAmount); 
			// 设置商品描述
			model.setBody(goodsDesc); 
			alipayRequest.setBizModel(model);
			// 生成表单
			form=client.pageExecute(alipayRequest).getBody(); 
		}else {
			AlipayTradePagePayRequest alipayRequest=new AlipayTradePagePayRequest();
			// 支付请求
			alipayRequest.setReturnUrl(alipayProperties.getReturnUrl());
			alipayRequest.setNotifyUrl(alipayProperties.getNotifyUrl());
			AlipayTradePayModel model=new AlipayTradePayModel();
			// 设置销售产品码
			model.setProductCode("FAST_INSTANT_TRADE_PAY"); 
			// 设置订单号
			model.setOutTradeNo(orderNo); 
			// 订单名称
			model.setSubject(subject); 
			// 支付总金额
			model.setTotalAmount(totalAmount); 
			// 设置商品描述
			model.setBody(goodsDesc); 
			alipayRequest.setBizModel(model);
			// 生成表单
			form=client.pageExecute(alipayRequest).getBody(); 
		}
		response.setContentType("text/html;charset=" + alipayProperties.getCharset()); 
		// 将完整的表单html输出到页面 
		response.getWriter().write(form); 
		response.getWriter().flush(); 
		response.getWriter().close();
	}
	
	/**
	 * 支付宝服务器异步通知
	 * @Title: notifyUrl
	 * @param request
	 * @throws Exception 
	 * @return void
	 * @author: 高老四
	 * @since: 2018年9月4日 下午10:55:31
	 */
	@RequestMapping("/notifyUrl")
	public void notifyUrl(HttpServletRequest request)throws Exception{
		logger.info("异步通知notifyUrl");
		// 获取支付宝GET过来反馈信息
		Map<String,String> params = new HashMap<String,String>(16);
		Map<String,String[]> requestParams = request.getParameterMap();
		for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext();) {
			String name = (String) iter.next();
			String[] values = (String[]) requestParams.get(name);
			String valueStr = "";
			for (int i = 0; i < values.length; i++) {
				valueStr = (i == values.length - 1) ? valueStr + values[i] : valueStr + values[i] + ",";
			}
			params.put(name, valueStr);
			logger.info("name:"+name+",valueStr:"+valueStr);
		}
		// 调用SDK验证签名
		boolean signVerified = AlipaySignature.rsaCheckV1(params, alipayProperties.getAlipayPublicKey(), 
				alipayProperties.getCharset(), alipayProperties.getSignType()); 
		// 商户订单号
		String outTradeNo = request.getParameter("out_trade_no");
		// 交易状态
		String tradeStatus = request.getParameter("trade_status");
		// 验证成功 更新订单信息
		String tradeFinished = "TRADE_FINISHED";
		String tradeSuccess = "TRADE_SUCCESS";
		if(signVerified){ 
			if(tradeFinished.equals(tradeStatus)){
				logger.info("TRADE_FINISHED");
			}
			if(tradeSuccess.equals(tradeStatus)){
				logger.info("TRADE_SUCCESS");
			}
			if(StringUtil.isNotEmpty(outTradeNo)){
				Order order=orderService.getByOrderNo(outTradeNo);
				if(null != order){
					// 支付时间
					order.setPayDate(new Date()); 
					// 设置支付状态为已经支付
					order.setIsPay(1); 
					orderService.save(order);
				}
			}
		}else{
			logger.error("验证未通过");
		}
	}
	
	/**
	 * 同步跳转
	 * @param request
	 * @throws Exception
	 */
	@RequestMapping("/returnUrl")
	public ModelAndView returnUrl(HttpServletRequest request)throws Exception{
		ModelAndView mav=new ModelAndView();
		mav.addObject("title", "同步通知地址_高老四博客_glorze.com");
		
		// 获取支付宝GET过来反馈信息
		Map<String,String> params = new HashMap<String,String>(16);
		Map<String,String[]> requestParams = request.getParameterMap();
		for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext();) {
			String name = (String) iter.next();
			String[] values = (String[]) requestParams.get(name);
			String valueStr = "";
			for (int i = 0; i < values.length; i++) {
				valueStr = (i == values.length - 1) ? valueStr + values[i] : valueStr + values[i] + ",";
			}
			params.put(name, valueStr);
			logger.info("name:"+name+",valueStr:"+valueStr);
		}
		// 调用SDK验证签名
		boolean signVerified = AlipaySignature.rsaCheckV1(params, alipayProperties.getAlipayPublicKey(), 
				alipayProperties.getCharset(), alipayProperties.getSignType()); 

		if(signVerified) {
			mav.addObject("message", "非常感谢，祝您生活愉快！");
		}else {
			mav.addObject("message", "验签失败");
		}
		mav.setViewName("returnUrl");
		return mav;
	}
	
}
