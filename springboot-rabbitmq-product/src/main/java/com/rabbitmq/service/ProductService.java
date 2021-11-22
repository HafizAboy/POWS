package com.rabbitmq.service;

import java.util.List;

import com.rabbitmq.model.Product;

/**
 * @author Hafiz
 * @version 0.01
 */
public interface ProductService {

    List<Product> findAllProducts();
    Product findByProdCode(String productCode);
	Product saveProduct(Product product) throws Exception;
	Product updateProduct(Product product) throws Exception;
	Product deleteByProdCode(String productCode) throws Exception;
	void deleteProduct(Product product) throws Exception;
}
