package com.bh.ecsite.value;

import java.io.Serializable;
import java.sql.Date;

public class ItemInCartDTO implements Serializable {
	private String userId;
	private ItemDTO item;
	private int amount;
	private Date bookedDate;

	public ItemInCartDTO() {
	}

	public ItemInCartDTO(String userId, ItemDTO item, int amount,
			Date bookedDate) {
		super();
		this.userId = userId;
		this.item = item;
		this.amount = amount;
		this.bookedDate = bookedDate;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public ItemDTO getItem() {
		return item;
	}

	public void setItem(ItemDTO item) {
		this.item = item;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public Date getBookedDate() {
		return bookedDate;
	}

	public void setBookedDate(Date bookedDate) {
		this.bookedDate = bookedDate;
	}
}
