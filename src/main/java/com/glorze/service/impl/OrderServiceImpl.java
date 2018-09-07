package com.glorze.service.impl;

import java.util.List;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.glorze.entity.Order;
import com.glorze.repository.OrderRepository;
import com.glorze.service.OrderService;

/**
 * 订单Service实现类
 * @ClassName OrderServiceImpl 
 * @author: 高老四 
 * @since: 2018年8月28日 下午11:01:59
 */
@Service("orderService")
public class OrderServiceImpl implements OrderService{

	@Resource
	private OrderRepository orderRepository;
	
	@Override
	public void save(Order order) {
		orderRepository.save(order);
	}

	@Override
	public Order getByOrderNo(String orderNo) {
		return orderRepository.getByOrderNo(orderNo);
	}

	@Override
	public Order getById(Integer id) {
		return orderRepository.getOne(id);
	}

	@Override
	public List<Order> list(Order order, Integer page, Integer pageSize) {
		Pageable pageable = PageRequest.of(page-1,pageSize,Sort.Direction.DESC,"payDate");
		Page<Order> pageOrder = orderRepository.findAll(new Specification<Order>() {
			@Override
			public Predicate toPredicate(Root<Order> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				Predicate predicate=cb.conjunction();
				predicate.getExpressions().add(cb.equal(root.get("isPay"), 1));
				return predicate;
			}
		}, pageable);
		return pageOrder.getContent();
	}

	@Override
	public Long getCount(Order order) {
		return orderRepository.count(new Specification<Order>() {
			@Override
			public Predicate toPredicate(Root<Order> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				Predicate predicate=cb.conjunction();
				predicate.getExpressions().add(cb.equal(root.get("isPay"), 1));
				return predicate;
			}
		});
	}
	
}
