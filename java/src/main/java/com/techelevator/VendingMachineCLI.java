package com.techelevator;

import com.techelevator.view.Menu;

import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class VendingMachineCLI {

	private static final String MAIN_MENU_OPTION_DISPLAY_ITEMS = "Display Vending Machine Items";
	private static final String MAIN_MENU_OPTION_PURCHASE = "Purchase";
	private static final String MAIN_MENU_EXIT = "Exit";
	private static final String[] MAIN_MENU_OPTIONS = { MAIN_MENU_OPTION_DISPLAY_ITEMS, MAIN_MENU_OPTION_PURCHASE, MAIN_MENU_EXIT};
	private static final String[] PURCHASE_OPTIONS = {"Feed Money", "Select Product", "Finish Transaction"};

	private Menu menu;
	List<Item> itemList = new ArrayList<>();
	Purchasing balance = new Purchasing();
	//List<Item> saleCounterList = new ArrayList<>();



	public VendingMachineCLI(Menu menu) {
		this.menu = menu;
	}

	public void run() {

		loadData();

		while (true) {
			String choice = (String) menu.getChoiceFromOptions(MAIN_MENU_OPTIONS);

			if (choice.equals(MAIN_MENU_OPTION_DISPLAY_ITEMS)) {
				displayItems();
			} else if (choice.equals(MAIN_MENU_OPTION_PURCHASE)) {
				displayPurchaseMenu();
			} else if (choice.equals(MAIN_MENU_EXIT)) {
				System.out.println("Thank you for choosing the Vendo-Matic 800!");
//				String fileName = new SimpleDateFormat("yyyy-MM-dd hh-mm-ss'.txt'").format(new Date());
//				SalesReport finalReport = new SalesReport(fileName);
//				//finalReport.generateReport(itemList);
//				finalReport.timeStampedSalesReport("salesreport.txt", fileName);
				System.exit(1);
			}
		}
	}

	public void loadData() {
		File file = new File("vendingmachine.csv");

		try {
			Scanner scanner = new Scanner(file);
			while (scanner.hasNextLine()) {
				String line = scanner.nextLine();
				String[] lineArr = line.split("\\|");
				String slot = lineArr[0];
				String name = lineArr[1];
				BigDecimal price = new BigDecimal(lineArr[2]);
				String type = lineArr[3];

				// Add the item to the list by type. Will allow to getSound when item is purchased.
				if (type.equals("Chip")) {
					Item item = new Chip(slot, name, price);
					itemList.add(item);
				} else if (type.equals("Candy")) {
					Item item = new Candy(slot, name, price);
					itemList.add(item);
				} else if (type.equals("Drink")) {
					Item item = new Drink(slot, name, price);
					itemList.add(item);
				} else if (type.equals("Gum")) {
					Item item = new Gum(slot, name, price);
					itemList.add(item);
				}
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	public void displayItems() {
		System.out.println("=========================================");
		System.out.println("          Vending Machine Items          ");
		System.out.println("=========================================");

		for (Item item : itemList) {
			System.out.println(item.getSlot() + " | " + item.getName() + " | $" + item.getPrice() + " | " + item.getInventory() + " Remaining");
		}
	}

	public void displayPurchaseMenu() {
		System.out.println("=========================================");
		System.out.println("             Purchasing Menu             ");
		System.out.println("=========================================");


		boolean keepPurchasing = true;

		while (keepPurchasing) {
			System.out.println("\nCurrent Money Provided: $" + balance.getBalance());
			String choice = (String) menu.getChoiceFromOptions(PURCHASE_OPTIONS);

			if (choice.equals("Feed Money")) {
				balance.feedMoney();
			} else if (choice.equals("Select Product")) {
				selectProduct();
			} else if (choice.equals("Finish Transaction")) {
				System.out.println("===========================================");
				System.out.println("Thank you for choosing the Vendo-Matic 800!");
				System.out.println("Please Take Your Change!");
				System.out.println(balance.getChange(balance.getBalance()));
				System.out.println("===========================================");
				System.exit(1);
			}
		}
	}

	public void selectProduct() {
		displayItems();
		Scanner scanner = new Scanner(System.in);
		boolean run = true;

		while(run) {
			System.out.println("Select an item to purchase: ");
			String itemToPurchase = scanner.nextLine();

			boolean valid = false;
			for (Item item : itemList) {
				if (item.getSlot().equals(itemToPurchase.toUpperCase()) && balance.getBalance().compareTo(item.getPrice()) >= 0 && item.getInventory() > 0) {
					valid = true;
				}
					if (valid) {
						item.increaseSaleCounter();
						BigDecimal beforePurchase = balance.getBalance();
						item.lowerInventory();
						balance.removeMoney(item.getPrice());
						System.out.println("=========================");
						System.out.println(item.getName());
						System.out.println("$" + item.getPrice());
						System.out.println(item.getSound());
						System.out.println("=========================");

						LogWriter writer = new LogWriter("log.txt");
						writer.writeToFile(item.getName() + " " + item.getSlot() + " $" + beforePurchase + " $" + balance.getBalance());
						run = false;
						break;
					}
				}
			if (!valid) {
				System.out.println("==================================================");
				System.out.println("Transaction Declined. Returning to Purchasing Menu");
				System.out.println("==================================================");
				run = false;
			}
		}

		String fileName = new SimpleDateFormat("yyyy-MM-dd hh-mm-ss'.txt'").format(new Date());
		SalesReport report = new SalesReport("salesreport.txt");
		report.generateReport(itemList);
		report.timeStampedSalesReport("salesreport.txt", fileName);
	}

	public static void main(String[] args) {
		System.out.println("===================================");
		System.out.println("|         VENDO-MATIC 800         |");
		System.out.println("===================================");

		Menu menu = new Menu(System.in, System.out);
		VendingMachineCLI cli = new VendingMachineCLI(menu);
		cli.run();
	}

}
