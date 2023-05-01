package org.yearup;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Scanner;
import java.util.HashMap;

//class named, and imports established
public class OnlineStoreApp {
    static ArrayList<Inventory> getOnlineStore = new ArrayList<>();
    static HashMap<Inventory, Integer> cart = new HashMap<>();
    static Scanner scanner = new Scanner(System.in);

    //Use BufferedReader to read the access and read the file with the product inventory list
    public static void run() throws Exception
    {
        FileReader fileReader = new FileReader("inventory.csv");
        BufferedReader reader = new BufferedReader(fileReader);

        reader.readLine();

        String line = reader.readLine();
        while (line != null)
        {
            String[] columns = line.split("\\|");

            String id = columns[0];
            String name = columns[1];
            double price = Double.parseDouble(columns[2]);

            Inventory inventory = new Inventory(id, name, price);

            getOnlineStore.add(inventory);

            line = reader.readLine();
        }
        reader.close();

        while (true)
        {
            int command;
            do
            {
                command = displayHomeScreen();
                switch (command)
                {
                    case 1:
                        showAllProducts();
                        break;
                    case 2:
                        showCart();
                        break;
                    case 3:
                        checkOutCart();
                        break;
                    case 0:
                        System.out.println("Thank you for shopping. Have a great day!");
                        return;
                    default:
                        System.out.println("Invalid option. Please try again.");
                        break;
                }
            } while (command == 3);
        }
    }

    //create method to display home screen and initialize prompts to view other screens
    public static int displayHomeScreen()
    {
        int command;
            System.out.println("What would you like to do?");
            System.out.println(" 1 - Show Products");
            System.out.println(" 2 - Show cart");
            System.out.println(" 3 - Checkout cart");
            System.out.println(" 0 - Exit Store");
            System.out.print("Enter your option: ");


            command = scanner.nextInt();
            switch (command)
            {
                case 1:
                    showAllProducts();
                    break;
                case 2:
                    showCart();
                    break;
                case 3:
                    checkOutCart();
                    break;
                case 0:
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
                    break;
            }
        return command;

    }

