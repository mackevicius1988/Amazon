package com.mm.amazon.inventory.services;

import com.mm.amazon.inventory.domain.Entry;

/**
 * 
 * @author mmackevicius
 *
 */
public interface InventoryService {
	
	/**
	 * 
	 * @param item
	 */
	void addItem(Entry item);

	void persistItems();
}
