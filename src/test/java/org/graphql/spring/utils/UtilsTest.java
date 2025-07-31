package org.graphql.spring.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.junit.jupiter.MockitoExtension;


@ExtendWith(MockitoExtension.class)
public class UtilsTest {
	
    @Test
    void validate_baseSku() {
    	String ele= Utils.generateBaseSku("Electronics");
    	assertEquals("ELE", ele);
    	String fur= Utils.generateBaseSku("Furniture");
    	assertEquals("FUR", fur);
    	String app= Utils.generateBaseSku("Appliances");
    	assertEquals("APP", app);
    	String clo= Utils.generateBaseSku("Clothing");
    	assertEquals("CLO", clo);
    	String com= Utils.generateBaseSku("Computers");
    	assertEquals("COM", com);
    	String sma= Utils.generateBaseSku("Smartphones");
    	assertEquals("SMA", sma);
    	String ofc= Utils.generateBaseSku("Office Chairs");
    	assertEquals("OFC", ofc);
    	String ref= Utils.generateBaseSku("Refrigerators");
    	assertEquals("REF", ref);
    	String mec= Utils.generateBaseSku("Men''s Clothing");
    	assertEquals("MEC", mec);
    	String woc= Utils.generateBaseSku("Women''s Clothing");
    	assertEquals("WOC", woc);
    }
    		
}
