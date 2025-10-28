package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Represents a Person's name in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidName(String)}
 */
public class Name {

    public static final String MESSAGE_CONSTRAINTS_NO_BLANK_NAME =
            "Names should not be blank\n";
    public static final String MESSAGE_CONSTRAINTS_AT_LEAST_ONE_ALPHANUMERIC =
            "Names should contain at least one alphabetic character\n";
    public static final String MESSAGE_CONSTRAINTS =
            "Names can only contain alphanumeric characters, spaces, hyphens, apostrophes, accented characters,"
                    + " periods, digits and slashes\n";
    public static final String MESSAGE_CONSTRAINTS_INVALID_START_END =
            "Names should not start or end with a hyphen, apostrophe, accented character, period or slash\n";
    public static final String MESSAGE_CONSTRAINTS_NO_CONSECUTIVE_SPECIAL_CHAR =
            "Names should not contain consecutive spaces, hyphens, apostrophes, accented characters,"
                    + " periods or slashes\n";

    /*
     * The first character of the address must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX_AT_LEAST_ONE_ALPHANUMERIC = "(?=.*(\\p{L}|\\d)).*";
    public static final String VALIDATION_REGEX_NAME_CONSTRAINTS = "^[\\p{L}\\p{M}0-9 \\-'’./]+$";
    public static final String VALIDATION_REGEX_NO_SPECIAL_CHAR_START = "^[\\-'’./].*$";
    public static final String VALIDATION_REGEX_NO_SPECIAL_CHAR_END = "^.*[\\-'’./]$";
    public static final String VALIDATION_REGEX_NO_CONSECUTIVE_SPECIAL_CHAR = ".*([ \\-'’./])\\1.*";

    public final String fullName;

    /**
     * Constructs a {@code Name}.
     *
     * @param name A valid name.
     */
    public Name(String name) {
        requireNonNull(name);

        try {
            checkArgument(isValidName(name), MESSAGE_CONSTRAINTS);
        } catch (ParseException e) {
            throw new IllegalArgumentException(e.getMessage());
        }

        fullName = name;
    }

    /**
     * Returns true if a given string is a valid name.
     */
    public static boolean isValidName(String test) throws ParseException {

        // Checks if the name is blank
        if (test.trim().isEmpty()) {
            throw new ParseException(MESSAGE_CONSTRAINTS_NO_BLANK_NAME);
        }

        // Checks if the name contains at least one alphabetic character
        if (!test.matches(VALIDATION_REGEX_AT_LEAST_ONE_ALPHANUMERIC)) {
            throw new ParseException(MESSAGE_CONSTRAINTS_AT_LEAST_ONE_ALPHANUMERIC);
        }

        // If the name does not satisfy the basic constraints
        if (!test.matches(VALIDATION_REGEX_NAME_CONSTRAINTS)) {
            throw new ParseException(MESSAGE_CONSTRAINTS);
        }

        // If the name starts or ends with a special character
        if (test.matches(VALIDATION_REGEX_NO_SPECIAL_CHAR_START)
                || test.matches(VALIDATION_REGEX_NO_SPECIAL_CHAR_END)) {
            throw new ParseException(MESSAGE_CONSTRAINTS_INVALID_START_END);
        }

        // If the name contains consecutive special characters
        if (test.matches(VALIDATION_REGEX_NO_CONSECUTIVE_SPECIAL_CHAR)) {
            throw new ParseException(MESSAGE_CONSTRAINTS_NO_CONSECUTIVE_SPECIAL_CHAR);
        }

        return true;

    }

    @Override
    public String toString() {
        return fullName;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Name)) {
            return false;
        }

        Name otherName = (Name) other;
        return fullName.equals(otherName.fullName);
    }

    @Override
    public int hashCode() {
        return fullName.hashCode();
    }

}
