package com.mm.amazon.inventory.services;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

import com.mm.amazon.inventory.domain.Entry;

/**
 * 
 * @author mmackevicius
 *
 */
public interface ImageService {
	/**
	 * 
	 * @param image
	 */
	List<Entry> uploadImages(String imageDirectoryUrl, BigDecimal price);
}
