package org.bachelor.web.springmvc;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

public class BaseController {

	private String dateFormat = "yyyy-MM-dd HH:mm:ss";
	
	@InitBinder  
	public void initBinder(WebDataBinder binder) throws Exception {  
	    //注册自定义的属性编辑器  
	    DateFormat df = new SimpleDateFormat(dateFormat);  
	    CustomDateEditor dateEditor = new CustomDateEditor(df, true);  
	    //表示如果命令对象有Date类型的属性，将使用该属性编辑器进行类型转换  
	    binder.registerCustomEditor(Date.class, dateEditor);      
	}

	public String getDateFormat() {
		return dateFormat;
	}

	public void setDateFormat(String dateFormat) {
		this.dateFormat = dateFormat;
	}
	
	
}
