package main.model.parsers.ParsingException;

import main.model.Purchases;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class PurchasesParser {

// fileLocation = "/Users/jonathan/Downloads/December2018_6160.csv"

    public PurchasesParser(String fileLocation, Purchases purchases) throws FileNotFoundException {
        Scanner currentBill = new Scanner(new File(fileLocation));
        currentBill.useDelimiter(",");
        while (currentBill.hasNext()) {
            System.out.print (currentBill.next() + "|");
        }
        currentBill.close();
    }
}
