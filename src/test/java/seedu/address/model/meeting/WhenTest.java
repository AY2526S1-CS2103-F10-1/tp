package seedu.address.model.meeting;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static seedu.address.testutil.Assert.assertThrows;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

import seedu.address.logic.parser.exceptions.ParseException;

public class WhenTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new When((LocalDateTime) null));
    }

    @Test
    public void constructor_invalidWhen_throwsIllegalArgumentException() {
        String invalidName = "";
        assertThrows(IllegalArgumentException.class, () -> new MeetingName(invalidName));
    }

    @Test
    public void isValidWhen() {
        try {
            // invalid name
            assertThrows(ParseException.class, () -> When.isValidWhen("")); // empty string
            assertThrows(ParseException.class, () -> When.isValidWhen(" ")); // spaces only
            assertThrows(ParseException.class, () -> When.isValidWhen("^")); // only non-alphanumeric characters

            // contains non-alphanumeric characters
            assertThrows(ParseException.class, () -> When.isValidWhen("peter*"));

            // valid date
            assertTrue(When.isValidWhen("12-01-2025 1400"));
            assertTrue(When.isValidWhen("2025-01-12 14:00"));
            assertTrue(When.isValidWhen("2025-01-12 1400"));
            assertTrue(When.isValidWhen("2025/01/12 14:00"));
            assertTrue(When.isValidWhen("2025/01/12 1400"));
            assertTrue(When.isValidWhen("12/01/2025 14:00"));
            assertTrue(When.isValidWhen("12/01/2025 1400"));
            assertTrue(When.isValidWhen("12-01-2025 14:00"));
        } catch (ParseException ignored) {
            fail();
        }
    }

    @Test
    public void equals() throws ParseException {
        When when = new When("12-01-2025 14:00");

        // same values -> returns true
        assertTrue(when.equals(new When("12-01-2025 14:00")));

        // same object -> returns true
        assertTrue(when.equals(when));

        // null -> returns false
        assertFalse(when.equals(null));

        // different types -> returns false
        assertFalse(when.equals(5.0f));

        // different values -> returns false
        assertFalse(when.equals(new When("12-12-2025 14:00")));
    }
}
