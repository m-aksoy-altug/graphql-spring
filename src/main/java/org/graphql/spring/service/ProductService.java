package org.graphql.spring.service;

import java.math.BigInteger;
import java.util.List;

import org.graphql.spring.dto.CustomerDto;
import org.graphql.spring.dto.ProductCategoriesDto;
import org.graphql.spring.entity.Customer;
import org.graphql.spring.entity.ProductCategories;
import org.graphql.spring.exception.NotFoundException;
import org.graphql.spring.repo.ProductCategoriesRepo;
import org.graphql.spring.utils.Constant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

@Service
public class ProductService {
	private static final Logger log= LoggerFactory.getLogger(ProductService.class);
	private final Sinks.Many<ProductCategoriesDto> productCategorySink = Sinks.many().multicast().onBackpressureBuffer();
	
	@Autowired
	private ProductCategoriesRepo productCategoriesRepo;
	
	public Flux<ProductCategoriesDto> getProductCategoriesStream() {
		return productCategorySink.asFlux();
	}
	
	public List<ProductCategoriesDto> getAllProductCategories() {
	    List<ProductCategories> productCategories = productCategoriesRepo.findAll();
	    if (productCategories.isEmpty()) {
	        throw new NotFoundException(Constant.PRODUCT_CATEGORY_NOT_FOUND);
	    }
	    return productCategories.stream().map(ProductCategoriesDto::fromEntity).toList(); 
	}
	
	public List<ProductCategoriesDto> getChildCategoriesById(BigInteger id) {
		// log.info("getChildCategoriesById:"+ id.toString());
	    List<ProductCategories> productCategories = productCategoriesRepo.findByParentCategoryId(id);
	    // log.info("getChildCategoriesById:"+ productCategories.toString());
	    return productCategories.stream().map(ProductCategoriesDto::fromEntity).toList(); 
	}
	
	
	public ProductCategoriesDto getProductCategoryById(BigInteger id) {
	    return productCategoriesRepo.findById(id)
	        .map(ProductCategoriesDto::fromEntity)
	        .orElseThrow(() -> new NotFoundException(Constant.PRODUCT_CATEGORY_NOT_FOUND_WITH_ID + id));
	}
	
	
	public ProductCategoriesDto addProductCategory(ProductCategoriesDto productCategoriesDto) {
		ProductCategories productCategoryEnt= productCategoriesRepo.save(productCategoriesDto.toEntity());
		ProductCategoriesDto productCategory = productCategoriesRepo.findById(productCategoryEnt.getId())
		        .map(ProductCategoriesDto::fromEntity)
		        .orElseThrow(() -> new NotFoundException(Constant.PRODUCT_CATEGORY_NOT_FOUND_AFTER_SAVING));
		
		productCategorySink.tryEmitNext(productCategory);
		return productCategory;
	}

	
	
	
	
}
