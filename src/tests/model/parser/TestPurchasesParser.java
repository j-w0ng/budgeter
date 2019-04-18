package tests.model.parser;

import main.model.Purchases;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestPurchasesParser {

    @Test
    void testParser() throws FileNotFoundException {
        Purchases purchases = new Purchases();
        assertEquals(-73.93, purchases.CategoryTotal("GAS"));
        assertEquals(-4.23, purchases.CategoryTotal("PARKING"));
        assertEquals(-162.76, purchases.CategoryTotal("COSTCO"));
        assertEquals(-168.47, purchases.CategoryTotal("FOOD"));
        assertEquals(-139.93, purchases.CategoryTotal("GROCERY"));
        assertEquals(-55.99, purchases.CategoryTotal("MISC"));
    }
}
