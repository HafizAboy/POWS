package com.rabbitmq.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rabbitmq.model.Product;
import com.rabbitmq.repository.ProductRepository;

/**
 * @author Hafiz
 * @version 0.01
 */
@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
	private ProductRepository prodRepo;
	
	@Override
	public Product saveProduct(Product product) throws Exception {
		// TODO Auto-generated method stub
		return prodRepo.save(product);
	}

	@Override
	public List<Product> findAllProducts() {
		// TODO Auto-generated method stub
		return (List<Product>)prodRepo.findAll();
	}

	@Override
	public Product findByProdCode(String productCode) {
		// TODO Auto-generated method stub
		return prodRepo.findByProductCode(productCode);
	}

	@Override
	public Product updateProduct(Product product) throws Exception {
		// TODO Auto-generated method stub
		return saveProduct(product);
	}

	@Override
	public Product deleteByProdCode(String productCode) throws Exception {
		// TODO Auto-generated method stub
		return prodRepo.deleteByProductCode(productCode);
	}

	@Override
	public void deleteProduct(Product person) throws Exception {
		// TODO Auto-generated method stub
		prodRepo.delete(person);
	}
}
