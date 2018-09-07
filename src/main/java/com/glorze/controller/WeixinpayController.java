package com.glorze.controller;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URLEncoder;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.glorze.entity.Order;
import com.glorze.properties.WeixinpayProperties;
import com.glorze.service.OrderService;
import com.glorze.util.DateUtil;
import com.glorze.util.DeviceUtil;
import com.glorze.util.HttpClientUtil;
import com.glorze.util.IpUtil;
import com.glorze.util.SignUtil;
import com.glorze.util.StringUtil;
import com.glorze.util.XmlUtil;

/**
 * 微信支付控制器
 * @ClassName WeixinpayController 
 * @author: 高老四 
 * @since: 2018年9月4日 下午11:43:08
 */
@Controller
@RequestMapping("/weixinPay")
public class WeixinpayController {

	@Resource
	private WeixinpayProperties weixinpayProperties;
	
	@Resource
	private OrderService orderService;
	
	private static Logger logger=Logger.getLogger(WeixinpayController.class);
	
	/**
	 * 微信支付
	 * @Title: pay
	 * @param order
	 * @param request
	 * @param response
	 * @throws Exception 
	 * @return ModelAndView
	 * @author: 高老四
	 * @since: 2018年9月4日 下午11:45:57
	 */
	@RequestMapping("/pay")
	public ModelAndView pay(Order order,HttpServletRequest request,HttpServletResponse response)throws Exception{
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
	
		order.setSubject(subject);
		order.setTotalAmount(totalAmount);
		order.setGoodsDesc(goodsDesc);
		
		orderService.save(order);

		String userAgent=request.getHeader("user-agent");
		// 移动设备
		if(DeviceUtil.isMobileDevice(userAgent)) {
			// 生成订单号
			String orderNo=DateUtil.getCurrentDateStr(); 
			Map<String,Object> map=new HashMap<String,Object>(16);
			map.put("appid", weixinpayProperties.getAppId());
			map.put("mch_id", weixinpayProperties.getMchId());
			map.put("device_info", weixinpayProperties.getDeviceInfo());
			map.put("notify_url", weixinpayProperties.getNotifyUrl());
			// 交易类型
			map.put("trade_type", "MWEB"); 
			map.put("out_trade_no", orderNo);
			map.put("body", order.getGoodsDesc());
			map.put("total_fee", Integer.parseInt(order.getTotalAmount())*100);
			// 随机串
			map.put("nonce_str", StringUtil.getRandomString(30));
			// 终端IP
			map.put("spbill_create_ip", IpUtil.getRemortIP(request)); 
			// 签名
			map.put("sign", SignUtil.getSign(map, weixinpayProperties.getKey())); 
			String xml=XmlUtil.genXml(map);
			logger.info(xml);
			// 发送xml消息
			InputStream in = HttpClientUtil.sendXMLDataByPost(weixinpayProperties.getUrl().toString(),xml).getEntity().getContent(); 
			String mwebUrl=getElementValue(in,"mweb_url");
			logger.info("mwebUrl:"+mwebUrl);
			// 拼接跳转地址 
			mwebUrl+="&redirect_url="+URLEncoder.encode(weixinpayProperties.getReturnUrl(),"GBK");
			logger.info("编码：mweb_url:"+mwebUrl);
			// 设置订单号
			order.setOrderNo(orderNo); 
			// 保存订单信息
     		orderService.save(order); 
     		response.sendRedirect(mwebUrl);
     		return null;
		}
		ModelAndView mav=new ModelAndView();
		mav.addObject("order",order);
		mav.addObject("title", "微信扫码在线支付_高老四博客_glorze.com");
		mav.setViewName("weixinPay");
		return mav;
	}
	
	/**
	 * 加载支付二维码
	 * @Title: loadPayImage
	 * @param request
	 * @param response
	 * @throws Exception 
	 * @return void
	 * @author: 高老四
	 * @since: 2018年9月4日 下午11:51:30
	 */
	@RequestMapping("/loadPayImage")
	public void loadPayImage(HttpServletRequest request,HttpServletResponse response)throws Exception{
		String id=request.getParameter("id");
		Order order=orderService.getById(Integer.parseInt(id));
		// 生成订单号
		String orderNo=DateUtil.getCurrentDateStr(); 
		Map<String,Object> map=new HashMap<String,Object>(16);
		map.put("appid", weixinpayProperties.getAppId());
		map.put("mch_id", weixinpayProperties.getMchId());
		map.put("device_info", weixinpayProperties.getDeviceInfo());
		map.put("notify_url", weixinpayProperties.getNotifyUrl());
		// 交易类型
		map.put("trade_type", "NATIVE"); 
		map.put("out_trade_no", orderNo);
		map.put("body", order.getGoodsDesc());
		map.put("total_fee", Integer.parseInt(order.getTotalAmount())*100);
		// 随机串
		map.put("nonce_str", StringUtil.getRandomString(30)); 
		// 终端IP
		// map.put("spbill_create_ip", IpUtil.getRemortIP(request)); 
		map.put("spbill_create_ip", "127.0.0.1");
		// 签名
		map.put("sign", SignUtil.getSign(map, weixinpayProperties.getKey())); 
		String xml=XmlUtil.genXml(map);
		logger.info("微信二维码加载请求xml信息: " + xml);
		// 发送xml消息
		InputStream in = HttpClientUtil.sendXMLDataByPost(weixinpayProperties.getUrl().toString(),xml).getEntity().getContent(); 
		String codeUrl=getElementValue(in,"code_url"); 
		MultiFormatWriter multiFormatWriter = new MultiFormatWriter();  
	    Map hints = new HashMap(16);  
	    BitMatrix bitMatrix = null;  
	    try {  
	    	bitMatrix = multiFormatWriter.encode(codeUrl, BarcodeFormat.QR_CODE, 250, 250,hints);  
	    	BufferedImage image = toBufferedImage(bitMatrix);  
	    	//输出二维码图片流  
	    	try {  
	    		ImageIO.write(image, "png", response.getOutputStream());  
	    	} catch (IOException e) {  
	    		e.printStackTrace();  
	    	}finally{
	    		// 设置订单号
	    		order.setOrderNo(orderNo); 
	    		orderService.save(order); 
	    	}
	    } catch (WriterException e1) {  
	    	e1.printStackTrace();  
	    }           
	}
	
