package com.bh.ecsite.value;

import java.io.Serializable;

public class PurchaseDetailDTO implements Serializable {
	private int purchaseDetailId;
	private int purchaseId;
	private ItemDTO item;
	private int amount;

	public PurchaseDetailDTO() {
	}

	public PurchaseDetailDTO(int purchaseDetailId, int purchaseId,
			ItemDTO item, int amount) {
		super();
		this.purchaseDetailId = purchaseDetailId;
		this.purchaseId = purchaseId;
		this.item = item;
		this.amount = amount;
	}

	public int getPurchaseDetailId() {
		return purchaseDetailId;
	}

	public void setPurchaseDetailId(int purchaseDetailId) {
		this.purchaseDetailId = purchaseDetailId;
	}

	public int getPurchaseId() {
		return purchaseId;
	}

	public void setPurchaseId(int purchaseId) {
		this.purchaseId = purchaseId;
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
}
