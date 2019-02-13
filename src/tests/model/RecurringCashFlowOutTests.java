package tests.model;

import main.model.Status;
import main.model.Transactions;
import main.model.cashflow.RecurringCashFlowOut;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RecurringCashFlowOutTests {

    Transactions bills;

    @Test
    void testConstructor() {
        bills = new RecurringCashFlowOut();
        assertEquals(0, bills.getAmount());
        assertEquals(Status.RECURRING_BILLS, bills.getStatus());
    }

}

