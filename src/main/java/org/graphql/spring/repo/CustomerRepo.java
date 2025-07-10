package org.graphql.spring.repo;

import java.math.BigInteger;
import java.util.List;

import org.graphql.spring.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepo extends JpaRepository<Customer, BigInteger> {
	List<Customer> findByFirstName(String firstName);
    List<Customer> findByLastName(String lastName);
    List<Customer> findByFirstNameAndLastName(String firstName, String lastName);
    List<Customer> findByEmail(String email);
}
