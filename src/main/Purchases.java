package main;

import main.parsers.PurchasesParser;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

public class Purchases extends Transactions {

    private HashMap<String, ArrayList<Double>> purchases;


    public Purchases(String fileLocation, String description) {
        super(description);
        purchases = new HashMap<>();
        status = Status.CREDITCARD_BILL;
        CategoryTag tag = new CategoryTag("MBNA");
        tags.add(tag);

        try {
            PurchasesParser purchasesParser = new PurchasesParser(fileLocation, this);
            amount = totalBill();
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

    private double totalBill() {
        double totalAmount = 0;
        Collection<ArrayList<Double>> amounts = purchases.values();
        for (ArrayList<Double> a: amounts) {
            for (Double d: a) {
                totalAmount += d;
            }
        }
        return Math.round(totalAmount*100.0)/100.0;
    }
}
