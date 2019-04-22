package main;

import main.exceptions.NegativeAmountException;
import main.exceptions.EmptyStringException;
import main.exceptions.NullArgumentException;

import java.util.Collections;
import java.util.Set;
import java.util.HashSet;

// Represents past or future transactions with a status, amount, set of tags.

public class Transactions {

    protected String description;
    protected Set<CategoryTag> tags;
    protected double amount;
    protected Status status;

    // MODIFIES: this
    // EFFECTS: constructs a Transaction with default values
    public Transactions(String description) {
        tags = new HashSet<>();
        amount = 0;
        status = Status.TO_PURCHASE;
        this.description = description;
    }


    // MODIFIES: this
    // EFFECTS: creates a CategoryTag with name tagName and adds it to this Transaction
    //          throws EmptyStringException if name is empty
    public void addTag(String tagName) throws EmptyStringException {
        if (tagName == null || tagName.isEmpty()) {
            throw new EmptyStringException("Transaction, did not provide CategoryTag tagName");
        }
        boolean toAdd = true;
        for (CategoryTag t : tags) {
            if (t.getName().equalsIgnoreCase(tagName)) {
                toAdd = false;
            }
        }
        if (toAdd) {
            CategoryTag newTag = new CategoryTag(tagName);
            tags.add(newTag);
        }
    }

    // MODIFIES: this
    // EFFECTS: removes the CategoryTag with tagName from this Transaction
    //          throws EmptyStringException if name is empty
    public void removeTag(String tagName) throws EmptyStringException {
        if (tagName == null || tagName.isEmpty()) {
            throw new EmptyStringException("Transaction did not provide CategoryTag tagName");
        }
        if (!tags.isEmpty()) {
            for (CategoryTag t : tags) {
                if (t.getName().equals(tagName)) {
                    tags.remove(t);
                    break;
                }
            }
        }
    }

    // EFFECTS: returns an unmodifiable set of tags
    public Set<CategoryTag> getTags() {
        return Collections.unmodifiableSet(tags);
    }


    // EFFECTS: returns the status of this Transaction
    public Status getStatus() {
        return status;
    }

    // EFFECTS: returns the amount of this Transaction
    public double getAmount() {
        return amount;
    }

    // MODIFIES: this
    // EFFECTS: sets the status of this task
    //          throws NullArgumentException if status is null
    public void setStatus(Status status) throws NullArgumentException {
        if (status == null) {
            throw new NullArgumentException("Task status null");
        }
        this.status = status;
    }

    // MODIFIES: this
    // EFFECTS: sets the amount for this Transaction, rounds amount to two decimal places
    //          throws NegativeAmountException if amount is less than zero
    public void setAmount(double amount) throws NegativeAmountException {
        if (amount < 0) {
            throw new NegativeAmountException("Amount is Negative");
        }
        this.amount = (double) Math.round(amount * 100) / 100;
    }


    // EFFECTS: returns true if Transaction contains a CategoryTag with tagName, false otherwise
    public boolean containsTag(String tagName) {
        boolean containsTag = false;
        for (CategoryTag t : tags) {
            if (t.getName().equals(tagName)) {
                containsTag = true;
            }
        }
        return containsTag;
    }

    public String getDescription() {
        return description;
    }

}
