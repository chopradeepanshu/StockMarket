package com.stockmarket.ignition;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Ignition {

	public static int chooseOption(BufferedReaders br) {
        while (true) {
            System.out.println("""
                                        
                    Please choose an option from below
                    1. Show products
                    2. Show customers
                    3. Update price for a product
                    4. Update threshold for a user
                    5. Set new triggers
                    6. Add Customer
                    7. Add Product
                    8. Remove Customer
                    9. Remove Product
                    10. Exit""");
            try {
                return Integer.parseInt(br.readLine());
            } catch (NumberFormatException | IOException ex) {
                System.out.println("Please provide only integer values");
            }
        }
    }

	public static void main(String[] args) {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		Monitor monitor = new Monitor();
		while (true) {
			switch (chooseOption(reader)) {
			case 1 -> System.out.println(monitor.getProducts());
			case 2 -> System.out.println(monitor.getCustomers());
			case 3 -> monitor.setPriceForProduct();
			case 4 -> monitor.setThresholdForUser();
			case 5 -> monitor.setNewTrigger();
			case 6 -> monitor.addCustomer();
			case 7 -> monitor.addProduct();
			case 8 -> monitor.deleteCustomer();
			case 9 -> monitor.deleteProduct();
			case 10 -> System.exit(0);
			default -> System.out.println("Incorrect option selected, please retry.");
			}
		}
	}
}
