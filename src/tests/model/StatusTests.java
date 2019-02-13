package tests.model;

import main.model.Status;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class StatusTests {

    Status status;

    @Test
    void testToString() {
        status = Status.WORKING_TOWARDS;
        assertEquals("Working Towards", status.toString());
    }

}
