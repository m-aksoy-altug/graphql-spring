package org.graphql.spring.repo;

import java.math.BigInteger;

import org.graphql.spring.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepo extends JpaRepository<Product, BigInteger> {

}
