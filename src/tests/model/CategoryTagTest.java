package tests.model;

import main.model.CategoryTag;
import main.model.exceptions.EmptyStringException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CategoryTagTest {

    CategoryTag categoryTag;

    @BeforeEach
    void setup() {
        categoryTag = new CategoryTag("TestTag");
    }

    @Test
    void testConstructor() {
        try {
            assertEquals("TestTag", categoryTag.getName());
        } catch (EmptyStringException e) {
            fail("Threw Unnecessary EmptyStringException");
        }
    }

    @Test
    void testConstructorThrowEmptyStringException() {
        try {
            categoryTag = new CategoryTag("");
            fail("Did not throw EmptyStringException");
        } catch (EmptyStringException e) {
            System.out.println("Test Passed");
        }
    }

    @Test
    void testToString() {
        assertEquals("#TestTag", categoryTag.toString());
    }
}
