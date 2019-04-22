package main.parsers;

import main.Purchases;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class PurchasesParser {

    ArrayList<Double> totalsForEachCategory;

// fileLocation = "/Users/jonathan/Downloads/December2018_6160copy.csv"

    public PurchasesParser(String fileLocation, Purchases purchases) throws FileNotFoundException {
        Scanner currentBill = new Scanner(new File(fileLocation));
        currentBill.useDelimiter(",");
        currentBill.nextLine();
        while (currentBill.hasNext()) {
            currentBill.next();
            String locationCategory = determineLocation(currentBill.next());
            currentBill.next();
            String amount = currentBill.next();
            currentBill.nextLine();
            addAmountIntoCategory(locationCategory, purchases, amount);
//            System.out.print (currentBill.next() + "|");
        }
        currentBill.close();
    }

    private String determineLocation(String location) {
        if (location.contains("COSTCO"))
            return "COSTCO";
        if (location.contains("PRICESMART")
            || location.contains("SUPERMARKET")
            || location.contains("WAL-MART")
            || location.contains("SUPERSTORE"))
            return "GROCERY";
        if (location.contains("TOWN PANTRY"))
            return "GAS";
        if (location.contains("RAMEN")
            || location.contains("FOOD")
            || location.contains("CHOCOLATES")
            || location.contains("SUSHI")
            || location.contains("FRIED"))
            return "FOOD";
        if (location.contains("PAYBYPHONE"))
            return "PARKING";
        if (location.contains("PAYMENT"))
            return "PAYMENT";
        return "MISC";
    }

    private void addAmountIntoCategory(String s, Purchases p, String amount) {
        if (!s.equals("PAYMENT")) {
            if (p.doesCategoryExist(s)) {
                p.getCategoryValues(s).add(Double.valueOf(amount));
            } else {
                p.setNewCategory(s, new ArrayList<Double>());
                p.getCategoryValues(s).add(Double.valueOf(amount));
            }
        }
    }


}
