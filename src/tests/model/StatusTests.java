package tests.model;

import main.Status;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class StatusTests {

    Status status;

    @Test
    void testToString() {
        status = Status.PURCHASED;
        assertEquals("Purchased", status.toString());
    }

}
