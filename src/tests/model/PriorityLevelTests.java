package tests.model;

import main.model.goals.PriorityLevel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


public class PriorityLevelTests {

    PriorityLevel pl;

    @BeforeEach
    void setup() {
        pl = new PriorityLevel(false, false);
    }

    @Test
    void testConstructor() {
        assertFalse(pl.isEssential());
        assertFalse(pl.isImportant());
    }

    @Test
    void testSetters() {
        pl.setImportace(true);
        assertTrue(pl.isImportant());
        pl.setEssential(true);
        assertTrue(pl.isEssential());
    }

    @Test
    void testToString() {
        assertEquals("Not Essential or Important", pl.toString());
        pl.setEssential(true);
        assertEquals("Essential", pl.toString());
        pl.setImportace(true);
        assertEquals("Important & Essential", pl.toString());
        pl.setEssential(false);
        assertEquals("Important", pl.toString());
    }


}
