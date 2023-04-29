package org.yearup;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
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
            System.out.println(inventory.getId() + " - " + inventory.getName() + " - " + inventory.getPrice());

            getOnlineStore.add(inventory);

            line = reader.readLine();
        }
        reader.close();
        System.out.println();

        while (true) {
            int command;
            do {
                command = displayHomeScreen();
                switch (command) {
                    case 1:
                        showAllProducts();
                        break;
                    case 2:
                        showCart();
                        break;
                    case 0:
                        System.out.println("Thank you for shopping. Goodbye!");
                        break;
                    default:
                        System.out.println("Invalid option. Please try again.");
                        break;
                }
            } while (true);
        }

    }

    public static int displayHomeScreen() {
        System.out.println();
        System.out.println("What would you like to do?");
        System.out.println(" 1 - Show Products");
        System.out.println(" 2 - Show cart");
        System.out.println(" 0 - Exit Program");
        System.out.print("Enter your option: ");

        int command = scanner.nextInt();
        return command;
    }

    public static void showAllProducts() {

        for (Inventory inventory : getOnlineStore) {
            System.out.println(inventory.getId() + " - " + inventory.getName() + " - " + inventory.getPrice());
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
                    return;
                default:
                    System.out.println("Invalid option. Please try again.");
                    return;
            }
        } while (true);

    }

    public static void addToCart() {
        System.out.print("Enter the ID of the product you would like to add to your cart: ");
        String productID = scanner.next();

        for (Inventory inventory : getOnlineStore) {
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


    public static void showCart() {
        System.out.println("Items in cart");
        System.out.println("-----------------");

        double total = 0;

        for (Inventory inventory : cart.keySet()) {
            int quantity = cart.get(inventory);
            double price = inventory.getPrice();
            double subtotal = price * quantity;
            System.out.println(inventory.getName() + " - " + quantity + " x " + inventory.getPrice() + " = $" + subtotal);
            total += subtotal;
        }
        System.out.println("Total: $" + total);
    }
}

