package main.model;

import main.model.exceptions.EmptyStringException;

public class CategoryTag {

    private String name;

    // MODIFIES: this
    // EFFECTS: creates a Category Tag with the given name
    //          throws EmptyStringException if name is empty

    public CategoryTag(String name) throws EmptyStringException {
        if (name == null || name.isEmpty()) {
            throw new EmptyStringException("Category tag name empty");
        }
        this.name = name;
    }

    // EFFECTS: returns the name of this Category tag
    public String getName() {
        return name;
    }

    // EFFECTS: returns the category tag name with # in front of each tag
    @Override
    public String toString() {
        return "#" + name;
    }

}
