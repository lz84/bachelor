/*
 * @(#)DateUtil.java	May 23, 2013
 *
 * Copyright (c) 2013, Team Bachelor. All rights reserved.
 */
package org.bachelor.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;

/**
 * @author user
 *
 */
public class DateUtil {

	private static String formatStr = "yyyy-MM-dd HH:mm:ss";
	
	public static String format(Date date){
		return null;
	}
	
	public static String format(Date date, String formartStr){
		return null;
	}
	
	public static Date parse(String dateStr){
		return null;
	}
	
	public static Date parse(String dateStr, String formatStr){
		return null;
	}
	
	public static Date addSeconds(Date date, int day){
		return null;
	}
	
	public static Date addMinutes(Date date, int day){
		return null;
	}
	
	public static Date addHours(Date date, int day){
		return null;
	}
	
	public static Date addDays(Date date, int day){
		return null;
	}
	
	public static Date addWeeks(Date date, int day){
		return null;
	}
	
	public static Date addYears(Date date, int day){
		return null;
	}

	public static Date Str2Date(SimpleDateFormat sdf,String date) throws ParseException{
		if(!StringUtils.isEmpty(date)){
			
			return sdf.parse(date);
		}
		return null;
		
	}
}
