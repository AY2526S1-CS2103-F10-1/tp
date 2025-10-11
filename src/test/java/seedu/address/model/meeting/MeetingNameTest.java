package seedu.address.model.meeting;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class MeetingNameTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new MeetingName(null));
    }

    @Test
    public void constructor_invalidName_throwsIllegalArgumentException() {
        String invalidName = "";
        assertThrows(IllegalArgumentException.class, () -> new MeetingName(invalidName));
    }

    @Test
    public void isValidName() {
        // null name
        assertThrows(NullPointerException.class, () -> MeetingName.isValidMeetingName(null));

        // invalid name
        assertFalse(MeetingName.isValidMeetingName("")); // empty string
        assertFalse(MeetingName.isValidMeetingName(" ")); // spaces only
        assertFalse(MeetingName.isValidMeetingName("^")); // only non-alphanumeric characters
        assertFalse(MeetingName.isValidMeetingName("peter*")); // contains non-alphanumeric characters

        // valid name
        assertTrue(MeetingName.isValidMeetingName("financial sharing")); // alphabets only
        assertTrue(MeetingName.isValidMeetingName("12345")); // numbers only
        assertTrue(MeetingName.isValidMeetingName("financial sharing session 2")); // alphanumeric characters
        assertTrue(MeetingName.isValidMeetingName("Financial Sharing Session 2")); // with capital letters
        assertTrue(MeetingName.isValidMeetingName("Financial Sharing with Some Company Session 3")); // long names
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
