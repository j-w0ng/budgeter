package tests.parser;

import main.CategoryTag;
import main.Purchases;
import main.Status;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestPurchasesParser {

    String fileLocation = "/Users/jonathan/Downloads/December2018_6160copy.csv";

    @Test
    void testParser() throws FileNotFoundException {
        Purchases purchases = new Purchases(fileLocation, "December 2018");
        assertEquals(-73.93, purchases.CategoryTotal("GAS"));
        assertEquals(-4.23, purchases.CategoryTotal("PARKING"));
        assertEquals(-162.76, purchases.CategoryTotal("COSTCO"));
        assertEquals(-168.47, purchases.CategoryTotal("FOOD"));
        assertEquals(-139.93, purchases.CategoryTotal("GROCERY"));
        assertEquals(-55.99, purchases.CategoryTotal("MISC"));
        assertEquals(-605.31, purchases.getAmount());
        assertTrue(purchases.getTags().contains(new CategoryTag("MBNA")));
        assertEquals(Status.CREDITCARD_BILL, purchases.getStatus());
        System.out.println(purchases.toString());
    }
}
