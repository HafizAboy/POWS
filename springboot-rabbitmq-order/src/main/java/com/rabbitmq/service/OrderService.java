package com.rabbitmq.service;

import java.util.List;

import com.rabbitmq.model.Order;

/**
 * @author Hafiz
 * @version 0.01
 */
public interface OrderService {

    List<Order> findAllOrders();
    Order findByOrderNumber(String orderNumber);
	Order saveOrder(Order order) throws Exception;
	Order updateOrder(Order order) throws Exception;
	Order deleteByOrderNumber(String orderNumber) throws Exception;
	void deleteOrder(Order order) throws Exception;
}
