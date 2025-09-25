package com.dto;

public class CategoryDTO {
	//vars
	private long category_id;
	private String name;
	private String description;
	
	public long getCategory_id() {
		return category_id;
	}
	public void setCategory_id(long category_id) {
		this.category_id = category_id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}

	//all-args-constr
	public CategoryDTO(long category_id, String name, String description) {
		super();
		this.category_id = category_id;
		this.name = name;
		this.description = description;
	}
	
	//no-args constr
	public CategoryDTO() {}


}
