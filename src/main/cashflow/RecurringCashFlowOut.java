package main.cashflow;

import main.Status;
import main.Transactions;

import java.util.HashSet;

public class RecurringCashFlowOut extends Transactions {

    // MODIFIES: this
    // EFFECTS: constructs a RecurringCashFlowOut with default values
    public RecurringCashFlowOut(String description, double amount) {
        super(description);
        tags = new HashSet<>();
        this.amount = amount;
        status = Status.RECURRING_BILLS;
    }

    @Override
    public String toString() {
        return "Description: " + description + '\n' +
                "Amount: " + amount + "\n" +
                "Tags: " + tags;
    }

}
