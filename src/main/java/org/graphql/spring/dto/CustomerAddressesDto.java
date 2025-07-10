package org.graphql.spring.dto;

import java.math.BigInteger;
import java.time.LocalDateTime;

import org.graphql.spring.entity.CustomerAddresses;

public class CustomerAddressesDto {
	private BigInteger addressId;
    private BigInteger customerId;
    private String addressType;
    private String streetAddress1;
    private String streetAddress2;
    private String city;
    private String stateProvince;
    private String postalCode;
    private String country;
    private Boolean isDefault;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    
    
    
	public CustomerAddressesDto(BigInteger customerId, String addressType, String streetAddress1, String streetAddress2,
			String city, String stateProvince, String postalCode, String country) {
		super();
		this.customerId = customerId;
		this.addressType = addressType;
		this.streetAddress1 = streetAddress1;
		this.streetAddress2 = streetAddress2; // can be null
		this.city = city;
		this.stateProvince = stateProvince;
		this.postalCode = postalCode;
		this.country = country;
		this.isDefault= false;
		this.createdAt= LocalDateTime.now();
		
	}
	
	public CustomerAddressesDto(BigInteger addressId, BigInteger customerId, String addressType, String streetAddress1,
			String streetAddress2, String city, String stateProvince, String postalCode, String country,
			Boolean isDefault, LocalDateTime createdAt, LocalDateTime updatedAt) {
		super();
		this.addressId = addressId;
		this.customerId = customerId;
		this.addressType = addressType;
		this.streetAddress1 = streetAddress1;
		this.streetAddress2 = streetAddress2;
		this.city = city;
		this.stateProvince = stateProvince;
		this.postalCode = postalCode;
		this.country = country;
		this.isDefault = isDefault;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
	}
	public static CustomerAddressesDto fromEntity(CustomerAddresses custAdd) {
		return new CustomerAddressesDto(
				custAdd.getId(),
				custAdd.getCustomerId(),
				custAdd.getAddressType(),
				custAdd.getStreetAddress1(),
				custAdd.getStreetAddress2(),
				custAdd.getCity(),
				custAdd.getStateProvince(),
				custAdd.getPostalCode(),
            	custAdd.getCountry(),
            	custAdd.getIsDefault(),
            	custAdd.getCreatedAt(),
            	custAdd.getUpdatedAt());
    }
	

	public CustomerAddresses toEntity() {
	    CustomerAddresses entity = new CustomerAddresses();
	    entity.setId(addressId);
	    entity.setCustomerId(customerId);
	    entity.setAddressType(addressType);
	    entity.setStreetAddress1(streetAddress1);
	    entity.setStreetAddress2(streetAddress2);
	    entity.setCity(city);
	    entity.setStateProvince(stateProvince);
	    entity.setPostalCode(postalCode);
	    entity.setCountry(country);
	    entity.setIsDefault(isDefault);
	    entity.setCreatedAt(createdAt);
	    entity.setUpdatedAt(updatedAt);
	    return entity;
	}

	public BigInteger getAddressId() {
		return addressId;
	}

	public void setAddressId(BigInteger addressId) {
		this.addressId = addressId;
	}

	public BigInteger getCustomerId() {
		return customerId;
	}

	public void setCustomerId(BigInteger customerId) {
		this.customerId = customerId;
	}

	public String getAddressType() {
		return addressType;
	}

	public void setAddressType(String addressType) {
		this.addressType = addressType;
	}

	public String getStreetAddress1() {
		return streetAddress1;
	}

	public void setStreetAddress1(String streetAddress1) {
		this.streetAddress1 = streetAddress1;
	}

	public String getStreetAddress2() {
		return streetAddress2;
	}

	public void setStreetAddress2(String streetAddress2) {
		this.streetAddress2 = streetAddress2;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getStateProvince() {
		return stateProvince;
	}

	public void setStateProvince(String stateProvince) {
		this.stateProvince = stateProvince;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public Boolean getIsDefault() {
		return isDefault;
	}

	public void setIsDefault(Boolean isDefault) {
		this.isDefault = isDefault;
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
	
	
}
