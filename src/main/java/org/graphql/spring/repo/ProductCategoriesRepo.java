package org.graphql.spring.repo;

import java.math.BigInteger;
import java.util.List;

import org.graphql.spring.entity.ProductCategories;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductCategoriesRepo extends JpaRepository<ProductCategories, BigInteger> {
	List<ProductCategories> findByParentCategoryId(BigInteger id);
}
