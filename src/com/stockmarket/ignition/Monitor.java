package com.stockmarket.ignition;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import com.stockmarket.actions.Notification;
import com.stockmarket.data.CustomerNotification;
import com.stockmarket.data.User;
import com.stockmarket.data.Stock;
import com.stockmarket.data.TriggerAlerts;
import com.stockmarket.data.BuyOrSell;
import com.stockmarket.exceptions.UnknownValueException;
import com.stockmarket.models.StockAlerts;
import com.stockmarket.models.FinancialInstrument;
import com.stockmarket.models.Person;
import com.stockmarket.models.StockTriggers;

public class Monitor {
	Map<String, FinancialInstrument> stocks;
	Map<String, Person> customers;
	Map<FinancialInstrument, List<StockTriggers>> triggerAlerts;
	private final BufferedReader reader;
	private final StockAlerts alerts;

	Monitor() {
		stocks = new HashMap<>();
		customers = new HashMap<>();
		triggerAlerts = new HashMap<>();
		alerts = new Notification();
		reader = new BufferedReader(new InputStreamReader(System.in));
	}

	public List<FinancialInstrument> getProducts() {
		return new ArrayList<>(stocks.values());
	}

	public List<Person> getCustomers() {
		return new ArrayList<>(customers.values());
	}

	public void addCustomer() {
		try {
			System.out.println("Please enter the name of customer:");
			String name = reader.readLine();
			System.out.println("Do you want to add both email and phone ? (y/n");
			String option = reader.readLine();
			long phoneNum = -1;
			String email = null;
			if (option.equals("y")) {
				System.out.println("Please enter the phone number");
				phoneNum = Long.parseLong(reader.readLine());
				System.out.println("Please enter the email id");
				email = reader.readLine();
			} else {
				System.out.println("What do want to enter? (email/phone)");
				String opt2 = reader.readLine();
				if (opt2.equals("email")) {
					System.out.println("Please enter the email ID");
					email = reader.readLine();
				} else {
					System.out.println("Please enter the phone number");
					phoneNum = Long.parseLong(reader.readLine());
				}
			}

			String uniqueId;
			do {
				uniqueId = String.valueOf(Math.abs(new Random().nextInt()));
			} while (customers.containsKey(uniqueId));

			Person customer = (email != null && phoneNum > 0) ? new User(name, uniqueId, email, phoneNum)
					: (email == null) ? new User(name, uniqueId, phoneNum) : new User(name, uniqueId, email);
			customers.put(uniqueId, customer);
		} catch (IOException | NumberFormatException ex) {
			System.out.println(ex.getMessage());
			System.out.println("please try again!!");
		}
	}

	public void addProduct() {
		try {
			String uniqueId, productName;
			float price = -1.0f;
			System.out.println("Please enter the product name");
			productName = reader.readLine();
			System.out.println("Please enter the price of the product");
			price = Float.parseFloat(reader.readLine());

			do {
				uniqueId = String.valueOf(Math.abs(new Random().nextInt()));
				;
			} while (customers.containsKey(uniqueId));
			FinancialInstrument stock = new Stock(productName, uniqueId, price);
			stocks.put(stock.getStockId(), stock);
		} catch (IOException | NumberFormatException ex) {
			System.out.println(ex.getMessage());
			System.out.println("please try again!!");
		}
	}

	public void deleteCustomer() {
		System.out.println("Please enter the customerId");
		try {
			String customerId = reader.readLine();
			if (customers.containsKey(customerId)) {
				Person cust = customers.remove(customerId);
				System.out.printf("Customer %s deleted successfully", cust.getName());
			} else {
				System.out.printf("Customer with id %s not present in the database", customerId);
			}
		} catch (IOException ex) {
			System.out.println(ex.getMessage());
			System.out.println("Please try again");
		}
	}

