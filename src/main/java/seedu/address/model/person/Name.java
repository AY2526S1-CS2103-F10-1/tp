package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Represents a Person's name in the address book.
 */
public class Name {

    /*
     * The first character of the address must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX_AT_LEAST_ONE_ALPHANUMERIC = "(?=.*(\\p{L}|\\d)).*";
    public static final String VALIDATION_REGEX_NAME_CONSTRAINTS = "^[\\p{L}\\p{M}0-9 \\-'’./]+$";
    public static final String VALIDATION_REGEX_NO_SPECIAL_CHAR_START = "^[\\-'’./].*$";
    public static final String VALIDATION_REGEX_NO_SPECIAL_CHAR_END = "^.*[\\-'’./]$";
    public static final String VALIDATION_REGEX_NO_CONSECUTIVE_SPECIAL_CHAR = ".*([ \\-'’./])\\1.*";

    public static final int MAX_NAME_CHAR_LENGTH = 95;

    public static final String MESSAGE_CONSTRAINTS_NO_BLANK_NAME =
            "Names should not be blank\n";
    public static final String MESSAGE_CONSTRAINTS_AT_LEAST_ONE_ALPHANUMERIC =
            "Names should contain at least one alphanumeric character\n";
    public static final String MESSAGE_CONSTRAINTS =
            "Names can only contain alphanumeric characters, spaces, hyphens, apostrophes, accented characters,"
                    + " periods and slashes\n";
    public static final String MESSAGE_CONSTRAINTS_INVALID_START_END =
            "Names should not start or end with a hyphen, apostrophe, period or slash\n";
    public static final String MESSAGE_CONSTRAINTS_NO_CONSECUTIVE_SPECIAL_CHAR =
            "Names should not contain consecutive spaces, hyphens, apostrophes,"
                    + " periods or slashes\n";
    public static final String MESSAGE_CONSTRAINTS_NAME_OVER_95_CHARS = String.format(
            "Names should not be longer than %d characters!\n",
            MAX_NAME_CHAR_LENGTH
    );

    public final String fullName;

    /**
     * Constructs a {@code Name} after validating it.
     *
     * @throws IllegalArgumentException if the given name is invalid
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
     * Validates the given name string against defined constraints.
     *
     * @throws IllegalArgumentException if the name is invalid
     */
    public static boolean isValidName(String test) throws ParseException {

        if (test.isBlank()) {
            throw new ParseException(MESSAGE_CONSTRAINTS_NO_BLANK_NAME);
        }

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

        if (test.matches(VALIDATION_REGEX_NO_CONSECUTIVE_SPECIAL_CHAR)) {
            throw new ParseException(MESSAGE_CONSTRAINTS_NO_CONSECUTIVE_SPECIAL_CHAR);
        }

        if (test.trim().length() > MAX_NAME_CHAR_LENGTH) {
            throw new ParseException(MESSAGE_CONSTRAINTS_NAME_OVER_95_CHARS);
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
