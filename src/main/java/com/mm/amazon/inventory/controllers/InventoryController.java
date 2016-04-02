package com.mm.amazon.inventory.controllers;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.mm.amazon.inventory.domain.Entry;
import com.mm.amazon.inventory.domain.InventoryItem;
import com.mm.amazon.inventory.services.ImageService;
import com.mm.amazon.inventory.services.InventoryService;

/**
 * 
 * @author mmackevicius
 *
 */
@Controller
public class InventoryController {

	@Autowired
	ImageService imageService;

	@Autowired
	InventoryService inventoryService;

	@RequestMapping(value = "/home", method = RequestMethod.GET)
	public String greetingForm(Model model) {
		model.addAttribute("inventoryItem", new InventoryItem());
		return "home";
	}

	@RequestMapping(value = "/addItem", method = RequestMethod.POST)
	public String addItem(@ModelAttribute InventoryItem inventoryItem, Model model) throws IOException {
		System.out.println("Starting....");
		Thread job = new Thread() {
			@Override
			public void run() {
				List<Entry> entries = imageService.uploadImages(inventoryItem.getDirecotry(), inventoryItem.getPrice());
				for (Entry entry : entries) {
					inventoryService.addItem(entry);
				}
				
				inventoryService.persistItems();
			}
		};
		job.start();

		return "home";
	}

}
