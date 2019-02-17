package main.model;

import main.model.parsers.ParsingException.PurchasesParser;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;

public class Purchases extends Transactions {

    private HashMap<String, ArrayList<Double>> purchases;
    private PurchasesParser purchasesParser;

    public Purchases() {

        purchases = new HashMap<>();

        try {
            purchasesParser = new PurchasesParser("/Users/jonathan/Downloads/December2018_6160.csv",
                                                    this);
        } catch (FileNotFoundException e) {
            System.out.println("File not Found");
        }
    }

    public void setNewCateogry(String string, ArrayList<Double> arrayList) {
        purchases.put(string, arrayList);
    }

    public void getCategoryValues(String string) {
        purchases.get(string);
    }

    private boolean doesCategoryExist(String string) {
        return purchases.containsKey(string);
    }
}
