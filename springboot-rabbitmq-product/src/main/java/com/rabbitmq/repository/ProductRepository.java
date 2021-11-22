package com.rabbitmq.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rabbitmq.model.Product;

/**
 * @author Hafiz
 * @version 0.01
 */
public interface ProductRepository extends JpaRepository<Product, UUID> {

	Product findByProductCode(String productCode);
	Product deleteByProductCode(String productCode) throws Exception;
}
