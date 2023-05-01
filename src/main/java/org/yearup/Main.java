package org.yearup;


public class Main {

    //main class, run program
    public static void main(String[] args) throws Exception
    {
        System.out.println("Hello Shopper!");
        System.out.println();

        OnlineStoreApp app = new OnlineStoreApp();
        OnlineStoreApp.run();
    }
}

