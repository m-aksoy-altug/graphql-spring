package org.graphql.spring.service;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

import org.graphql.spring.dto.ProductCategoriesDto;
import org.graphql.spring.dto.ProductDto;
import org.graphql.spring.entity.Product;
import org.graphql.spring.entity.ProductCategories;
import org.graphql.spring.exception.NotFoundException;
import org.graphql.spring.repo.ProductCategoriesRepo;
import org.graphql.spring.repo.ProductRepo;
import org.graphql.spring.utils.Constant;
import org.graphql.spring.utils.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

@Service
public class ProductService {
	private static final Logger log= LoggerFactory.getLogger(ProductService.class);
	
	private final Sinks.Many<ProductCategoriesDto> productCategorySink = Sinks.many().multicast().onBackpressureBuffer();
	
	private final Sinks.Many<ProductDto> productSink = Sinks.many().multicast().onBackpressureBuffer();
	
	@Autowired
	private ProductCategoriesRepo productCategoriesRepo;
	
	@Autowired
	private ProductRepo productRepo;
	
	public Flux<ProductCategoriesDto> getProductCategoriesStream() {
		return productCategorySink.asFlux()
				 .onErrorResume(e -> {
		                log.error("Product Category Subscription error", e);
		                return Flux.empty();
		            });
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
	
	@Transactional
	public ProductCategoriesDto addProductCategory(ProductCategoriesDto productCategoriesDto) {
		ProductCategories productCategoryEnt= productCategoriesRepo.save(productCategoriesDto.toEntity());
		ProductCategoriesDto productCategory = productCategoriesRepo.findById(productCategoryEnt.getId())
		        .map(ProductCategoriesDto::fromEntity)
		        .orElseThrow(() -> new NotFoundException(Constant.PRODUCT_CATEGORY_NOT_FOUND_AFTER_SAVING));
		
		productCategorySink.tryEmitNext(productCategory);
		return productCategory;
	}

	public List<ProductDto> getAllProducts() {
		List<Product> products = productRepo.findAll();
	    if (products.isEmpty()) {
	        throw new NotFoundException(Constant.PRODUCT_NOT_FOUND);
	    }
	    return products.stream().map(ProductDto::fromEntity).toList();
	}

	
	@Transactional
	public ProductDto addProduct(ProductDto productDto, ProductCategoriesDto productCategory) {
	   skuCheck(productDto,productCategory);
	   Product product = productRepo.save(productDto.toEntity());
	   ProductDto productDtoSaved= productRepo.findById(product.getId())
					   .map(ProductDto::fromEntity)
				       .orElseThrow(() -> new NotFoundException(Constant.PRODUCT_NOT_FOUND_AFTER_SAVING));
	   productSink.tryEmitNext(productDtoSaved);
		return productDtoSaved;
	}
	
	public Flux<ProductDto> getProductStream() {
		return productSink.asFlux()
				 .onErrorResume(e -> {
		                log.error("Product Subscription error", e);
		                return Flux.empty();
		            });
	}
	
	private void skuCheck(ProductDto productDto, ProductCategoriesDto productCategory) {
		String givenSku= productDto.getSku();
		String baseSku= Utils.generateBaseSku(productCategory.getName());
		 log.info("Given sku: "+givenSku+ ", base sku: "+ baseSku);
		if(givenSku==null || givenSku.isBlank()) {
			skuCreation(productDto,baseSku);
		}
		else {
			if(!Utils.skuValidFormat(givenSku) || !baseSku.equals(givenSku.substring(0, 2))) {
				throw new IllegalArgumentException(Constant.GIVEN_SKU_IS_INVALID_FORMAT);
			}
			skuCreation(productDto,baseSku);
		}
	}
	
	private void skuCreation(ProductDto productDto, String baseSku) {
		Optional<Product> highestSkOpt= productRepo.findTopBySkuBaseOrderBySuffixDesc(baseSku);
		if(highestSkOpt.isPresent()) {
			String highestSku= highestSkOpt.get().getSku();
			String charPart = highestSku.substring(0, highestSku.indexOf('-'));
		    String numericPart = highestSku.substring(highestSku.indexOf('-') + 1);
			Integer numeric= Integer.parseInt(numericPart);
			log.info("highestSku"+ highestSku);
			productDto.setSku( String.format("%s-%06d", charPart, numeric + 1));
		}else {
			log.info("Sku with new base sku");
			productDto.setSku( String.format("%s-%06d", baseSku, 000001));
		}
	}
	
	
	
}
