package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's phone number in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidPhone(String)}
 */
public class Phone {


    public static final String MESSAGE_CONSTRAINTS =
            "Phone numbers should only contain numbers, and it should be at least 3 digits long. \n"
            + "It should also satisy these constraints: \n"
            + "1) Country codes are optional but if specified should start with a + then at least 2 digits,"
            + " followed by a space then the main number. \n"
            + "2) The main number should at least be 3 digits long with no spaces. \n"
            + "3) The extension is optional but if present, there should be a space then an x then at least one digit.";
    public static final String VALIDATION_REGEX = "^(?:\\+\\d{2,} )?\\d{3,}(?: x\\d+)?$";
    public final String value;


    /**
     * Constructs a {@code Phone}.
     *
     * @param phone A valid phone number.
     */
    public Phone(String phone) {
        requireNonNull(phone);
        String input = phone.trim();
        checkArgument(isValidPhone(input), MESSAGE_CONSTRAINTS);
        value = input;
    }

    /**
     * Returns true if a given string is a valid phone number.
     */
    public static boolean isValidPhone(String test) {
        return test.matches(VALIDATION_REGEX);
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
        if (!(other instanceof Phone)) {
            return false;
        }

        Phone otherPhone = (Phone) other;
        return value.equals(otherPhone.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
