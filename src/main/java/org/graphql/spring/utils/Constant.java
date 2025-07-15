package org.graphql.spring.utils;

import java.util.List;

public class Constant {
	public static final String DATE_FORMAT= "yyyy-MM-dd";
	public static final List<String> ADDRESS_TYPE= List.of("billing", "shipping", "both");
	
	
	public static final String INVALID_ADDRESS_TYPE="Invalid address type: ";
	public static final String INVALID_NUMBER="Invalid number: ";
	
	public static final String CUSTOMER_NOT_FOUND= "No customers found";
	public static final String CUSTOMER_NOT_FOUND_WITH= "No customers found with ";
	public static final String CUSTOMER_NOT_FOUND_WITH_ID="Customer not found with id: " ;
	public static final String CUSTOMER_NOT_FOUND_WITH_FIRST_NAME= "No customers found with first name: ";
	public static final String CUSTOMER_NOT_FOUND_WITH_LAST_NAME="No customers found with last name: ";
	public static final String CUSTOMER_NOT_FOUND_WITH_EMAIL="No customers found with email : ";
	public static final String CUSTOMER_NOT_FOUND_AFTER_SAVING="Customer not found after saving";
	public static final String CUSTOMER_ADDRESS_NOT_FOUND= "No customer addresses found";
	public static final String CUSTOMER_ADDRESS_NOT_FOUND_WITH_ID="Customer address not found with id: ";
	public static final String CUSTOMER_ADDRESS_NOT_FOUND_WITH_CITY="Customer address not found with city: ";
	public static final String CUSTOMER_ADDRESS_NOT_FOUND_WITH_POST_CODE="Customer address not found with post code: ";
	public static final String CUSTOMER_ADDRESS_NOT_FOUND_BY_COUNTRY="Customer address not found by Country: ";
	
	public static final String PRODUCT_CATEGORY_NOT_FOUND= "No product category found";
	public static final String PRODUCT_CATEGORY_NOT_FOUND_WITH_ID="Product category not found with id: " ;
	public static final String PRODUCT_CATEGORY_NOT_FOUND_AFTER_SAVING="Product category not found after saving";
	public static final String PRODUCT_CATEGORY_NAME_NOT_FOUND_WITH_ID="Product category name not found with id: " ;
	
	
}
