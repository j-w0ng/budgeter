package main.model.goals;



public class PriorityLevel {

    private boolean essential;
    private boolean importance;

    // essential and important items include bills, insurance, etc.
    // essential items are items that are required, but not needed immediately.
    // important items are items that are required by a certain date.
    // all other items are not essential or important.
    // Note: important items have higher priority level than essential items.

    // MODIFIES: this
    // EFFECTS: construct a default PriorityLevel (not essential, not important)
    public PriorityLevel() {
        essential = false;
        importance = false;
    }

    // MODIFIES: this
    // EFFECTS: constructs a PriorityLevel for essential and importance
    public PriorityLevel(boolean essential, boolean importance) {
        this.essential = essential;
        this.importance = importance;

    }

    // EFFECTS: returns the importance of PriorityLevel
    public boolean isImportant() {
        return importance;
    }

    // MODIFIES: this
    // EFFECTS: updates the importance of PriorityLevel
    public void setimportace(boolean importance) {
        this.importance = importance;

    }

    // EFFECTS: returns whether of PriorityLevel is essential
    public boolean isEssential() {
        return essential;
    }

    // MODIFIES: this
    // EFFECTS: updates the essential of PriorityLevel
    public void setEssential(boolean essential) {
        this.essential = essential;
    }

    // EFFECTS: returns one of the four string representation of PriorityLevel
    @Override
    public String toString() {
        String priority;
        if (importance && essential) {
            priority = "Important & Essential";
        } else {
            if (importance) {
                priority = "Important";
            } else {
                if (essential) {
                    priority = "Essential";
                } else {
                    priority = "Not Essential or Important";
                }
            }
        }
        return priority;
    }
}
