package com.bh.ecsite.value;

import java.io.Serializable;

public class UserDTO implements Serializable {
	private String userId;
	private String password;
	private String name;
	private String address;

	public UserDTO(String userId, String password, String name, String address) {
		this.userId = userId;
		this.password = password;
		this.name = name;
		this.address = address;
	}

	public UserDTO() {
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean checkPassword(String password) {
		if (this.password != null) {
			if (this.password.equals(password)) {
				return true;
			}
		}
		return false;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
}
