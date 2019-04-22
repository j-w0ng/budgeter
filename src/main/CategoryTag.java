package main;

import main.exceptions.EmptyStringException;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CategoryTag)) return false;
        CategoryTag that = (CategoryTag) o;
        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
