package main.model.goals;

import main.model.exceptions.NullArgumentException;

import java.util.Calendar;
import java.util.Date;

import static java.util.Calendar.AM;
import static java.util.Calendar.PM;

public class PurchaseByDate {

    private Calendar purchaseBy;

    // MODIFIES: this
    // EFFECTS: creates a PurchaseByDate with deadline at end of day following month same date.

    public PurchaseByDate() {
        purchaseBy = Calendar.getInstance();
        purchaseBy.set(Calendar.MONTH, Calendar.MONTH + 1);
        purchaseBy.set(Calendar.HOUR, 11);
        purchaseBy.set(Calendar.MINUTE, 59);
        purchaseBy.set(Calendar.AM_PM, PM);
    }

    // MODIFIES: this
    // EFFECTS: creates a PurchaseByDate with deadline of the given date
    //          throws NullArgumentException if date is null
    public PurchaseByDate(Date date) throws NullArgumentException {
        if (date == null) {
            throw new NullArgumentException("Null Date Provided");
        }
        purchaseBy = Calendar.getInstance();
        purchaseBy.setTime(date);
    }

    // MODIFIES: this
    // EFFECTS: changes the PurchaseByDate to the given date
    //          throws NullArgumentException if date is null
    public void setPurchaseByDate(Date date) throws NullArgumentException {
        if (date == null) {
            throw new NullArgumentException("Null Date Provided");
        }
        purchaseBy.setTime(date);
    }


    // MODIFIES: this
    // EFFECTS: postpones the PurchaseByDate by one month
    //          (leave the time, date the same as in the original)
    public void postponeOneMonth() {
        purchaseBy.set(Calendar.MONTH, purchaseBy.get(Calendar.MONTH) + 1);
    }

    // MODIFIES: this
    // EFFECTS: postpones the PurchaseByDate by one year
    //          (leave the time, date, month the same as in the original)
    public void postponeOneYear() {
        purchaseBy.set(Calendar.YEAR, purchaseBy.get(Calendar.YEAR) + 1);
    }


    // EFFECTS: returns true if PurchaseByDate has passed
    public boolean isOverdue() {
        return purchaseBy.before(Calendar.getInstance());
    }


    // EFFECTS: returns true if PurchaseByDate is within the 6 months, irrespective of time of the day,
    // and false otherwise
    public boolean purchaseByDateLessThanHalfYearAway() {
        Calendar beginningTime = Calendar.getInstance();
        beginningTime.set(Calendar.HOUR, 00);
        beginningTime.set(Calendar.MINUTE, 00);
        beginningTime.set(Calendar.AM_PM, AM);
        Calendar endTime = Calendar.getInstance();
        int sixMonthsLater = beginningTime.get(Calendar.MONTH) + 6;
        endTime.set(Calendar.YEAR, sixMonthsLater, Calendar.DATE);
        endTime.set(Calendar.HOUR, 12);
        endTime.set(Calendar.MINUTE, 59);
        endTime.set(Calendar.AM_PM, PM);
        return !purchaseBy.before(beginningTime) && !purchaseBy.after(endTime);
    }

    // EFFECTS: returns true if PurchaseByDate is within the 3 months, irrespective of time of the day,
    // and false otherwise
    public boolean purchaseByDateLessThanThreeMonthsAway() {
        Calendar beginningTime = Calendar.getInstance();
        beginningTime.set(Calendar.HOUR, 00);
        beginningTime.set(Calendar.MINUTE, 00);
        beginningTime.set(Calendar.AM_PM, AM);
        Calendar endTime = Calendar.getInstance();
        int threeMonthsLater = beginningTime.get(Calendar.MONTH) + 3;
        endTime.set(Calendar.YEAR, threeMonthsLater, Calendar.DATE);
        endTime.set(Calendar.HOUR, 12);
        endTime.set(Calendar.MINUTE, 59);
        endTime.set(Calendar.AM_PM, PM);
        return !purchaseBy.before(beginningTime) && !purchaseBy.after(endTime);
    }

    // EFFECTS: returns a string representation of PurchaseByDate in the following format
    //     day-of-week month day year
    @Override
    public String toString() {
        String firstPart = purchaseBy.getTime().toString().substring(0, 10);
        String secondPart = purchaseBy.getTime().toString().substring(24);

        return firstPart + " " + secondPart;

    }

    // EFFECTS: returns the purchaseByDate
    public Date getPurchaseByDate() {
        return purchaseBy.getTime();
    }

}

