package org.graphql.spring.entity;

import java.lang.invoke.ConstantBootstraps;
import java.math.BigInteger;
import java.time.LocalDateTime;

import org.graphql.spring.utils.Constant;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "customer_addresses")
public class CustomerAddresses {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "address_id")
	private BigInteger id;
	
//	@ManyToOne(optional = false, fetch = FetchType.LAZY)
//    @JoinColumn(name = "customer_id", nullable = false)
//    private Customer customer;
	
	@Column(name = "customer_id", nullable = false, length = 20)
	private BigInteger customerId;
	
    @Column(name = "address_type", nullable = false, length = 20)
    private String addressType;

    @Column(name = "street_address1", nullable = false, length = 200)
    private String streetAddress1;

    @Column(name = "street_address2", length = 100)
    private String streetAddress2;

    @Column(name = "city", nullable = false, length = 50)
    private String city;

    @Column(name = "state_province", nullable = false, length = 50)
    private String stateProvince;

    @Column(name = "postal_code", nullable = false, length = 20)
    private String postalCode;

    @Column(name = "country", nullable = false, length = 50)
    private String country;

    @Column(name = "is_default", nullable = false)
    private Boolean isDefault;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt; 

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
   
	public BigInteger getId() {
		return id;
	}

	public void setId(BigInteger id) {
		this.id = id;
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
		if (!Constant.ADDRESS_TYPE.contains(addressType.toLowerCase())) {
		    throw new IllegalArgumentException(Constant.INVALID_ADDRESS_TYPE+ addressType);
		}
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

	@Override
	public String toString() {
		return "CustomerAddresses [id=" + id + ", customerId=" + customerId + ", addressType=" + addressType
				+ ", streetAddress1=" + streetAddress1 + ", streetAddress2=" + streetAddress2 + ", city=" + city
				+ ", stateProvince=" + stateProvince + ", postalCode=" + postalCode + ", country=" + country
				+ ", isDefault=" + isDefault + ", createdAt=" + createdAt + ", updatedAt=" + updatedAt + "]";
	}

   
}
