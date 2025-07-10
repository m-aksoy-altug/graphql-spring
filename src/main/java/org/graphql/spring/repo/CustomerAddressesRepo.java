package org.graphql.spring.repo;

import java.math.BigInteger;
import java.util.List;

import org.graphql.spring.entity.CustomerAddresses;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerAddressesRepo  extends JpaRepository<CustomerAddresses, BigInteger> {
	List<CustomerAddresses> findByCustomerId(BigInteger customerId);
	List<CustomerAddresses> findByCity(String city);
	List<CustomerAddresses> findByPostalCode(String postalCode);
	List<CustomerAddresses> findByCountry(String country);
	
}
