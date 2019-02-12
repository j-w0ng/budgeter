package main.model.cashflow;

import main.model.CategoryTag;
import main.model.Status;
import main.model.Transactions;

import java.util.HashSet;

public class CashFlowIn extends Transactions {

    // MODIFIES: this
    // EFFECTS: constructs a CashFlowIn with default values
    public CashFlowIn() {
        tags = new HashSet<>();
        amount = 0;
        status = Status.INCOME;
        CategoryTag incomeTag = new CategoryTag("Income");
        tags.add(incomeTag);
    }


}
