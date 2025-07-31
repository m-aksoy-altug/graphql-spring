package org.graphql.spring.repo;

import java.math.BigInteger;
import java.util.Optional;

import org.graphql.spring.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProductRepo extends JpaRepository<Product, BigInteger> {
	
	    @Query(value = """
	    		SELECT * FROM products 
	    		WHERE sku LIKE CONCAT(:skuBase, '-%')
	    		ORDER BY CAST(SUBSTRING(sku, POSITION('-' IN sku) + 1) AS UNSIGNED) DESC  LIMIT 1 
	    		""", nativeQuery = true)
        Optional<Product> findTopBySkuBaseOrderBySuffixDesc(@Param("skuBase") String skuBase);
	    Optional<Product> findBySku(String sku);
	    
	    
}
