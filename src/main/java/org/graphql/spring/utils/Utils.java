package org.graphql.spring.utils;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


public class Utils {
	public static LocalDate convertDateFromStr(String dateStr) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(Constant.DATE_FORMAT);
		return  LocalDate.parse(dateStr, formatter);
	}
	
	public static BigInteger convertStrForBigInt(String id) {
		BigInteger bigInt=null; 
		try {
			bigInt= new BigInteger(id);
		}catch(NumberFormatException e) {
			throw new IllegalArgumentException(Constant.INVALID_INTEGER_NUMBER);
		}
		return  bigInt;
	}
	
	public static BigDecimal convertStrForBigDec(String id) {
		BigDecimal bigDec=null; 
		try {
			bigDec= new BigDecimal(id);
		}catch(NumberFormatException e) {
			throw new IllegalArgumentException(Constant.INVALID_DECIMAL_NUMBER);
		}
		return  bigDec;
	}
}