	public void deleteProduct() {
		System.out.println("Please enter the productId");
		try {
			String productId = reader.readLine();
			if (stocks.containsKey(productId)) {
				FinancialInstrument product = stocks.remove(productId);
				System.out.printf("Product %s deleted successfully", product.getName());
			} else {
				System.out.printf("Product with id %s not present in the database", productId);
			}
		} catch (IOException ex) {
			System.out.println(ex.getMessage());
			System.out.println("Please try again");
		}
	}

	public void setPriceForProduct() {
		System.out.println("Please enter the productId");
		try {
			String productId = reader.readLine();
			if (stocks.containsKey(productId)) {
				System.out.println("Please enter the new amount");
				float price = Float.parseFloat(reader.readLine());
				FinancialInstrument product = stocks.get(productId);
				float priceDiff = Math.abs(product.getPrice() - price);
				checkAndSendAlerts(product, priceDiff,
						(priceDiff > 0.0f) ? BuyOrSell.BuyTrigger : BuyOrSell.SellTrigger);
				product.setPrice(price);
				System.out.println("Product price updated successfully");
			} else {
				System.out.printf("Product with id %s not present in the database", productId);
			}
		} catch (IOException | NumberFormatException ex) {
			System.out.println(ex.getMessage());
			System.out.println("Please try again");
		}
	}

	private void checkAndSendAlerts(FinancialInstrument product, float priceDiff, BuyOrSell triggerType) {
		for (StockTriggers trigger : triggerAlerts.get(product)) {
			if ((trigger.getTriggerThreshold() < priceDiff) && (trigger.getTriggerType().equals(triggerType))) {
				try {
					alerts.sendNotification(trigger.getPerson(), product, trigger);
				} catch (UnknownValueException ex) {
					System.out.println(ex.getMessage());
				}
			}
		}
	}

	public void setThresholdForUser() {
		try {
			System.out.println("Please enter the productId");
			String productId = reader.readLine();
			System.out.println("Please enter the customerId");
			String customerId = reader.readLine();

			if (stocks.containsKey(productId) && customers.containsKey(customerId)) {
				System.out.println("Please enter the threshold for notification");
				float threshold = Float.parseFloat(reader.readLine());
				for (StockTriggers trigger : triggerAlerts.get(productId)) {
					if (trigger.getPerson().getCustId().equals(customerId)) {
						trigger.setTriggerThreshold(threshold);
						break;
					}
				}
				System.out.println("Threshold updated successfully");
			} else {
				System.out.printf("Product with id %s or customer with id %s not present in the database", productId,
						customerId);
			}
		} catch (IOException | NumberFormatException ex) {
			System.out.println(ex.getMessage());
			System.out.println("Please try again");
		}
	}

	public void setNewTrigger() {
		try {
			System.out.println("Please enter the productId");
			String productId = reader.readLine();
			System.out.println("Please enter the customerId");
			String customerId = reader.readLine();

			if (stocks.containsKey(productId) && customers.containsKey(customerId)) {

				System.out.println("Please enter the trigger type: (up, down)");
				BuyOrSell triggerType = reader.readLine().equals("up") ? BuyOrSell.SellTrigger
						: BuyOrSell.BuyTrigger;

				System.out.println("Please enter the communication type: (phone/email)");
				CustomerNotification communication = reader.readLine().equals("email") ? CustomerNotification.Email
						: CustomerNotification.Phone;

				System.out.println("Please enter the threshold for notification");
				float threshold = Float.parseFloat(reader.readLine());

				StockTriggers trigger = new TriggerAlerts(triggerType, customers.get(customerId), communication, threshold);
				List<StockTriggers> triggersList = triggerAlerts.getOrDefault(stocks.get(productId), new ArrayList<>());
				triggersList.add(trigger);
				triggerAlerts.put(stocks.get(productId), triggersList);
				System.out.println("New Trigger set successfully");
			} else {
				System.out.printf("Product with id %s or customer with id %s not present in the database", productId,
						customerId);
			}
		} catch (IOException | NumberFormatException ex) {
			System.out.println(ex.getMessage());
			System.out.println("Please try again");
		}
	}
}
