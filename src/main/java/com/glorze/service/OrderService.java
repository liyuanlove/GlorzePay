package com.glorze.service;

import java.util.List;

import com.glorze.entity.Order;

/**
 * 订单Service接口
 * @ClassName OrderService 
 * @author: 高老四 
 * @since: 2018年8月28日 下午11:01:11
 */
public interface OrderService {

	/**
	 * 保存订单
	 * @Title: save
	 * @param order 
	 * @return void
	 * @author: 高老四
	 * @since: 2018年8月28日 下午11:01:17
	 */
	public void save(Order order);
	
	/**
	 * 根据订单编号获取订单
	 * @Title: getByOrderNo
	 * @param orderNo
	 * @return Order
	 * @author: 高老四
	 * @since: 2018年9月4日 下午10:50:54
	 */
	public Order getByOrderNo(String orderNo);
	
	/**
	 * 根据主键获取订单实体
	 * @Title: getById
	 * @param id
	 * @return Order
	 * @author: 高老四
	 * @since: 2018年9月4日 下午11:41:38
	 */
	public Order getById(Integer id);
	
	/**
	 * 分页查询订单信息
	 * @Title: list
	 * @param order
	 * @param page 第几页
	 * @param pageSize 每页多少条记录
	 * @return List<Order>
	 * @author: 高老四
	 * @since: 2018年9月6日 下午11:05:23
	 */
	public List<Order> list(Order order,Integer page,Integer pageSize);
	
	/**
	 * 获取订单总记录数
	 * @Title: getCount
	 * @param order
	 * @return Long
	 * @author: 高老四
	 * @since: 2018年9月6日 下午11:05:50
	 */
	public Long getCount(Order order);
}
