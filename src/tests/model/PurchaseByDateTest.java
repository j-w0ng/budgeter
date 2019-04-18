package tests.model;

import main.model.exceptions.NullArgumentException;
import main.model.goals.PurchaseByDate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Calendar;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

public class PurchaseByDateTest {

    PurchaseByDate purchaseByDate;
    Calendar today;

    @BeforeEach
    void setup() {
        today = Calendar.getInstance();
        purchaseByDate = new PurchaseByDate();
    }


    @Test
    void testConstructor() {
        today.set(Calendar.MONTH, today.get(Calendar.MONTH) + 1);
        assertEquals(today.getTime().getTime(), purchaseByDate.getPurchaseByDate().getTime(), 1000);
    }

    @Test
    void testConstructorWithDateInput() {
        try {
            today.set(Calendar.DATE, Calendar.DATE + 14);
            purchaseByDate = new PurchaseByDate(today.getTime());
            assertEquals(today.getTime().getTime(), purchaseByDate.getPurchaseByDate().getTime(), 1000);
        } catch (NullArgumentException e) {
            fail("Threw Unnecessary NullArgumentException");
        }
    }

    @Test
    void testConstructorWithNullDateInput() {
        try {
            Date date = null;
            purchaseByDate = new PurchaseByDate(date);
            fail("Did not throw NullArgumentException");
        } catch (NullArgumentException e) {
            System.out.println("Test Passed");
        }
    }


    @Test
    void testSetPurchaseByDateWithNullDateInput() {
        try {
            Date date = null;
            purchaseByDate.setPurchaseByDate(date);
            fail("Did not throw NullArgumentException");
        } catch (NullArgumentException e) {
            System.out.println("Test Passed");
        }
    }

    @Test
    void testSetPurchaseByDate() {
        try {
            today.set(Calendar.DATE, Calendar.DATE + 14);
            purchaseByDate.setPurchaseByDate(today.getTime());
            assertEquals(today.getTime().getTime(), purchaseByDate.getPurchaseByDate().getTime(), 1000);
        } catch (NullArgumentException e) {
            fail("Threw Unnecessary NullArgumentException");
        }
    }

    @Test
    void testPostponeOneMonth() {
        purchaseByDate.postponeOneMonth();
        today.set(Calendar.MONTH, today.get(Calendar.MONTH) + 2);
        assertEquals(today.getTime().getTime(), purchaseByDate.getPurchaseByDate().getTime(), 1000);
    }

    @Test
    void testPostponeOneYear() {
        purchaseByDate.postponeOneYear();
        today.set(Calendar.MONTH, today.get(Calendar.MONTH) + 13);
        assertEquals(today.getTime().getTime(), purchaseByDate.getPurchaseByDate().getTime(), 1000);
    }

    @Test
    void testisOverDue() {
        today.set(Calendar.MONTH, Calendar.MONTH - 1);
        purchaseByDate.setPurchaseByDate(today.getTime());
        assertTrue(purchaseByDate.isOverdue());
    }

    @Test
    void testisOverDueNotOverDue() {
        today.set(Calendar.DAY_OF_MONTH, Calendar.DAY_OF_MONTH + 14);
        purchaseByDate.setPurchaseByDate(today.getTime());
        assertFalse(purchaseByDate.isOverdue());
    }

    @Test
    void testPurchaseByDateLessThanHalfYearAway() {
        for (int i = 0; i < 5; i++) {
            purchaseByDate.postponeOneMonth();
            assertTrue(purchaseByDate.purchaseByDateLessThanHalfYearAway());
        }
        purchaseByDate.postponeOneMonth();
        assertFalse(purchaseByDate.purchaseByDateLessThanHalfYearAway());
    }

    @Test
    void testPurchaseByDateLessThanThreeMonthsAway() {
        for (int i = 0; i < 2; i++) {
            purchaseByDate.postponeOneMonth();
            assertTrue(purchaseByDate.purchaseByDateLessThanThreeMonthsAway());
        }
        purchaseByDate.postponeOneMonth();
        assertFalse(purchaseByDate.purchaseByDateLessThanThreeMonthsAway());
    }

    @Test
    void testToString() {
        today.set(Calendar.MONTH, today.get(Calendar.MONTH) + 1);
        String firstPart = today.getTime().toString().substring(0, 10);
        String secondPart = today.getTime().toString().substring(24);
        assertEquals(firstPart + " " + secondPart, purchaseByDate.toString());
    }
}
