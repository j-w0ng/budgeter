package main.model;

public enum Status {
    INCOME("Income"),
    RECURRING_BILLS("Recurring Bills"),
    TO_PURCHASE("To Purchase"),
    WORKING_TOWARDS("Working Towards"),
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
