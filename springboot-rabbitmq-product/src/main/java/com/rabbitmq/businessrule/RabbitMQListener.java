package com.rabbitmq.businessrule;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.rabbitmq.config.MQConfig;
import com.rabbitmq.constants.ErrorEnum;
import com.rabbitmq.exceptions.WebserviceException;
import com.rabbitmq.model.Product;
import com.rabbitmq.service.ProductService;

@Component
public class RabbitMQListener {

	protected final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private ProductService productService;

    @RabbitListener(queues = MQConfig.QUEUE)
    public void listener(ProductOrder prodOrder) {
        System.out.println(prodOrder);
        int updatedStock;

		Product savedProduct = new Product();
		Product productFindByProductCode = productService.findByProdCode(prodOrder.getProductCode());
		updatedStock = productFindByProductCode.getStock() - prodOrder.getStock();
		logger.info("UpdatedStock:- "+updatedStock);
		productFindByProductCode.setStock(updatedStock);
		try {
			logger.info("Update Product via RabbitMQ");
			savedProduct = productService.updateProduct(productFindByProductCode);
			logger.info("Product updated via RabbitMQ");
		}catch (Exception e) {
			logger.error("Error:- Unable to update product via RabbitMQ");
			throw new WebserviceException(ErrorEnum.SAVING_UNSUCCESSFUL, ErrorEnum.SAVING_UNSUCCESSFUL.getDescription());
		}
    }

}
