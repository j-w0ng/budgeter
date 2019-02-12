package main.model.goals;

import main.model.Status;
import main.model.Transactions;
import main.model.cashflow.CashFlowIn;
import main.model.exceptions.NegativeAmountException;

import java.util.Date;
import java.util.HashSet;

public class SavingGoals extends Transactions {

    private String description;
    private PurchaseByDate purchaseByDate;
    private double amountContributed;
    private PriorityLevel priorityLevel;
    private int progress;

    // MODIFIES: this
    // EFFECTS: constructs a SavingGoals with default values
    public SavingGoals() {
        tags = new HashSet<>();
        purchaseByDate = new PurchaseByDate();
        amount = 0;
        amountContributed = 0;
        status = Status.TO_PURCHASE;
        priorityLevel = new PriorityLevel(false, false);
    }

    public void contribution(double amount) {
        if (amount < 0) {
            throw new NegativeAmountException();
        }
        if (amount + amountContributed > amount) {
            amountContributed = amount;
            CashFlowIn newIncome = new CashFlowIn();
            newIncome.setAmount(amount + amountContributed - amount);
        }
        amountContributed += amount;
        progress = (int) Math.round(amountContributed * 100/amount);
    }

    // MODIFIES: this
    // EFFECTS: sets description of SavingGoal
    public void setDescription(String description) {
        this.description = description;
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

    // EFFECTS: gets priorityLevel of SavingGoal
    public PriorityLevel getPriorityLevel() {
        return priorityLevel;
    }

    // EFFECTS: gets progress of SavingGoal
    public int getProgress() {
        return progress;
    }

}
