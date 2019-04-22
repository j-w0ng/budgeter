package tests.model;

import main.Status;
import main.exceptions.NegativeAmountException;
import main.goals.SavingGoals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Calendar;

import static org.junit.jupiter.api.Assertions.*;

public class SavingGoalsTests {

    SavingGoals savingGoals;

    @BeforeEach
    void setup() {
        String description = "Motorcycle";
        double amount = 123.45;
        savingGoals = new SavingGoals(description, amount);
    }

    @Test
    void testConstructor() {
        Calendar today = Calendar.getInstance();
        today.set(Calendar.MONTH, today.get(Calendar.MONTH) + 1);
        assertEquals("Motorcycle", savingGoals.getDescription());
        assertEquals(123.45, savingGoals.getAmount());
        assertEquals(Status.TO_PURCHASE, savingGoals.getStatus());
        assertTrue(savingGoals.getTags().isEmpty());
        assertEquals(today.getTime().getTime(),
                savingGoals.getPurchaseByDate().getPurchaseByDate().getTime(),
                1000);
        assertEquals(0, savingGoals.getProgress());
        assertEquals(0, savingGoals.getAmountContributed());
    }

    @Test
    void testContribution() {
        try {
            savingGoals.contribution(100);
            assertEquals(100, savingGoals.getAmountContributed());
            assertEquals(81, savingGoals.getProgress());
        } catch (NegativeAmountException e) {
            fail("Threw Unnecessary NegativeAmountException");
        }
    }

    @Test
    void testNegativeContribution() {
        try {
            savingGoals.contribution(-100);
            fail("Did not threw NegativeAmountException");
        } catch (NegativeAmountException e) {
            System.out.println("Test Passed");
        }
    }

    @Test
    void testOverContribution() {
        try {
            savingGoals.contribution(100);
            savingGoals.contribution(24.45);
            assertEquals(123.45, savingGoals.getAmountContributed());
            assertEquals(100, savingGoals.getProgress());
        } catch (NegativeAmountException e) {
            fail("Threw Unnecessary NegativeAmountException");
        }
    }

}
