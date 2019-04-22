package main.cashflow;

import main.Status;
import main.CategoryTag;
import main.Transactions;

import java.util.HashSet;

public class CashFlowIn extends Transactions {

    // MODIFIES: this
    // EFFECTS: constructs a CashFlowIn with default values
    public CashFlowIn() {
        super("Income");
        tags = new HashSet<>();
        amount = 0;
        status = Status.INCOME;
        CategoryTag incomeTag = new CategoryTag("Income");
        tags.add(incomeTag);
    }

    @Override
    public String toString() {
        return "Description: " + description + '\n' +
                "Amount: " + amount + "\n" +
                "Tags: " + tags;
    }
}
