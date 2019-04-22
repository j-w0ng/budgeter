package tests.model;

import main.Status;
import main.Transactions;
import main.cashflow.RecurringCashFlowOut;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RecurringCashFlowOutTests {

    Transactions bills;

    @Test
    void testConstructor() {
        bills = new RecurringCashFlowOut("cable", 20);
        assertEquals(20, bills.getAmount());
        assertEquals(Status.RECURRING_BILLS, bills.getStatus());
    }

}

