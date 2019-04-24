package tests.persistence;

import main.Purchases;
import main.Transactions;
import main.persistence.GSON;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.fail;

public class TestGSON {

    String fileLocation = "/Users/jonathan/Downloads/December2018_6160copy.csv";

    @Test
    void testConstructor() {
        List<Transactions> transactions = new LinkedList<>();
        try {
            Purchases p = new Purchases(fileLocation, "December Bill");
            transactions.add(p);
        } catch (FileNotFoundException e) {
            fail();
        }
        GSON.write(transactions);
    }
}
