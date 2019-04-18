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
            purchasesParser = new PurchasesParser("/Users/jonathan/Downloads/December2018_6160copy.csv",
                                                    this);
        } catch (FileNotFoundException e) {
            System.out.println("File not Found");
        }
    }

    public void setNewCategory(String string, ArrayList<Double> arrayList) {
        purchases.put(string, arrayList);
    }

    public ArrayList<Double> getCategoryValues(String string) {
        return purchases.get(string);
    }

    public boolean doesCategoryExist(String string) {
        return purchases.containsKey(string);
    }

    public double CategoryTotal(String string) {
        ArrayList<Double> amounts = purchases.get(string);
        double total = 0;
        for (Double d: amounts) {
            total += d;
        }
        return Math.round(total*100.0)/100.0;
    }
}
