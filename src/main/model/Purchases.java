package main.model;

import main.model.parsers.ParsingException.PurchasesParser;

import java.io.FileNotFoundException;
import java.util.ArrayList;

public class Purchases extends Transactions {

    private ArrayList<Purchases> currentMonthPurchases;
    private PurchasesParser purchasesParser;

    public Purchases() {
        try {
            purchasesParser = new PurchasesParser("/Users/jonathan/Downloads/December2018_6160.csv",
                                                    this);
        } catch (FileNotFoundException e) {
            System.out.println("File not Found");
        }
    }


}
