package tests.model;

import main.Transactions;
import main.Status;

import main.cashflow.CashFlowIn;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CashFlowInTests {

    Transactions income;

    @Test
    void testConstructor() {
        income = new CashFlowIn("Income",0);
        assertEquals(0, income.getAmount());
        assertEquals(Status.INCOME, income.getStatus());
        assertTrue(income.containsTag("Income"));
    }

}
