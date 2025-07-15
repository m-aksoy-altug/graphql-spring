package org.graphql.spring.dto;

import java.math.BigInteger;
import java.util.List;

import org.graphql.spring.entity.ProductCategories;

public class ProductCategoriesDto {
	private BigInteger categoryId;
	private BigInteger parentCategoryId;
    private String name;
    private String description;
    private List<ProductCategoriesDto> childCategories;
    
    public ProductCategoriesDto() {}
    
    public ProductCategoriesDto(BigInteger categoryId, BigInteger parentCategoryId, String name, String description) {
		super();
		this.categoryId = categoryId;
		this.parentCategoryId = parentCategoryId;
		this.name = name;
		this.description = description;
	}
    
    public static ProductCategoriesDto fromEntity(ProductCategories prodC) {
    	return new ProductCategoriesDto(prodC.getId(),
    									prodC.getParentCategoryId(),
    									prodC.getName(),
    									prodC.getDescription()
    			);
    }
    
    public ProductCategories toEntity() {
    	ProductCategories entity = new ProductCategories();
    	entity.setId(categoryId);
    	entity.setParentCategoryId(parentCategoryId);
    	entity.setDescription(description);
    	entity.setName(name);
    	return entity;
    }
    
	public BigInteger getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(BigInteger categoryId) {
		this.categoryId = categoryId;
	}
	public BigInteger getParentCategoryId() {
		return parentCategoryId;
	}
	public void setParentCategoryId(BigInteger parentCategoryId) {
		this.parentCategoryId = parentCategoryId;
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

	public List<ProductCategoriesDto> getChildCategories() {
		return childCategories;
	}

	public void setChildCategories(List<ProductCategoriesDto> childCategories) {
		this.childCategories = childCategories;
	}

	@Override
	public String toString() {
		return "ProductCategoriesDto [categoryId=" + categoryId + ", parentCategoryId=" + parentCategoryId + ", name="
				+ name + ", description=" + description + ", childCategories=" + childCategories + "]";
	}
	
    
}
