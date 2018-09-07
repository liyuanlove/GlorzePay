package com.glorze.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * 订单表
 * @ClassName Order 
 * @author: 高老四 
 * @since: 2018年8月28日 下午10:37:57
 */
@Entity
@Table(name="t_order")
public class Order {

	/**
	 * 主键
	 */
	@Id
	@GeneratedValue
	private Integer id;
	
	/**
	 * 订单号
	 */
	@Column(length=200)
	private String orderNo;
	
	/**
	 * 商品编号,id
	 */
	private Integer goodsNo;
	
	/**
	 * 订单标题,名称
	 */
	@Column(length=200)
	private String subject;
	
	/**
	 * 商品描述
	 */
	@Column(length=800)
	private String goodsDesc;
	
	/**
	 * 总金额,商品价格
	 */
	@Column(length=50)
	private String totalAmount; 
	
	/**
	 * 支付日期
	 */
	@Temporal(TemporalType.TIMESTAMP)
	private Date payDate; 
	
	/**
	 * 支付人姓名或昵称
	 */
	@Column(length=50)
	private String nickName;
	
	/**
	 * 客户qq号码
	 */
	@Column(length=20)
	private String qq; 
	
	/**
	 * 客户留言
	 */
	@Column(length=1000)
	private String message;
	
	/**
	 * 支付方式
	 */
	@Column(length=10)
	private String way;
	
	/**
	 * 是否支付:
	 * 	0.未支付
	 * 	1.已经支付
	 */
	private int isPay;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	
	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(String totalAmount) {
		this.totalAmount = totalAmount;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getQq() {
		return qq;
	}

	public void setQq(String qq) {
		this.qq = qq;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getWay() {
		return way;
	}

	public void setWay(String way) {
		this.way = way;
	}

	public int getIsPay() {
		return isPay;
	}

	public void setIsPay(int isPay) {
		this.isPay = isPay;
	}

	public Integer getGoodsNo() {
		return goodsNo;
	}

	public void setGoodsNo(Integer goodsNo) {
		this.goodsNo = goodsNo;
	}

	public String getGoodsDesc() {
		return goodsDesc;
	}

	public void setGoodsDesc(String goodsDesc) {
		this.goodsDesc = goodsDesc;
	}

	@JsonSerialize(using=CustomDateTimeSerializer.class)
	public Date getPayDate() {
		return payDate;
	}

	public void setPayDate(Date payDate) {
		this.payDate = payDate;
	}

	@Override
	public String toString() {
		return "Order [id=" + id + ", orderNo=" + orderNo + ", goodsNo=" + goodsNo + ", subject=" + subject
				+ ", goodsDesc=" + goodsDesc + ", totalAmount=" + totalAmount + ", payDate=" + payDate + ", nickName="
				+ nickName + ", qq=" + qq + ", message=" + message + ", way=" + way + ", isPay=" + isPay + "]";
	}
	
}
