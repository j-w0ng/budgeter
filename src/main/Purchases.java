package main;

import main.parsers.PurchasesParser;

import java.io.FileNotFoundException;
import java.util.*;

public class Purchases extends Transactions {

    private HashMap<String, ArrayList<Double>> purchases;


    public Purchases(String fileLocation, String description) throws FileNotFoundException  {
        super(description);
        purchases = new HashMap<>();
        status = Status.CREDITCARD_BILL;
        CategoryTag tag = new CategoryTag("MBNA");
        tags.add(tag);

        PurchasesParser purchasesParser = new PurchasesParser(fileLocation, this);
        amount = totalBill();

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

    public Set<String> getCategories() {
        return purchases.keySet();
    }

    public double CategoryTotal(String string) {
        ArrayList<Double> amounts = purchases.get(string);
        double total = 0;
        for (Double d: amounts) {
            total += d;
        }
        return Math.round(total*100.0)/100.0;
    }

    public double totalBill() {
        double totalAmount = 0;
        Collection<ArrayList<Double>> amounts = purchases.values();
        for (ArrayList<Double> a: amounts) {
            for (Double d: a) {
                totalAmount += d;
            }
        }
        return Math.round(totalAmount*100.0)/100.0;
    }

    @Override
    public String toString() {
        Set<String> categories = purchases.keySet();
        String output = "";
        for (String s: categories) {
            output = output.concat("\t" + s.toLowerCase() + ": $" + total(purchases.get(s)) * -1 + "\n");
        }
        return "Description: " + description + "\n" +
                "Category split this month: " + "\n" +
                output +
                "Total Amount: $" + amount * -1 + "\n" +
                "Tags: " + tags;
    }

    private double total(List<Double> d) {
        double total = 0;
        for (Double toAdd: d) {
            total += toAdd;
        }
        return Math.round(total*100.0)/100.0;
    }
}
