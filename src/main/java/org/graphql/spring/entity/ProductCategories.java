package org.graphql.spring.entity;

import java.math.BigInteger;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "product_categories")
public class ProductCategories {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
	private BigInteger id;
	
	@Column(name = "parent_category_id")
	private BigInteger parentCategoryId;
	
	@Column(name = "name", nullable = false, length = 50)
    private String name;
	
	@Column(name = "description", columnDefinition = "TEXT")
    private String description;

	public BigInteger getId() {
		return id;
	}

	public void setId(BigInteger id) {
		this.id = id;
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

	@Override
	public String toString() {
		return "ProductCategories [id=" + id + ", parentCategoryId=" + parentCategoryId + ", name=" + name
				+ ", description=" + description + "]";
	}
	
	
	
}
