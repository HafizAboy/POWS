package com.rabbitmq.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rabbitmq.model.Order;

/**
 * @author Hafiz
 * @version 0.01
 */
public interface OrderRepository extends JpaRepository<Order, UUID> {

	Order findByOrderNumber(String orderNumber);
	Order deleteByOrderNumber(String orderNumber) throws Exception;
}