	/**
	 * 微信支付服务器异步回调通知
	 * @Title: notifyUrl
	 * @param request
	 * @throws Exception 
	 * @return void
	 * @author: 高老四
	 * @since: 2018年9月4日 下午11:56:01
	 */
	@RequestMapping("/notifyUrl")
	public void notifyUrl(HttpServletRequest request)throws Exception{
		logger.info("notifyUrl");
		//读取参数    
        InputStream inputStream ;    
        StringBuffer sb = new StringBuffer();    
        inputStream = request.getInputStream();    
        String s ;    
        BufferedReader in = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));    
        while ((s = in.readLine()) != null){    
            sb.append(s);    
        }    
        in.close();    
        inputStream.close();    
    
        // 解析xml成map    
        Map<String, String> m = new HashMap<String, String>(16);    
        m = XmlUtil.doXMLParse(sb.toString());    
            
        // 过滤空,设置TreeMap    
        SortedMap<Object,Object> packageParams = new TreeMap<Object,Object>();          
        Iterator<String> it = m.keySet().iterator();    
        while (it.hasNext()) {    
            String parameter = it.next();    
            String parameterValue = m.get(parameter);    
                
            String v = "";    
            if(null != parameterValue) {    
                v = parameterValue.trim();    
            } 
            logger.info("p:"+parameter+",v:"+v);
            packageParams.put(parameter, v);    
        }  
        
        // 微信支付的API密钥    
        String key = weixinpayProperties.getKey();  
        // 验证通过
        String utf8 = "UTF-8";
        String success = "SUCCESS";
        String resultCode = "result_code";
        if(SignUtil.isTenpaySign(utf8, packageParams, key)){ 
        	if(success.equals((String)packageParams.get(resultCode))){  
        		String outTradeNo=(String) packageParams.get("out_trade_no");
        		Order order=orderService.getByOrderNo(outTradeNo);
        		if(order!=null){
					order.setPayDate(new Date());
					order.setIsPay(1);
					orderService.save(order);
				}
        	}
        }else{
        	logger.error("验证未通过");
        }
	}
	
	/**
	 * 查询订单支付状态
	 * @Title: loadPayState
	 * @param id
	 * @throws Exception 
	 * @return Integer
	 * @author: 高老四
	 * @since: 2018年9月4日 下午11:57:31
	 */
	@ResponseBody
	@RequestMapping("/loadPayState")
	public Integer loadPayState(Integer id)throws Exception{
		Order order=orderService.getById(id);
		return order.getIsPay();
	}
	
	/**
	 * 微信支付同步通知页面
	 * @Title: returnUrl
	 * @throws Exception 
	 * @return ModelAndView
	 * @author: 高老四
	 * @since: 2018年9月4日 下午11:57:58
	 */
	@RequestMapping("/returnUrl")
	public ModelAndView returnUrl() throws Exception{
		ModelAndView mav=new ModelAndView();
		mav.addObject("title", "同步通知地址_高老四博客");
		mav.addObject("message", "非常感谢，祝您生活愉快");
		mav.setViewName("returnUrl");
		return mav;
	}
	
	/**
	 * 类型转换  
	 * @Title: toBufferedImage
	 * @param matrix
	 * @return 
	 * @return BufferedImage
	 * @author: 高老四
	 * @since: 2018年9月4日 下午11:58:23
	 */
	public static BufferedImage toBufferedImage(BitMatrix matrix) {  
		int width = matrix.getWidth();  
		int height = matrix.getHeight();  
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);  
		for (int x = 0; x < width; x++) {  
			for (int y = 0; y < height; y++) {  
				image.setRGB(x, y, matrix.get(x, y) == true ? 0xff000000 : 0xFFFFFFFF);  
			}  
		}  
		return image;  
	}  
	
	/**
	 * 过返回IO流获取支付地址
	 * @Title: getElementValue
	 * @param in
	 * @param key
	 * @throws Exception 
	 * @return String
	 * @author: 高老四
	 * @since: 2018年9月4日 下午11:59:01
	 */
	private String getElementValue(InputStream in,String key)throws Exception{
		SAXReader reader = new SAXReader();
		Document document = reader.read(in);
	    Element root = document.getRootElement();
	    List<Element> childElements = root.elements();
	    for (Element child : childElements) {
	    	if(key.equals(child.getName())){
	    		return child.getStringValue();
	    	}
	    }
	    return null;
	}
}
