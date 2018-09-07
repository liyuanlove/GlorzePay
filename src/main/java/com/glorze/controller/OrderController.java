package com.glorze.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.glorze.entity.Order;
import com.glorze.service.OrderService;

/**
 * 订单控制器
 * @ClassName OrderController 
 * @author: 高老四 
 * @since: 2018年9月6日 下午11:14:46
 */
@Controller
@RequestMapping("/order")
public class OrderController {

	@Resource
	private OrderService orderService;
	
	/**
	 * 跳转订单列表页面
	 * @Title: toOrderListPage
	 * @return ModelAndView
	 * @author: 高老四
	 * @since: 2018年9月6日 下午11:15:01
	 */
	@RequestMapping("/toOrderListPage")
	public ModelAndView toOrderListPage(){
		ModelAndView mav=new ModelAndView();
		mav.addObject("title", "订单列表_高老四博客_glorze.com");
		mav.setViewName("orderList");
		return mav;
	}
	
	/**
	 * 分页查询订单信息
	 * @Title: orderList
	 * @param page 第几页
	 * @param limit 每页数据个数
	 * @return Map<String,Object>
	 * @author: 高老四
	 * @since: 2018年9月6日 下午11:15:45
	 */
	@ResponseBody
	@RequestMapping("/orderList")
	public Map<String,Object> orderList(@RequestParam(value="page", required=false) Integer page,
			@RequestParam(value="limit", required=false) Integer limit){
		Map<String,Object> resultMap=new HashMap<>(16);
		Order searchOrder=new Order();
		List<Order> orderList = orderService.list(searchOrder, page, limit);
		Long count = orderService.getCount(searchOrder);
		resultMap.put("code", 0);
		resultMap.put("count", count);
		resultMap.put("data", orderList);
		return resultMap;
	}
}
