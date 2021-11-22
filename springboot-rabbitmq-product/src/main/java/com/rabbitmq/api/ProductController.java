package com.rabbitmq.api;

import java.util.List;
import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.rabbitmq.constants.ErrorEnum;
import com.rabbitmq.exceptions.WebserviceException;
import com.rabbitmq.model.Product;
import com.rabbitmq.service.ProductService;

import io.swagger.annotations.ApiOperation;

/**
 * @author Aboy
 * @version 0.01
 */
@CrossOrigin
@RestController
@RequestMapping("/products")
public class ProductController {

	protected final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private ProductService productService;

	/**
	 * Fetch a list of products
	 * @return a list of products
	 * @throws Exception 
	 */
	@RequestMapping(path="", method = RequestMethod.GET, produces = "application/json")
	@ApiOperation(value = "Fetch all products")
	public ResponseEntity<?> products() throws Exception {
		List<Product> products = (List<Product>) productService.findAllProducts();

		return new ResponseEntity<>(products, HttpStatus.OK);
	}

	/**
	 * Finds a product by <code>productCode</code>
	 * 
	 * @param productCode product's productCode
	 * 
	 * @return the {@link Product} object
	 * @throws Exception 
	 */
	@RequestMapping(path = "/getProductByProductCode/{productCode}", 
			method = RequestMethod.GET)
	@ApiOperation(value = "Fetch a product")
	public ResponseEntity<?> product(@PathVariable String productCode) throws Exception {
		Product product = new Product();
		product = productService.findByProdCode(productCode);
		
		return new ResponseEntity<>(product, HttpStatus.OK);
	}

	/**
	 * Add a product
	 * 
	 * @param product
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(path = "",
			method = RequestMethod.POST,
			consumes =  MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "Create a new product")
	public ResponseEntity<Product> addProduct(@RequestBody Product product) throws Exception {

		Product savedProduct = new Product();

		// validate the input
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		Validator validator = factory.getValidator();
		Set<ConstraintViolation<Product>> violations = validator.validate(product);

		logger.error("size of violations : " + violations.size());

		for (ConstraintViolation<Product> constraintViolation : violations) {
			logger.error("constraintViolation: field \"" + constraintViolation.getPropertyPath() + "\"," + constraintViolation.getMessage());
			throw new WebserviceException(ErrorEnum.REQUIRED_ELEMENT_MISSING, constraintViolation.getMessage());
		}
		// validation - End

		if(violations.size() == 0) {
			logger.info("Validation Success!");

			try {
				logger.info("Saving Product");
				savedProduct =  productService.saveProduct(product);
				logger.info("Product creation completed");
			}catch (Exception e) {
				logger.error("Error:- Unable to create product");
				throw new WebserviceException(ErrorEnum.SAVING_UNSUCCESSFUL, ErrorEnum.SAVING_UNSUCCESSFUL.getDescription());
			}
		}
		return new ResponseEntity<Product>(savedProduct, HttpStatus.CREATED);
	}

	/**
	 * Updates the product
	 * 
	 * @param product
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(path = "/{productCode}",
			method = RequestMethod.PUT)
	@ApiOperation(value = "Update a product")
	public ResponseEntity<Product> updateProduct(@PathVariable String productCode, @RequestBody Product product) throws Exception {

		Product savedProduct = new Product();
		logger.info("Prod code: "+productCode);
		Product productFindByProductCode = productService.findByProdCode(productCode);
		// validate the input
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		Validator validator = factory.getValidator();
		Set<ConstraintViolation<Product>> violations = validator.validate(product);

		logger.error("size of violations : " + violations.size());

		for (ConstraintViolation<Product> constraintViolation : violations) {
			logger.error("constraintViolation: field \"" + constraintViolation.getPropertyPath() + "\"," + constraintViolation.getMessage());
			throw new WebserviceException(ErrorEnum.REQUIRED_ELEMENT_MISSING, constraintViolation.getMessage());
		}
		// validation - End

		if(violations.size() == 0) {
			logger.info("Validation Success!");

			logger.info("Selected Order no: "+productFindByProductCode.getProductCode());
			productFindByProductCode.setPrice(product.getPrice());
			productFindByProductCode.setProductName(product.getProductName());
			productFindByProductCode.setStock(product.getStock());
			
			try {
				logger.info("Update Product");
				savedProduct = productService.updateProduct(productFindByProductCode);
				logger.info("Product updated");
			}catch (Exception e) {
				logger.error("Error:- Unable to update product");
				throw new WebserviceException(ErrorEnum.SAVING_UNSUCCESSFUL, ErrorEnum.SAVING_UNSUCCESSFUL.getDescription());
			}
		}
		
		return new ResponseEntity<Product>(savedProduct, HttpStatus.OK);
	}


	/**
	 * Deletes product identified with <code>productCode</code>
	 * @param productCode
	 * @throws Exception 
	 */
	@RequestMapping(path = "/{productCode}", 
			method = RequestMethod.DELETE)
	@ApiOperation(value = "Delete a product")
	public ResponseEntity<?> deleteProduct(@PathVariable String productCode) throws Exception {

		Product productFindByProductCode = productService.findByProdCode(productCode);
		try {
			logger.info("Delete Product");
			productService.deleteProduct(productFindByProductCode);
			logger.info("Product Deleted");
		}catch (Exception e) {
			logger.error("Error:- Unable to delete product");
			throw new WebserviceException(ErrorEnum.DELETION_UNSUCCESSFUL, ErrorEnum.DELETION_UNSUCCESSFUL.getDescription());
		}
		return new ResponseEntity<>(HttpStatus.OK);
	}
}
