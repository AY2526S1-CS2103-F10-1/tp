package seedu.address.model.meeting;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Represents a Meeting's venue.
 * Guarantees: immutable; is valid as declared in {@link #isValidVenue(String)}
 */
public class Venue {
    public static final String MESSAGE_CONSTRAINTS =
            "Venue should only contain alphanumeric characters and spaces, and it should not be blank and not contain"
                    + " weird symbols (!, $ etc.)";
    public static final int MAX_VENUE_CHAR_LENGTH = 95;

    /*
     * The first character of the address must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX = "^(?=.*[\\p{L}\\d])[\\p{L}\\p{M}\\d ,.'()#&@:\\-/]+$";

    public static final String MESSAGE_VENUE_EXCEED_MAX_LENGTH = String.format(
            "Venue should not be longer than %d characters!", MAX_VENUE_CHAR_LENGTH
    );

    public final String value;

    /**
     * Constructs a {@code Venue}.
     *
     * @param venue A valid venue.
     */
    public Venue(String venue) {
        requireNonNull(venue);
        try {
            checkArgument(isValidVenue(venue), MESSAGE_CONSTRAINTS);
        } catch (ParseException e) {
            throw new IllegalArgumentException(MESSAGE_VENUE_EXCEED_MAX_LENGTH);
        }
        value = venue;
    }

    /**
     * Returns true if a given string is a valid venue.
     */
    public static boolean isValidVenue(String test) throws ParseException {
        if (test.trim().length() > MAX_VENUE_CHAR_LENGTH) {
            throw new ParseException(MESSAGE_VENUE_EXCEED_MAX_LENGTH);
        }

        return test.matches(VALIDATION_REGEX);
    }

    public boolean isBlank() {
        return value == null || value.isBlank();
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Venue)) {
            return false;
        }

        Venue otherVenue = (Venue) other;
        return value.equals(otherVenue.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
