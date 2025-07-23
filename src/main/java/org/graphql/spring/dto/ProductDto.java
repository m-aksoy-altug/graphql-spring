package org.graphql.spring.dto;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;

import org.graphql.spring.entity.Product;

public class ProductDto {
	private BigInteger productId;
	private String sku;
	private String name;
	private String description;
	private BigInteger categoryId;
	private BigDecimal unitPrice;
	private BigDecimal weight;
	private String dimensions;
	private Boolean isActive;  // default true
	private LocalDateTime createdAt; // default current date time 
	private LocalDateTime updatedAt;
	
	private ProductDto() {}
	
	public ProductDto(BigInteger id, String sku, String name, String description, BigInteger categoryId,
			BigDecimal unitPrice, BigDecimal weight, String dimensions) {
		super();
		this.productId = id;
		this.sku = sku;
		this.name = name;
		this.description = description;
		this.categoryId = categoryId;
		this.unitPrice = unitPrice;
		this.weight = weight;
		this.dimensions = dimensions;
		this.isActive = true;
		this.createdAt =LocalDateTime.now();
	}

	public static ProductDto fromEntity(Product product) {
		ProductDto productDto = new ProductDto();
		productDto.setProductId(product.getId());
		productDto.setCategoryId(product.getCategoryId());
		productDto.setCreatedAt(product.getCreatedAt());
		productDto.setDescription(product.getDescription());
		productDto.setDimensions(product.getDimensions());
		productDto.setIsActive(product.getIsActive());
		productDto.setName(product.getName());
		productDto.setSku(product.getSku());
		productDto.setUnitPrice(product.getUnitPrice());
		productDto.setUpdatedAt(product.getUpdatedAt());
		productDto.setWeight(product.getWeight());
		return productDto;
	}
	
	public Product toEntity() {
		Product productEnt = new Product();
		productEnt.setId(productId);
		productEnt.setSku(sku);
		productEnt.setName(name);
		productEnt.setDescription(description);
		productEnt.setCategoryId(categoryId);
		productEnt.setUnitPrice(unitPrice);
		productEnt.setWeight(weight);
		productEnt.setDimensions(dimensions);
		productEnt.setIsActive(isActive);
		productEnt.setCreatedAt(createdAt);
		productEnt.setUpdatedAt(updatedAt);
		return productEnt;
	}
	
	public BigInteger getProductId() {
		return productId;
	}

	public void setProductId(BigInteger productId) {
		this.productId = productId;
	}

	public String getSku() {
		return sku;
	}
	public void setSku(String sku) {
		this.sku = sku;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public BigInteger getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(BigInteger categoryId) {
		this.categoryId = categoryId;
	}
	public BigDecimal getUnitPrice() {
		return unitPrice;
	}
	public void setUnitPrice(BigDecimal unitPrice) {
		this.unitPrice = unitPrice;
	}
	public BigDecimal getWeight() {
		return weight;
	}
	public void setWeight(BigDecimal weight) {
		this.weight = weight;
	}
	public String getDimensions() {
		return dimensions;
	}
	public void setDimensions(String dimensions) {
		this.dimensions = dimensions;
	}
	public Boolean getIsActive() {
		return isActive;
	}
	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}
	public LocalDateTime getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}
	public LocalDateTime getUpdatedAt() {
		return updatedAt;
	}
	public void setUpdatedAt(LocalDateTime updatedAt) {
		this.updatedAt = updatedAt;
	}

	@Override
	public String toString() {
		return "ProductDto [productId=" + productId + ", sku=" + sku + ", name=" + name + ", description=" + description
				+ ", categoryId=" + categoryId + ", unitPrice=" + unitPrice + ", weight=" + weight + ", dimensions="
				+ dimensions + ", isActive=" + isActive + ", createdAt=" + createdAt + ", updatedAt=" + updatedAt + "]";
	}

}
