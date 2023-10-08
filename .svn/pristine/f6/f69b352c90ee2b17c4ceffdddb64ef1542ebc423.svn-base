package com.bh.ecsite.value;

import java.io.Serializable;
import java.util.ArrayList;

public class PurchaseDTO implements Serializable {
	private int purchaseId;
	private UserDTO purchasedUser;
	private ArrayList<PurchaseDetailDTO> purchasedItems;
	private java.sql.Date purchasedDate;
	private String destination;
	private boolean cancel;

	public PurchaseDTO(int purchaseId, UserDTO purchasedUser,
			ArrayList<PurchaseDetailDTO> purchasedItems, java.sql.Date purchasedDate,
			String destination, boolean cancel) {
		super();
		this.purchaseId = purchaseId;
		this.purchasedUser = purchasedUser;
		this.purchasedItems = purchasedItems;
		this.purchasedDate = purchasedDate;
		this.destination = destination;
		this.cancel = cancel;
	}

	public PurchaseDTO() {
	}

	public int getPurchaseId() {
		return purchaseId;
	}

	public void setPurchaseId(int purchaseId) {
		this.purchaseId = purchaseId;
	}

	public UserDTO getPurchasedUser() {
		return purchasedUser;
	}

	public void setPurchasedUser(UserDTO purchasedUser) {
		this.purchasedUser = purchasedUser;
	}

	public ArrayList<PurchaseDetailDTO> getPurchasedItems() {
		return purchasedItems;
	}

	public void setPurchasedItems(ArrayList<PurchaseDetailDTO> purchasedItems) {
		this.purchasedItems = purchasedItems;
	}

	public java.sql.Date getPurchasedDate() {
		return purchasedDate;
	}

	public void setPurchasedDate(java.sql.Date purchasedDate) {
		this.purchasedDate = purchasedDate;
	}

	public String getDestination() {
		return destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}

	public boolean isCancel() {
		return cancel;
	}

	public void setCancel(boolean cancel) {
		this.cancel = cancel;
	}

}
