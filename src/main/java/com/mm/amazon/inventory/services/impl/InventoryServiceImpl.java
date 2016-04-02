package com.mm.amazon.inventory.services.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.mm.amazon.inventory.domain.Entry;
import com.mm.amazon.inventory.domain.InventoryItem;
import com.mm.amazon.inventory.services.InventoryService;

/**
 * 
 * @author mmackevicius
 *
 */
public class InventoryServiceImpl implements InventoryService {

	private InputStream inp = null;
	private InputStream inpDe = null;
	private InputStream inpFr = null;

	private Workbook workbook = null;
	private Workbook workbookDe = null;
	private Workbook workbookFr = null;
	private static int startRow = 4;
	private Map<Integer, String> sizeMap = null;
	private Map<Integer, String> sizeCreMap = null;

	public InventoryServiceImpl() {
		try {
			inp = new FileInputStream("C:\\excel\\men_uk_output.xls");
			workbook = WorkbookFactory.create(inp);

			inpDe = new FileInputStream("C:\\excel\\men_de_output.xls");
			workbookDe = WorkbookFactory.create(inp);

			inpFr = new FileInputStream("C:\\excel\\men_fr_output.xls");
			workbookFr = WorkbookFactory.create(inp);

			sizeMap = new HashMap<>();
			sizeCreMap = new HashMap<>();
			sizeMap.put(0, "");
			sizeMap.put(1, "Small");
			sizeMap.put(2, "Medium");
			sizeMap.put(3, "Large");
			sizeMap.put(4, "XL");

			sizeCreMap.put(0, "");
			sizeCreMap.put(1, "-S");
			sizeCreMap.put(2, "-M");
			sizeCreMap.put(3, "-L");
			sizeCreMap.put(4, "-XL");
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void addItem(Entry item) {
		String sku = "grau-Chiefprints" + UUID.randomUUID().toString().substring(0, 7);

		for (int i = 0; i < 5; i++) {
			addItem(item, i, sku, workbook);
		}

		for (int i = 0; i < 5; i++) {
			addItem(item, i, sku, workbookDe);
		}

		for (int i = 0; i < 5; i++) {
			addItem(item, i, sku, workbookFr);
		}

	}

	private void addItem(Entry item, int position, String sku, Workbook wb) {

		try {
			Sheet worksheet = wb.getSheet("input");
			Row row = worksheet.createRow(startRow);

			Cell skuCell = row.createCell(0);
			String itemName = sku + sizeCreMap.get(position);
			skuCell.setCellValue(itemName);

			Cell nameCell = row.createCell(3);
			nameCell.setCellValue(item.getTitle().substring(0, item.getTitle().indexOf(".")));

			Cell chiefprints = row.createCell(4);
			chiefprints.setCellValue("Chiefprints");

			Cell type = row.createCell(5);
			type.setCellValue("shirt");

			Cell productDescription = row.createCell(6);
			productDescription.setCellValue(
					"Best quality T-shirt with Direct To Garment technology which provides high and very nice looking print on a product. Satisfy yourself or your person with brand new unique T-shirt directly from our hands!");

			Cell priceCell = row.createCell(10);
			priceCell.setCellValue(item.getPrice() + "");

			Cell currencyCell = row.createCell(11);
			currencyCell.setCellValue("EUR");

			Cell quantity = row.createCell(12);
			quantity.setCellValue("9999");

			// Cell recomended = row.createCell(28);
			// recomended.setCellValue("1981872031");

			Cell keywords1 = row.createCell(29);
			keywords1.setCellValue("Clothes,clothing,tshirt,tee,shirt,for,girl,boy");

			Cell keywords2 = row.createCell(30);
			keywords2.setCellValue("store,t,order,where,buy,");

			Cell keywords3 = row.createCell(31);
			keywords3.setCellValue("anime,print,image,art,photo,quote,movie,picture");

			Cell image = row.createCell(44);
			image.setCellValue(item.getImageUrl());

			Cell otherImage = row.createCell(45);
			otherImage.setCellValue("http://i.imgur.com/QBroqUI.jpg");

			String relationship = position % 4 == 0 ? "parent" : "child";

			Cell relationshipCell = row.createCell(61);
			relationshipCell.setCellValue(relationship);

			Cell variation = row.createCell(63);
			variation.setCellValue("variation");

			Cell sizecolor = row.createCell(64);
			sizecolor.setCellValue("sizecolor");
			Cell country = row.createCell(68);
			country.setCellValue("Lithuania");
			Cell color = row.createCell(71);
			color.setCellValue("Color");

			Cell size = row.createCell(72);
			Cell sizeName = row.createCell(73);
			size.setCellValue(sizeMap.get(position));
			sizeName.setCellValue(sizeMap.get(position));

			Cell material = row.createCell(74);
			material.setCellValue("100% Cotton");

			startRow++;
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}

	}

	@Override
	public void persistItems() {
		System.out.println("Writting to file....");
		try {
			startRow = 4;
			String id = UUID.randomUUID().toString();
			workbook.write(new FileOutputStream(new File("Inventory-UK-" + id + ".xls")));
			workbookDe.write(new FileOutputStream(new File("Inventory-FR-" + id + ".xls")));
			workbookFr.write(new FileOutputStream(new File("Inventory-DE-" + id + ".xls")));
			// workbook = new XSSFWorkbook(new FileInputStream("Inventory" + id
			// + ".xls"));
		} catch (IOException e) {
			throw new RuntimeException(e);
		} finally {
			// workbook = null;
		}
	}

}
