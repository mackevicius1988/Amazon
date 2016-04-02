package com.mm.amazon.inventory.domain;

import java.math.BigDecimal;

/**
 * 
 * @author mmackevicius
 *
 */
public class Entry {
	@Override
	public String toString() {
		return "Entry [title=" + title + ", price=" + price + ", imageUrl=" + imageUrl + "]";
	}

	private String title;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	private BigDecimal price;
	private String imageUrl;
}
