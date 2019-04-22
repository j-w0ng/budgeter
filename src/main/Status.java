package main;

public enum Status {
    INCOME("Income"),
    RECURRING_BILLS("Recurring Bills"),
    CREDITCARD_BILL("CreditCard Bill"),
    TO_PURCHASE("To Purchase"),
    PURCHASED("Purchased");

    private String description;

    // EFFECTS: sets description of Status
    Status(String description) {
        this.description = description;
    }

    // EFFECTS: returns description of Status
    public String getDescription() {
        return description;
    }

    // EFFECTS: returns description of Status
    @Override
    public String toString() {
        return description;
    }
}
