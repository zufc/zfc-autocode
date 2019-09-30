package com.zfc.code.data;


import com.zfc.code.util.NameFormator;

/**
 * 表字段属性
 */
public class ItemProperty {


	private String filedName ;
	/** 字段属性名称 */
	private String name;
	/** 首字母大写名称*/
	private String firstBigName;
	
	/** 字段类型（int, varchar, ...） */
	private String type;

	//数据库字段
	private String dbType ;
	
	/** 字段Java类型（int, String, ...） */
	private String jtype;
	
	/** 字段描述 */
	private String comment;
	
	/** 是否主键 */
	private boolean priKey;

	private boolean nullable;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public boolean isPriKey() {
		return priKey;
	}

	public void setPriKey(boolean priKey) {
		this.priKey = priKey;
	}

	public String getJtype() {
		return jtype;
	}

	public void setJtype(String jtype) {
		this.jtype = jtype;
	}

	public String getFirstBigName() {
		if(name != null && name.trim().length()!=0){
			firstBigName = NameFormator.toUfirst(name) ;
		}
		return firstBigName;
	}

	public void setFirstBigName(String firstBigName) {
		this.firstBigName = firstBigName;
	}


	public String getDbType() {
		return dbType;
	}

	public void setDbType(String dbType) {
		this.dbType = dbType;
	}

	public String getFiledName() {
		return filedName;
	}

	public void setFiledName(String filedName) {
		this.filedName = filedName;
	}


	public boolean isNullable() {
		return nullable;
	}

	public void setNullable(String nullString) {
		if("YES".equals(nullString)){
			nullable = true ;
		}else {
			nullable = false ;
		}
	}
}
