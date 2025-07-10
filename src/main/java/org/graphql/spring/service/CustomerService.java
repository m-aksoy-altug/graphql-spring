package org.graphql.spring.service;

import java.lang.invoke.ConstantBootstraps;
import java.math.BigInteger;
import java.util.List;

import org.graphql.spring.controller.CustomerController;
import org.graphql.spring.dto.CustomerAddressesDto;
import org.graphql.spring.dto.CustomerDto;
import org.graphql.spring.entity.Book;
import org.graphql.spring.entity.Customer;
import org.graphql.spring.entity.CustomerAddresses;
import org.graphql.spring.exception.NotFoundException;
import org.graphql.spring.repo.CustomerAddressesRepo;
import org.graphql.spring.repo.CustomerRepo;
import org.graphql.spring.utils.Constant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

@Service
public class CustomerService {
	
	private static final Logger log= LoggerFactory.getLogger(CustomerService.class);

	// Sink type must match with return Flux<Object>
	private final Sinks.Many<CustomerDto> customerSink = Sinks.many().multicast().onBackpressureBuffer();
	
	@Autowired
	private  CustomerRepo customersRepo;
	
	@Autowired
	private  CustomerAddressesRepo customerAddressesRepo;
	
	
	public Flux<CustomerDto> getCustomerStream() {
		return customerSink.asFlux();
	}
	
	public CustomerDto addCustomer(CustomerDto newCustomer) {
		Customer custEnt= customersRepo.save(newCustomer.toEntity());
		CustomerDto savedDto = customersRepo.findById(custEnt.getId())
		        .map(CustomerDto::fromEntity)
		        .orElseThrow(() -> new NotFoundException(Constant.CUSTOMER_NOT_FOUND_AFTER_SAVING));
		
		customerSink.tryEmitNext(savedDto);
		return savedDto;
	}
	
	public CustomerDto getCustomerById(BigInteger id) {
	    return customersRepo.findById(id)
	        .map(CustomerDto::fromEntity)
	        .orElseThrow(() -> new NotFoundException(Constant.CUSTOMER_NOT_FOUND_WITH_ID + id));
	}
	
	public List<CustomerDto> getCustomerByFirstName(String firstName) {
	    List<Customer> customers = customersRepo.findByFirstName(firstName);
	    //customers.stream().forEach(x->log.info("getCustomerByFirstName:"+x.toString()));
		if (customers.isEmpty()) {
	        throw new NotFoundException(Constant.CUSTOMER_NOT_FOUND_WITH_FIRST_NAME+ firstName);
	    }
	    return customers.stream().map(CustomerDto::fromEntity).toList(); 
	}
	
	public List<CustomerDto> getCustomerByLastName(String lastName) {
	    List<Customer> customers = customersRepo.findByLastName(lastName);
	    // customers.stream().forEach(x->log.info("getCustomerByLastName:"+x.toString()));
		if (customers.isEmpty()) {
	        throw new NotFoundException(Constant.CUSTOMER_NOT_FOUND_WITH_LAST_NAME+ lastName);
	    }
	    return customers.stream().map(CustomerDto::fromEntity).toList(); 
	}
	
	public List<CustomerDto> getCustomerByFirstAndLastName(String firstName,String lastName) {
	    List<Customer> customers = customersRepo.findByFirstNameAndLastName(firstName, lastName);
		// customers.stream().forEach(x->log.info("getCustomerByFirstAndLastName:"+x.toString()));
	    
		if (customers.isEmpty()) {
	        throw new NotFoundException(Constant.CUSTOMER_NOT_FOUND_WITH+lastName +" "+ lastName);
	    }
	    return customers.stream().map(CustomerDto::fromEntity).toList(); 
	}
	
	public List<CustomerDto> getCustomerByEmail(String email) {
	    List<Customer> customers = customersRepo.findByEmail(email);
	    // customers.stream().forEach(x->log.info("getCustomerByEmail:"+x.toString()));
	    
		if (customers.isEmpty()) {
	        throw new NotFoundException(Constant.CUSTOMER_NOT_FOUND_WITH_EMAIL + email);
	    }
	    return customers.stream().map(CustomerDto::fromEntity).toList(); 
	}
	
	public List<CustomerDto> getAllCustomers() {
	    List<Customer> customers = customersRepo.findAll();
	    if (customers.isEmpty()) {
	        throw new NotFoundException(Constant.CUSTOMER_NOT_FOUND);
	    }
	    return customers.stream().map(CustomerDto::fromEntity).toList(); 
	}
	
	
	public List<CustomerAddressesDto> customerAddressByCustomerId(BigInteger id) {
	    List<CustomerAddresses> addressEntity= customerAddressesRepo.findByCustomerId(id);
	    if (addressEntity.isEmpty()) {
	        throw  new NotFoundException(Constant.CUSTOMER_ADDRESS_NOT_FOUND_WITH_ID + id);
	    }
	    return addressEntity.stream().map(CustomerAddressesDto::fromEntity).toList();
	}
	
	public List<CustomerAddressesDto> customerAddressByCity(String city) {
	    List<CustomerAddresses> addressEntity= customerAddressesRepo.findByCity(city);
	    if (addressEntity.isEmpty()) {
	        throw  new NotFoundException(Constant.CUSTOMER_ADDRESS_NOT_FOUND_WITH_CITY + city);
	    }
	    return addressEntity.stream().map(CustomerAddressesDto::fromEntity).toList();
	}
	
	public List<CustomerAddressesDto> customerAddressByPostCode(String postCode) {
	    List<CustomerAddresses> addressEntity= customerAddressesRepo.findByPostalCode(postCode);
	    if (addressEntity.isEmpty()) {
	        throw  new NotFoundException(Constant.CUSTOMER_ADDRESS_NOT_FOUND_WITH_POST_CODE + postCode);
	    }
	    return addressEntity.stream().map(CustomerAddressesDto::fromEntity).toList();
	}
	
	public List<CustomerAddressesDto> customerAddressByCountry(String country) {
	    List<CustomerAddresses> addressEntity= customerAddressesRepo.findByCountry(country);
	    if (addressEntity.isEmpty()) {
	        throw  new NotFoundException(Constant.CUSTOMER_ADDRESS_NOT_FOUND_BY_COUNTRY + country);
	    }
	    return addressEntity.stream().map(CustomerAddressesDto::fromEntity).toList();
	}
	
	
	public List<CustomerAddressesDto> allAddresses() {
	    List<CustomerAddresses> addressEntity = customerAddressesRepo.findAll();
	    if (addressEntity.isEmpty()) {
	        throw new NotFoundException(Constant.CUSTOMER_ADDRESS_NOT_FOUND);
	    }
	    return addressEntity.stream().map(CustomerAddressesDto::fromEntity).toList(); 
	}
	
	
	
}
