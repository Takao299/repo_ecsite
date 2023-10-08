package com.bh.ecsite.value;

import java.io.Serializable;

public class CategoryDTO implements Serializable {
	private int categoryId;
	private String name;

	public CategoryDTO(int categoryId, String name) {
		super();
		this.categoryId = categoryId;
		this.name = name;
	}

	public CategoryDTO() {
	}

	public int getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
