package seedu.address.model.meeting;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.logic.parser.exceptions.ParseException;

public class VenueTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Venue(null));
    }

    @Test
    public void constructor_invalidVenue_throwsIllegalArgumentException() {
        String invalidVenue = "";
        assertThrows(IllegalArgumentException.class, () -> new Venue(invalidVenue));

        String anotherInvalidVenue = "some very very very very very very very very very very very very very very very"
                + " very v long venue";
        assertThrows(IllegalArgumentException.class, () -> new Venue(anotherInvalidVenue));
    }

    @Test
    public void isValidVenue() {
        // null name
        assertThrows(NullPointerException.class, () -> Venue.isValidVenue(null));

        try {
            // invalid name
            assertFalse(Venue.isValidVenue("")); // empty string
            assertFalse(Venue.isValidVenue(" ")); // spaces only
            assertFalse(Venue.isValidVenue("^")); // only non-alphanumeric characters
            assertFalse(Venue.isValidVenue("%$")); // contains weird symbols
            // boundary value (96 characters)
            assertThrows(ParseException.class, () -> Venue.isValidVenue(
                    "some very very very very very very very very very very very very very very very very v long"
                            + " venue"));

            // valid name
            assertTrue(Venue.isValidVenue("amk hub")); // alphabets only
            assertTrue(Venue.isValidVenue("12345")); // numbers only
            assertTrue(Venue.isValidVenue("amk hub level 2")); // alphanumeric characters
            assertTrue(Venue.isValidVenue("AMK Hub Level 2")); // with capital letters
            assertTrue(Venue.isValidVenue("AMK Hub Starbucks located at Basement Level B1")); // long names

            // boundary value (95 characters)
            assertTrue(Venue.isValidVenue(
                    "some very very very very very very very very very very very very very very very very long"
                            + " venue"));
        } catch (ParseException pe) {
            // This block should not reach
            fail();
        }

    }

    @Test
    public void equals() {
        Venue venue = new Venue("Valid Name");

        // same values -> returns true
        assertTrue(venue.equals(new Venue("Valid Name")));

        // same object -> returns true
        assertTrue(venue.equals(venue));

        // null -> returns false
        assertFalse(venue.equals(null));

        // different types -> returns false
        assertFalse(venue.equals(5.0f));

        // different values -> returns false
        assertFalse(venue.equals(new Venue("Other Valid Name")));
    }
}
