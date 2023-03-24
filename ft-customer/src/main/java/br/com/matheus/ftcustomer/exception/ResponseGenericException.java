package br.com.matheus.ftcustomer.exception;

import java.util.HashMap;


public class ResponseGenericException<T> {
    public static <T> Object response(T object) {
    	
        return new HashMap<String, Object>() {
            private static final long serialVersionUID = 1L;
            {
                put("result", object);
            }
        };
    }

	public static <T> Object response(T object, int i) {
		return new HashMap<String, Object>() {
            private static final long serialVersionUID = 1L;
            {
            	put("total", i);
                put("result", object);
            }
        };
	}
}
