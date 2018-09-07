package com.glorze.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.glorze.entity.Order;

/**
 * 订单Repository接口
 * @ClassName OrderRepository 
 * @author: 高老四 
 * @since: 2018年8月28日 下午10:59:22
 */
public interface OrderRepository extends JpaRepository<Order, Integer>,JpaSpecificationExecutor<Order>{
	
	/**
	 * 根据订单编号查询订单记录
	 * @Title: getByOrderNo
	 * @param orderNo
	 * @return Order
	 * @author: 高老四
	 * @since: 2018年9月4日 下午10:49:22
	 */
	@Query(value="select * from t_order where order_no=?1",nativeQuery=true)
	public Order getByOrderNo(String orderNo);
	
}
