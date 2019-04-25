package main.goals;

import main.exceptions.NegativeAmountException;
import main.Status;
import main.Transactions;
import main.cashflow.CashFlowIn;

import java.util.Date;
import java.util.HashSet;

public class SavingGoals extends Transactions {

    private PurchaseByDate purchaseByDate;
    private double amountContributed;
    private double amountLeft;
    private PriorityLevel priorityLevel;
    private int progress;

    // MODIFIES: this
    // EFFECTS: constructs a SavingGoals with default values
    public SavingGoals(String description, double amount) {
        super(description);
        tags = new HashSet<>();
        purchaseByDate = new PurchaseByDate();
        this.amount = amount;
        this.amountLeft = amount;
        amountContributed = 0;
        status = Status.TO_PURCHASE;
        priorityLevel = new PriorityLevel(false, false);
    }

    // MODIFIES: this
    // EFFECTS: adds amount to amountContributed
    //          throws NegativeAmountException if amount < 0
    public void contribution(double amount) {
        if (amount < 0) {
            throw new NegativeAmountException();
        }
        if (amount + amountContributed > this.amount) {
            CashFlowIn newIncome = new CashFlowIn("Over-contribution", 0);
            newIncome.setAmount(amount + amountContributed - this.amount);
            status = Status.PURCHASED;
            amountContributed = this.amount;
            amountLeft = this.amount - amountContributed;
            progress = (int) Math.round(amountContributed * 100 / this.amount);
        } else {
            amountContributed += amount;
            amountLeft = this.amount - amountContributed;
            progress = (int) Math.round(amountContributed * 100 / this.amount);
        }
    }

    // MODIFIES: this
    // EFFECTS: sets purchaseByDate of SavingGoal
    public void setPurchaseByDate(Date date) {
        purchaseByDate.setPurchaseByDate(date);
    }

    // MODIFIES: this
    // EFFECTS: sets priorityLevel of SavingGoal
    public void setPriorityLevel(boolean essential, boolean importance) {
        priorityLevel.setEssential(essential);
        priorityLevel.setImportace(importance);
    }

    // EFFECTS: gets description of SavingGoal
    public String getDescription() {
        return description;
    }

    // EFFECTS: gets purchaseByDate of SavingGoal
    public PurchaseByDate getPurchaseByDate() {
        return purchaseByDate;
    }

    // EFFECTS: gets AmountLeft of SavingGoal
    public double getAmountLeft() {
        return amountLeft;
    }

    // EFFECTS: gets priorityLevel of SavingGoal
    public PriorityLevel getPriorityLevel() {
        return priorityLevel;
    }

    // EFFECTS: gets priorityLevel of SavingGoal
    public double getAmountContributed() {
        return amountContributed;
    }

    // EFFECTS: gets progress of SavingGoal
    public int getProgress() {
        return progress;
    }

    @Override
    public String toString() {
        return "Description: " + description + "\n" +
                "Purchase By:" + purchaseByDate + "\n" +
                "Amount Left: $" + amountLeft + "\n" +
                "Priority Level: " + priorityLevel + "\n" +
                "Progress: " + progress + "%" + "\n" +
                "Tags: " + tags;
    }
}
