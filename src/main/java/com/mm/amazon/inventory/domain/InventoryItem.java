package com.mm.amazon.inventory.domain;

import java.math.BigDecimal;

public class InventoryItem {

	private String direcotry;
	private BigDecimal price;

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	private String language;

	public BigDecimal getPrice() {
		return price;
	}

	public String getDirecotry() {
		return direcotry;
	}

	public void setDirecotry(String direcotry) {
		this.direcotry = direcotry;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

}
