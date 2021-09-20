package com.existingeevee.inventoryreload.utils;

public class StringUtils {

	
	public static String reverseString(String string) {
        byte[] strAsByteArray = string.getBytes();
        
        byte[] result = new byte[strAsByteArray.length];
 
        // Store result in reverse order into the
        // result byte[]
        for (int i = 0; i < strAsByteArray.length; i++)
            result[i] = strAsByteArray[strAsByteArray.length - i - 1];
        
        return new String(result);
	}
	
}
