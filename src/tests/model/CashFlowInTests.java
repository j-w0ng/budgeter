package tests.model;

import main.model.Transactions;
import main.model.Status;

import main.model.cashflow.CashFlowIn;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CashFlowInTests {

    Transactions income;

    @Test
    void testConstructor() {
        income = new CashFlowIn();
        assertEquals(0, income.getAmount());
        assertEquals(Status.INCOME, income.getStatus());
        assertTrue(income.containsTag("Income"));
    }

}
