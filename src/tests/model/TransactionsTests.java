package tests.model;

import main.Status;
import main.Transactions;
import main.exceptions.EmptyStringException;
import main.exceptions.NegativeAmountException;
import main.exceptions.NullArgumentException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TransactionsTests {

    Transactions transactions;

    @BeforeEach
    void setup() {
        transactions = new Transactions("test");
    }

    @Test
    void testConstructor() {
        assertTrue(transactions.getTags().isEmpty());
        assertEquals(0, transactions.getAmount());
        assertEquals(Status.TO_PURCHASE, transactions.getStatus());
    }

    @Test
    void testAddTag() {
        try {
            transactions.addTag("tag1");
            transactions.addTag("tag2");
            assertTrue(transactions.containsTag("tag1"));
            assertTrue(transactions.containsTag("tag2"));
            assertEquals(2, transactions.getTags().size());
        } catch (EmptyStringException e) {
            fail("Threw Unnecessary EmptyStringException");
        }
    }

    @Test
    void testAddTagThrowEmptyStringException() {
        try {
            transactions.addTag("tag1");
            transactions.addTag("");
            fail("Did Not Throw EmptyStringException");
        } catch (EmptyStringException e) {
            System.out.println("Test Passed");
        }
    }

    @Test
    void testRemoveTag() {
        try {
            transactions.addTag("tag1");
            transactions.addTag("tag2");
            transactions.removeTag("tag1");
            assertFalse(transactions.containsTag("tag1"));
            assertTrue(transactions.containsTag("tag2"));
            assertEquals(1, transactions.getTags().size());
        } catch (EmptyStringException e) {
            fail("Threw Unnecessary EmptyStringException");
        }
    }

    @Test
    void testRemoveAndAddTag() {
        try {
            transactions.addTag("tag1");
            transactions.addTag("tag2");
            transactions.removeTag("tag1");
            transactions.addTag("tag3");
            assertFalse(transactions.containsTag("tag1"));
            assertTrue(transactions.containsTag("tag2"));
            assertTrue(transactions.containsTag("tag3"));
            assertEquals(2, transactions.getTags().size());
        } catch (EmptyStringException e) {
            fail("Threw Unnecessary EmptyStringException");
        }
    }

    @Test
    void testRemoveTagThrowEmptyStringException() {
        try {
            transactions.addTag("tag1");
            transactions.removeTag("");
            fail("Did Not Throw EmptyStringException");
        } catch (EmptyStringException e) {
            System.out.println("Test Passed");
        }
    }

    @Test
    void testSetStatus() {
        try {
            transactions.setStatus(Status.RECURRING_BILLS);
            assertEquals(Status.RECURRING_BILLS, transactions.getStatus());
        } catch (NullArgumentException e) {
            fail("Threw Unnecessary NullArgumentException");
        }
    }

    @Test
    void testSetStatusThrowNullArgumentException() {
        try {
            transactions.setStatus(null);
            fail("Did not throw NullArgumentException");
        } catch (NullArgumentException e) {
            System.out.println("Test Passed");
        }
    }

    @Test
    void testSetAmount() {
        try {
            transactions.setAmount(2.35);
            assertEquals(2.35, transactions.getAmount());
        } catch (NullArgumentException e) {
            fail("Threw Unnecessary NegativeAmountException");
        }
    }

    @Test
    void testSetAmountThrowNegativeAmountException() {
        try {
            transactions.setAmount(-2);
            fail("Did not throw NegativeAmountException");
        } catch (NegativeAmountException e) {
            System.out.println("Test Passed");
        }
    }



}
