package org.graphql.spring.dto;

import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;

import org.graphql.spring.entity.Customer;

public class CustomerDto {
	private BigInteger customerId;
	private String firstName;
	private String lastName;
	private String email;
	private String phone;
	private LocalDate dateOfBirth;
	private LocalDateTime registrationDate;
	private Boolean isActive;
	private String taxId;
	private String notes;
	
	public CustomerDto(String firstName,String lastName, String email, String phone,LocalDate dateOfBirth) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.phone = phone;
		this.dateOfBirth = dateOfBirth;
		this.isActive= true;
		this.notes="Regular customer";
		this.registrationDate= LocalDateTime.now();
	}
	
	public CustomerDto(BigInteger customerId, String firstName, String lastName, String email, String phone,
			LocalDate dateOfBirth, LocalDateTime registrationDate, Boolean isActive, String taxId, String notes) {
		super();
		this.customerId = customerId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.phone = phone;
		this.dateOfBirth = dateOfBirth;
		this.registrationDate = registrationDate;
		this.isActive = isActive;
		this.taxId = taxId;
		this.notes = notes;
	}
	
	public static CustomerDto fromEntity(Customer c) {
		return new CustomerDto(
    		c.getId(),
            c.getFirstName(),
            c.getLastName(),
            c.getEmail(),
            c.getPhone(),
            c.getDateOfBirth(),
            c.getRegistrationDate(),
            c.getIsActive(),
            c.getTaxId(),
            c.getNotes()
        );
    }
	
	public Customer toEntity() {
        Customer c = new Customer();
        c.setId(customerId);
        c.setFirstName(firstName);
        c.setLastName(lastName);
        c.setEmail(email);
        c.setPhone(phone);
        c.setDateOfBirth(dateOfBirth);
        c.setRegistrationDate(registrationDate);
        c.setIsActive(isActive);
        c.setTaxId(taxId);
        c.setNotes(notes);
        return c;
    }
	
	public BigInteger getCustomerId() {
		return customerId;
	}
	public void setCustomerId(BigInteger customerId) {
		this.customerId = customerId;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public LocalDate getDateOfBirth() {
		return dateOfBirth;
	}
	public void setDateOfBirth(LocalDate dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}
	public LocalDateTime getRegistrationDate() {
		return registrationDate;
	}
	public void setRegistrationDate(LocalDateTime registrationDate) {
		this.registrationDate = registrationDate;
	}
	public Boolean getIsActive() {
		return isActive;
	}
	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}
	public String getTaxId() {
		return taxId;
	}
	public void setTaxId(String taxId) {
		this.taxId = taxId;
	}
	public String getNotes() {
		return notes;
	}
	public void setNotes(String notes) {
		this.notes = notes;
	}
	
	
}
