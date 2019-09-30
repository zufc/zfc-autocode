package com.zfc.code.data;

import java.util.HashMap;
import java.util.Map;

/**
 * 类型转换：mysql -> Java  数据和字段对应关系
 *
 */
public class JavaType {
	
	private static Map<String, String> map = new HashMap<String, String>();
	
	static {
		map.put("int", "Integer");
		map.put("bigint", "Long");
		map.put("varchar", "String");
		map.put("text", "String");
		map.put("longtext", "String");
		map.put("datetime", "LocalDateTime");
		map.put("double", "Double");
		map.put("decimal", "Double");
		map.put("tinyint", "Integer");
		map.put("date", "LocalDate");
		map.put("char", "String");
		map.put("smallint", "Integer");
		
	}
	
	public static String get(String sqlType) {
		return map.get(sqlType.toLowerCase());
	}

}
