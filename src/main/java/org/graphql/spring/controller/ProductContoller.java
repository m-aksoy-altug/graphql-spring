package org.graphql.spring.controller;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

import org.graphql.spring.dto.CustomerAddressesDto;
import org.graphql.spring.dto.CustomerDto;
import org.graphql.spring.dto.ProductCategoriesDto;
import org.graphql.spring.dto.ProductDto;
import org.graphql.spring.exception.NotFoundException;
import org.graphql.spring.service.ProductService;
import org.graphql.spring.utils.Constant;
import org.graphql.spring.utils.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.graphql.data.method.annotation.SubscriptionMapping;
import org.springframework.stereotype.Controller;

import reactor.core.publisher.Flux;

@Controller
public class ProductContoller {

	private static final Logger log = LoggerFactory.getLogger(ProductContoller.class);

	@Autowired
	private ProductService productService;

	@SubscriptionMapping
	public Flux<ProductCategoriesDto> productCategoriesRegistered() {
		return productService.getProductCategoriesStream();
	}
	
	
	@SubscriptionMapping
	public Flux<ProductDto> productRegistered() {
		return productService.getProductStream();
	}
	
	@QueryMapping
	public List<ProductCategoriesDto> allProductCategories() {
		return productService.getAllProductCategories();
	}

	@SchemaMapping(typeName = "ProductCategoriesDto", field = "childCategories")
	public List<ProductCategoriesDto> childCategories(ProductCategoriesDto productCategoriesDto) {
		return productService.getChildCategoriesById(productCategoriesDto.getCategoryId());
	}

	@QueryMapping
	public ProductCategoriesDto productCategoryById(@Argument String id) {
		return productService.getProductCategoryById(Utils.convertStrForBigInt(id));
	}

	@MutationMapping
	public ProductCategoriesDto addProductCategory(@Argument String parentCategoryId, @Argument String name,
			@Argument String description) {
		ProductCategoriesDto productCategoriesDto = new ProductCategoriesDto();
		if (parentCategoryId == null || parentCategoryId.isBlank()) {
			// log.info("New parent category is creating now");
			productCategoriesDto.setCategoryId(null);
			productCategoriesDto.setParentCategoryId(null);
		} else {
			ProductCategoriesDto parent = productService
					.getProductCategoryById(Utils.convertStrForBigInt(parentCategoryId));
			if (parent.getName() == null || parent.getName().isBlank()) {
				throw new NotFoundException(Constant.PRODUCT_CATEGORY_NAME_NOT_FOUND_WITH_ID + parent.getCategoryId());
			}
			// log.info("Child category is creating now with parent
			// category"+parent.toString());
			productCategoriesDto.setParentCategoryId(parent.getCategoryId());
		}

		productCategoriesDto.setName(name);
		productCategoriesDto.setDescription(description);

		log.info("addProductCategory:" + productCategoriesDto.toString());
		return productService.addProductCategory(productCategoriesDto);
	}

	@QueryMapping
	public List<ProductDto> allProducts() {
		return productService.getAllProducts();
	}

	@SchemaMapping(typeName = "ProductDto", field = "category")
	public ProductCategoriesDto product(ProductDto productDto) {
		return productService.getProductCategoryById(productDto.getCategoryId());
	}

	@MutationMapping
	public ProductDto addProduct(@Argument String sku, @Argument String name, @Argument String description,
			@Argument String categoryId, @Argument String unitPrice, @Argument String weight,
			@Argument String dimensions) {
		ProductCategoriesDto productCategory = productService
				.getProductCategoryById(Utils.convertStrForBigInt(categoryId));

		BigDecimal bigDecUnitPrice = Utils.convertStrForBigDec(unitPrice);
		BigDecimal bigDecWeight = Utils.convertStrForBigDec(weight);

		ProductDto productDto = new ProductDto(null, sku, name, description, productCategory.getCategoryId(),
				bigDecUnitPrice, bigDecWeight, dimensions);
		return productService.addProduct(productDto,productCategory);

	}

}
