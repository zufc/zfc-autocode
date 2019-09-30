package com.zfc.code.data;


import com.zfc.code.util.NameFormator;

import java.util.List;

/**
 * 数据表属性
 */
public class TableProperty {
	
	/** 表名 */
	private String name;

	/** 类名 */
	private String className;


	private String firstBigName ;


	private String firstLowerName ;

	//模块名称
	private String modeName ;

	//基础包名称
	private String basicPackage ;

	/** 项目名 */
	private String project;
	
	/** 表注释 */
	private String comment;
	
	/** 表字段 */
	private List<ItemProperty> items;
	
	/** 用于搜索的表字段 */
	private List<ItemProperty> search;
	
	/** 主键 */
	private ItemProperty prikey;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public List<ItemProperty> getItems() {
		return items;
	}

	public void setItems(List<ItemProperty> items) {
		this.items = items;
	}

	public List<ItemProperty> getSearch() {
		return search;
	}

	public void setSearch(List<ItemProperty> search) {
		this.search = search;
	}

	public ItemProperty getPrikey() {
		return prikey;
	}

	public void setPrikey(ItemProperty prikey) {
		this.prikey = prikey;
	}

	public String getClassName() {
		if(name != null && name.trim().length()!=0){
			className = NameFormator.toUUCase(name);
		}
		return className;
	}
	public String getClassNameFirstLower(){
		if(name != null && name.trim().length()!=0){
			className = NameFormator.toUUCase(name);
		}
		return NameFormator.toLCase(className);
	}
	public void setClassName(String className) {
		this.className = className;
	}

	public String getProject() {
		return project;
	}

	public void setProject(String project) {
		this.project = project;
	}

	public String getFirstBigName() {
		return firstBigName;
	}

	public void setFirstBigName(String firstBigName) {
		this.firstBigName = firstBigName;
	}

	public String getModeName() {
		return modeName;
	}

	public void setModeName(String modeName) {
		this.modeName = modeName;
	}

	public String getBasicPackage() {
		return basicPackage;
	}

	public void setBasicPackage(String basicPackage) {
		this.basicPackage = basicPackage;
	}

	public String getFirstLowerName() {
		return firstLowerName;
	}

	public void setFirstLowerName(String firstLowerName) {
		this.firstLowerName = firstLowerName;
	}
}
