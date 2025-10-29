package seedu.address.model.meeting;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.logic.parser.exceptions.ParseException;

public class MeetingNameTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new MeetingName(null));
    }

    @Test
    public void constructor_invalidName_throwsIllegalArgumentException() {
        String invalidName = "";
        assertThrows(IllegalArgumentException.class, () -> new MeetingName(invalidName));

        String anotherInvalidMeetingName = "some very very very very very very very very very very very very very very"
                + " ver long meeting name";
        assertThrows(IllegalArgumentException.class, () -> new Venue(anotherInvalidMeetingName));
    }

    @Test
    public void isValidName() {
        // null name
        assertThrows(NullPointerException.class, () -> MeetingName.isValidMeetingName(null));

        // invalid name
        try {
            assertFalse(MeetingName.isValidMeetingName("")); // empty string
            assertFalse(MeetingName.isValidMeetingName(" ")); // spaces only

            // only non-alphanumeric characters
            assertFalse(MeetingName.isValidMeetingName("^"));

            // contains non-alphanumeric characters
            assertFalse(MeetingName.isValidMeetingName("peter*"));

            // boundary value (96 characters)
            assertThrows(ParseException.class, () -> MeetingName.isValidMeetingName(
                    "some very very very very very very very very very very very very very very ver"
                            + " long meeting name"));

            // valid name
            assertTrue(MeetingName.isValidMeetingName("financial sharing")); // alphabets only
            assertTrue(MeetingName.isValidMeetingName("12345")); // numbers only
            assertTrue(MeetingName.isValidMeetingName("financial sharing session 2")); // alphanumeric characters
            assertTrue(MeetingName.isValidMeetingName("Financial Sharing Session 2")); // with capital letters

            // long names
            assertTrue(MeetingName.isValidMeetingName("Financial Sharing with Some Company Session 3"));

            // boundary value (95 characters)
            assertTrue(MeetingName.isValidMeetingName(
                    "some very very very very very very very very very very very very very very ve"
                    + " long meeting name"));
        } catch (ParseException e) {
            // This block should not be reached
            fail();
        }

    }

    @Test
    public void equals() {
        MeetingName name = new MeetingName("Valid Name");

        // same values -> returns true
        assertTrue(name.equals(new MeetingName("Valid Name")));

        // same object -> returns true
        assertTrue(name.equals(name));

        // null -> returns false
        assertFalse(name.equals(null));

        // different types -> returns false
        assertFalse(name.equals(5.0f));

        // different values -> returns false
        assertFalse(name.equals(new MeetingName("Other Valid Name")));
    }
}
