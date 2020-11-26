package org.bachelor.dao;

public class DbContextHolder {
	 private static final ThreadLocal<String> contextHolder = new ThreadLocal<String>();   
	   
	public static void setDataResourceName(String drName) {
		contextHolder.set(drName);
	}

	public static String getDataResourceName() {
		return (String) contextHolder.get();
	}

	public static void clear() {
		contextHolder.remove();
	}
}
