package tests.persistence;

import main.Purchases;
import main.Transactions;
import main.persistence.GSON;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.List;

public class TestGSON {

    String fileLocation = "/Users/jonathan/Downloads/December2018_6160copy.csv";

    @Test
    void testConstructor() {
        List<Transactions> transactions = new LinkedList<>();
        Purchases p = new Purchases(fileLocation, "December Bill");
        transactions.add(p);
        GSON.write(transactions);
    }
}
