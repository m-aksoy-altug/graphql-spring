package org.graphql.spring.controller;

import java.time.LocalDate;
import java.util.List;

import org.graphql.spring.dto.CustomerAddressesDto;
import org.graphql.spring.dto.CustomerDto;
import org.graphql.spring.service.CustomerService;
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
public class CustomerController {

	private static final Logger log = LoggerFactory.getLogger(CustomerController.class);

	@Autowired
	private CustomerService customerService;
	
	@SubscriptionMapping
    public  Flux<CustomerDto> customerRegistered() {
        return customerService.getCustomerStream();
    }
	
	@MutationMapping
	public CustomerDto addCustomer(@Argument String firstName, @Argument String lastName, 
									@Argument String email,@Argument String phone, 
									@Argument String dateOfBirth) {

		LocalDate date = Utils.convertDateFromStr(dateOfBirth);
		CustomerDto newCustomer = new CustomerDto(firstName, lastName, email, phone, date);

		log.info("addCustomer:" + newCustomer.toString());
		return customerService.addCustomer(newCustomer);
	}
	
	@SchemaMapping(typeName = "CustomerDto", field = "addresses")
	public List<CustomerAddressesDto> getAddresses(CustomerDto customerDto) {
	    return customerService.customerAddressByCustomerId(customerDto.getCustomerId());
	
	}
	
	@MutationMapping
	public CustomerDto addCustomerAddressByEmail(@Argument String email, 
												@Argument String addressType, 
												@Argument String streetAddress1,
												@Argument String streetAddress2, 
												@Argument String city,
												@Argument String stateProvince,
												@Argument String postalCode,
												@Argument String country) {
		CustomerAddressesDto customerAddressesDto = 
				new CustomerAddressesDto(null, addressType, streetAddress1, streetAddress2,
						city,stateProvince,postalCode,country);
		log.info("addCustomerAddressByEmail:" + email +  customerAddressesDto);
		return customerService.addCustomerAddressByEmail(email,customerAddressesDto);
	}	
		
	@QueryMapping
	public List<CustomerDto> allCustomers() {
		// TODO: Paging
		return customerService.getAllCustomers();
	}
	
	@QueryMapping
	public CustomerDto customerById(@Argument String id) {
		return customerService.getCustomerById(Utils.convertStrForBigInt(id));
	}

	@QueryMapping
	public List<CustomerDto> customerByFirstName(@Argument String firstName) {
		// log.info("CustomerController:"+firstName);
		return customerService.getCustomerByFirstName(firstName);
	}

	@QueryMapping
	public List<CustomerDto> customerByLastName(@Argument String lastName) {
		// log.info("customerByLastName:"+lastName);
		return customerService.getCustomerByLastName(lastName);
	}

	@QueryMapping
	public List<CustomerDto> customerByFirstAndLastName(@Argument String firstName, @Argument String lastName) {
		// log.info("customerByFirstAndLastName:"+firstName + lastName);
		return customerService.getCustomerByFirstAndLastName(firstName, lastName);
	}
	
	
	@QueryMapping
	public CustomerDto customerByEmail(@Argument String email) {
		// log.info("customerByEmail:"+email);
		return customerService.getCustomerByEmail(email);
	}
	
	@MutationMapping
	public CustomerDto deleteCustomerByEmail(@Argument String email) {
		return customerService.deleteCustomerByEmail(email);
	}
	
	@QueryMapping
	public List<CustomerAddressesDto> customerAddressByCustomerId(@Argument String id) {
		log.info("customerAddressByCustomerId:"+id);
		return customerService.customerAddressByCustomerId(Utils.convertStrForBigInt(id));
	}
	
	@QueryMapping
	public List<CustomerAddressesDto> customerAddressByCity(@Argument String city) {
		log.info("customerAddressByCity:"+city);
		return customerService.customerAddressByCity(city);
	}
	
	@QueryMapping
	public List<CustomerAddressesDto> customerAddressByPostCode(@Argument String postCode) {
		log.info("customerAddressByPsotCode:"+postCode);
		return customerService.customerAddressByPostCode(postCode);
	}
	
	@QueryMapping
	public List<CustomerAddressesDto> customerAddressByCountry(@Argument String country) {
		log.info("customerAddressByCountry:"+country);
		return customerService.customerAddressByCountry(country);
	}
	
	@QueryMapping
	public List<CustomerAddressesDto> allAddresses() {
		// TODO: Paging
		return customerService.allAddresses();
	}
	
	
}
