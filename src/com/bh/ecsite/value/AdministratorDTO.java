package com.bh.ecsite.value;

import java.io.Serializable;

public class AdministratorDTO implements Serializable {
	private String adminId;
	private String password;
	private String name;

	public AdministratorDTO() {
	}

	public void setAdminId(String adminId) {
		this.adminId = adminId;
	}

	public String getAdminId() {
		return adminId;
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

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
}
