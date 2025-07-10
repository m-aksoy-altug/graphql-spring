package org.graphql.spring.utils;

import java.math.BigInteger;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


public class Utils {
	public static LocalDate convertDateFromStr(String dateStr) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(Constant.DATE_FORMAT);
		return  LocalDate.parse(dateStr, formatter);
	}
	
	public static BigInteger convertStrFromBigInt(String id) {
		BigInteger bigInt=null; 
		try {
			bigInt= new BigInteger(id);
		}catch(NumberFormatException e) {
			throw new IllegalArgumentException(Constant.INVALID_NUMBER);
		}
		return  bigInt;
	}
	
	
}