    /*create method to show all products in the inventory.csv file and create option to add products to the cart and
    create prompt menu to view more screens*/
    public static void showAllProducts()
    {
        System.out.println("Product ID - Product Name - Price");
        System.out.println("------------------------------------");

        for (Inventory inventory : getOnlineStore)
        {
            System.out.println(inventory.getId() + " - " + inventory.getName() + " - $" + inventory.getPrice());
        }

        char option;
        do
        {
            System.out.println();
            System.out.println("What would you like to do?");
            System.out.println(" A - Add items to cart");
            System.out.println(" C - Show Cart");
            System.out.println(" X - Return to homepage");
            System.out.print("Enter your options: ");
            option = scanner.next().charAt(0);

            switch (option)
            {
                case 'A':
                case 'a':
                    addToCart();
                    break;
                case 'C':
                case 'c':
                    showCart();
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

    //created option to allow customer to add items to the cart
    public static void addToCart()
    {
        System.out.println();
        System.out.print("Enter the ID of the product you would like to add to your cart: ");
        String productID = scanner.next().toUpperCase(Locale.ROOT);

        for (Inventory inventory : getOnlineStore)
        {
            if (inventory.getId().equals(productID))
            {
                if (cart.containsKey(inventory)) {
                    int currentQuantity = cart.get(inventory);
                    cart.put(inventory, currentQuantity + 1);
                } else
                {
                    cart.put(inventory, 1);
                }
                System.out.println();
                System.out.println(inventory.getName() + " has been added to the cart.");
                return;
            }
        }
        System.out.println("Invalid product ID. Please try again.");
    }

    //create method to show cart with added items listed, prompts menu to allow user to navigate to other screens
    public static void showCart()
    {

        System.out.println();
        System.out.println("Items in cart");
        System.out.println("----------------");

        double total = 0;
        double roundedTotal = 0;

        for (Inventory inventory : cart.keySet())
        {
            int quantity = cart.get(inventory);
            double price = inventory.getPrice();
            double subtotal = price * quantity;
            System.out.println(inventory.getName() + " - $" + inventory.getPrice() + " x " + quantity + " = " + subtotal);
            roundedTotal += subtotal;
        }
        roundedTotal = Math.round(roundedTotal * 100) / 100.0;
        total = roundedTotal;
        System.out.println();
        System.out.println("Total: $" + total);

        char option;
        do
        {
            System.out.println();
            System.out.println("What would you like to do?");
            System.out.println(" C - Checkout cart");
            System.out.println(" R - Remove an item from cart");
            System.out.println(" E - Empty cart");
            System.out.println(" X - Go back to homepage");
            System.out.print("Enter your option: ");
            option = scanner.next().charAt(0);
            System.out.println();

            switch (option)
            {
                case 'C':
                case 'c':
                    checkOutCart();
                    break;
                case 'R':
                case 'r':
                    removeFromCart();
                    break;
                case 'E':
                case 'e':
                    emptyCart();
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

    //created method to allow user to remove item(s) from cart
    public static void removeFromCart()
    {

        System.out.print("Enter the ID of the item you want to remove: ");
        String itemId = scanner.next().toUpperCase(Locale.ROOT);

        System.out.println();

        Inventory itemToRemove = null;
        for (Inventory item : cart.keySet())
        {
            if (item.getId().equals(itemId))
            {
                itemToRemove = item;
                break;
            }
        }

        if (itemToRemove != null)
        {
            int currentQuantity = cart.get(itemToRemove);
            if (currentQuantity > 1)
            {
                cart.put(itemToRemove, currentQuantity - 1);
            } else
            {
                cart.remove(itemToRemove);
            }
            System.out.println(itemToRemove.getName() + " has been removed from the cart.");
            displayHomeScreen();
        } else
        {
            System.out.println("Invalid item ID. Please try again.");
        }
    }

    //created method to allow user to empty the cart and prompt menu created to allow user to navigate to other screens
    public static void emptyCart()
    {
        cart.clear();
        System.out.println("Cart has been emptied");
        System.out.println();

        char option;
        do
        {
            System.out.println("What would you like to do?");
            System.out.println(" X - Return to homepage");
            System.out.println(" Q - Exit store");
            System.out.println("Enter your option: ");
            option = scanner.next().charAt(0);

            switch (option)
            {
                case 'X':
                case 'x':
                    displayHomeScreen();
                    break;
                case 'Q':
                case 'q':
                    System.out.println("Thank you for shopping. Have a great day!");
                    System.exit(0);
                default:
                    System.out.println("Invalid option. Please try again.");
                    break;
            }

        }while (true);

    }

    /* created method to allow user to check out the items in the cart, the total price of the items are calculated
    * depending on the quantity and is rounded to the 100th decimal, the total is displayed to the user, and after
    * payment has been confirmed, change owed is calculated and given to the user*/
    public static void checkOutCart()
    {
        double total = 0;

        System.out.println();
        System.out.println("Items in cart");
        System.out.println("----------------");

        for (Inventory inventory : cart.keySet())
        {
            int quantity = cart.get(inventory);
            double price = inventory.getPrice();
            double subtotal = price * quantity;
            System.out.println(inventory.getName() + " - $" + inventory.getPrice() + " x " + quantity + " = " + subtotal);
            total += subtotal;
        }
        System.out.println("Total: $" + total);
        System.out.println();

        System.out.println("Please enter your payment amount: ");
        double payment = scanner.nextDouble();

        if (payment >= total)
        {
            double change = payment - total;
            double roundedChange = Math.round(change * 100) / 100.0;
            System.out.println("Your change is $" + roundedChange);
            System.out.println("Thank you for your purchase!");
            cart.clear();
        } else
        {
            System.out.println("Insufficient payment. Returning full amount.");
            showCart();
        }

        char option;
        do
        {
            System.out.println();
            System.out.println("What would you like to do?");
            System.out.println(" X - Return to homepage");
            System.out.println(" Q - Exit store");
            System.out.print("Enter your option: ");
            option = scanner.next().charAt(0);

            switch (option)
            {
                case 'X':
                case 'x':
                    displayHomeScreen();
                    break;
                case 'Q':
                case 'q':
                    System.out.println("Thank you for shopping. Have a great day!");
                    System.exit(0);
                default:
                    System.out.println("Invalid option. Please try again.");
                    break;
            }
        } while (true);
    }
}

