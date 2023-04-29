package org.yearup;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Scanner;
import java.util.HashMap;

public class OnlineStoreApp {

    static ArrayList<Inventory> getOnlineStore = new ArrayList<>();
    static HashMap<Inventory, Integer> cart = new HashMap<>();
    static Scanner scanner = new Scanner(System.in);

    public static void run() throws Exception {

        FileReader fileReader = new FileReader("inventory.csv");
        BufferedReader reader = new BufferedReader(fileReader);

        reader.readLine();

        String line = reader.readLine();
        while (line != null) {
            String[] columns = line.split("\\|");

            String id = columns[0];
            String name = columns[1];
            double price = Double.parseDouble(columns[2]);

            Inventory inventory = new Inventory(id, name, price);

            getOnlineStore.add(inventory);

            line = reader.readLine();
        }
        reader.close();

        while (true) {
            int command;
                command = displayHomeScreen();
                if (command == 0) {
                    System.out.println("Thank you for shopping. Have a great day!");
                    break;
                }while (command != 0);
        }
    }

    public static int displayHomeScreen() {
        int command;
            System.out.println();
            System.out.println("What would you like to do?");
            System.out.println(" 1 - Show Products");
            System.out.println(" 2 - Show cart");
            System.out.println(" 0 - Exit Store");
            System.out.print("Enter your option: ");


            command = scanner.nextInt();
            switch (command) {
                case 1:
                    showAllProducts();
                    break;
                case 2:
                    showCart();
                    break;
                case 0:
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
                    break;
            }
        return command;

    }


    public static void showAllProducts() {

        for (Inventory inventory : getOnlineStore) {
            System.out.println(inventory.getId() + " - " + inventory.getName() + " - $" + inventory.getPrice());
        }

        char option;
        do {
            System.out.println();
            System.out.println("What would you like to do?");
            System.out.println(" A - Add items to cart");
            System.out.println(" X - Return to homepage");
            System.out.print("Enter your options: ");
            option = scanner.next().charAt(0);

            switch (option) {
                case 'A':
                case 'a':
                    addToCart();
                    break;
                case 'X':
                case 'x':
                    displayHomeScreen();
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
                    return;
            }
        } while (option != 'X' && option != 'x');

    }

    public static void addToCart() {
        System.out.print("Enter the ID of the product you would like to add to your cart: ");
        String productID = scanner.next().toUpperCase(Locale.ROOT);

        for (Inventory inventory : getOnlineStore) {
            if (inventory.getId().equals(productID)) {
                if (cart.containsKey(inventory)) {
                    int currentQuantity = cart.get(inventory);
                    cart.put(inventory, currentQuantity + 1);
                } else {
                    cart.put(inventory, 1);
                }
                System.out.println(inventory.getName() + " has been added to the cart.");
                return;
            }
        }
        System.out.println("Invalid product ID. Please try again.");
    }


    public static void showCart() {

        System.out.println();
        System.out.println("Items in cart");
        System.out.println("----------------");

        double total = 0;

        for (Inventory inventory : cart.keySet()) {
            int quantity = cart.get(inventory);
            double price = inventory.getPrice();
            double subtotal = price * quantity;
            System.out.println(inventory.getName() + " - $" + inventory.getPrice() + " x " + quantity + " = " + subtotal);
            total += subtotal;
        }
        System.out.println("Total: $" + total);

        char option;
        do {
            System.out.println("What would you like to do?");
            System.out.println(" C - Checkout Cart");
            System.out.println(" X - Go back to homepage");
            System.out.print("Enter your option: ");
            option = scanner.next().charAt(0);
            System.out.println();

            switch (option) {
                case 'C':
                case 'c':
                    checkOutCart();
                    break;
                case 'X':
                case 'x':
                    displayHomeScreen();
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
                    break;
            }
        }while (option != 'X' && option != 'x');
    }

    public static void checkOutCart() {
        double total = 0;

        System.out.println();
        System.out.println("Items in cart");
        System.out.println("----------------");

        for (Inventory inventory : cart.keySet()) {
            int quantity = cart.get(inventory);
            double price = inventory.getPrice();
            double subtotal = price * quantity;
            System.out.println(inventory.getName() + " - $" + inventory.getPrice() + " x " + quantity + " = " + subtotal);
            total += subtotal;
        }
        System.out.println("Total: $" + total);

        System.out.println("Please enter your payment amount: ");
        double payment = scanner.nextDouble();

        if (payment >= total) {
            double change = payment - total;
            double roundedChange = Math.round(change * 0);
            System.out.println("Your change is $" + change + roundedChange);
            System.out.println("Thank you for your purchase!");
            cart.clear();
        } else {
            System.out.println("Insufficient payment. Returning full amount.");
            showCart();
        }

        char option;
        do {
            System.out.println("What would you like to do?");
            System.out.println(" X - Return to homepage");
            System.out.println(" Q - Exit store");
            System.out.print("Enter your option: ");
            option = scanner.next().charAt(0);

            switch (option) {
                case 'X':
                case 'x':
                    displayHomeScreen();
                    break;
                case 'Q':
                case 'q':
                    System.out.println("Thank you for shopping. Have a great day!");
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        } while (option != 'Q' && option != 'q');
    }

}

