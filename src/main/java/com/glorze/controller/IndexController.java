package com.glorze.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * 首页请求
 * @ClassName IndexController 
 * @author: 高老四 
 * @since: 2018年8月28日 下午9:57:08
 */
@Controller
public class IndexController {

	/**
	 * 网页根目录请求
	 * @Title: root
	 * @return ModelAndView
	 * @author: 高老四
	 * @since: 2018年8月28日 下午9:57:16
	 */
	@RequestMapping("/")
	public ModelAndView root(){
		ModelAndView mav=new ModelAndView();
		mav.addObject("title", "在线支付_赞赏本站_高老四博客");
		mav.setViewName("index");
		return mav;
	}
}
