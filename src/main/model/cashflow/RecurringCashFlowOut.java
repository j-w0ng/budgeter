package main.model.cashflow;

import main.model.Status;
import main.model.Transactions;

import java.util.HashSet;

public class RecurringCashFlowOut extends Transactions {

    // MODIFIES: this
    // EFFECTS: constructs a RecurringCashFlowOut with default values
    public RecurringCashFlowOut() {
        tags = new HashSet<>();
        amount = 0;
        status = Status.RECURRING_BILLS;
    }


}
