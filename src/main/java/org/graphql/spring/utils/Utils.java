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
	
	 public static boolean skuValidFormat(String sku) {
	        if (sku == null) {
	            return false;
	        }
	        return sku.matches("^[A-Z]{3}-\\d{6}$");
	 }
	 
	public static String generateBaseSku(String categoryName) {
	    String cleaned = categoryName.replaceAll("[^a-zA-Z ]", "").trim();
	    String[] words = cleaned.split("\\s+");
	    StringBuilder prefix = new StringBuilder(3);
	    
	    if (words.length == 1) {
	        // One word: take first 3 characters
	        prefix.append(words[0].length() >= 3 ? words[0].substring(0, 3) : words[0]);
	    } else if (words.length == 2) {
	        // Two words: 2 chars from first word, 1 char from second word
	        prefix.append(words[0].length() >= 2 ? words[0].substring(0, 2) : words[0]);
	        if (words[1].length() >= 1) {
	            prefix.append(words[1].charAt(0));
	        }
	    } else if (words.length >= 3) {
	        // Three+ words: 1 char from each of first 3 words
	        for (int i = 0; i < Math.min(3, words.length); i++) {
	            if (words[i].length() >= 1) {
	                prefix.append(words[i].charAt(0));
	            }
	        }
	    }
	    
	    String skuPrefix = prefix.toString().toUpperCase();
	    // Pad with X if less than 3 chars
	    if (skuPrefix.length() < 3) {
	        skuPrefix = String.format("%-3s", skuPrefix).replace(' ', 'X');
	    }
	    return skuPrefix.length() > 3 ? skuPrefix.substring(0, 3) : skuPrefix;
	}
	
	
		
}
