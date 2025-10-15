package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;
import static seedu.address.logic.parser.ParserUtil.parseParametersAndLabels;
import static seedu.address.model.person.Person.LABEL_MESSAGE;
import static seedu.address.model.person.Person.LABEL_VALIDATION_REGEX;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Represents a Person's address in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidAddress(String)}
 */
public class Address {

    public static final String MESSAGE_CONSTRAINTS = "Addresses can take any values, and it should not be blank\n"
            + LABEL_MESSAGE
            + "\n\n"
            + "Multiple addresses are allowed but most adhere to the following conditions: \n"
            + "1. For 1 address only, the label is optional so: ADDRESS or ADDRESS (LABEL).\n"
            + "2. For multiple emails, the label is compulsory so: ADDRESS1 (LABEL1) ADDRESS2 (LABEL2) ... "
            + "ADDRESSN (LABELN).";

    /*
     * The first character of the address must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String ADDRESS_VALIDATION_REGEX = "[^\\s].*";

    public final String value;

    /**
     * Constructs an {@code Address}.
     *
     * @param address A valid address.
     */
    public Address(String address) {
        requireNonNull(address);
        checkArgument(isValidAddress(address), MESSAGE_CONSTRAINTS);
        value = address;
    }

    /**
     * Returns true if a given string is a valid email.
     */
    public static boolean isValidAddress(String test) {
        List<String> paramsAndLabels = parseParametersAndLabels(test);

        if (paramsAndLabels.isEmpty()) {
            return false;
        }

        return test.matches(ADDRESS_VALIDATION_REGEX);
    }

    /**
     * <p>
     * Check if the parameters and labels of an Address is valid.
     * Address and labels must be distinct they cannot be repeated.
     * For it to be valid we accept:
     * </p><br><p>
     * 1) If there is only one address we accept either: ADDRESS or ADDRESS (LABEL)
     * </p><p>
     * 2) If there is more than one email every email must be accompanied by a label like: ADDRESS1 (LABEL1)
     * ADDRESS2 (LABEL2) ...
     * </p>
     * @param list The {@code List<String>} of the parameters and labels of an Address.
     * @return A boolean indicating if the parameters and labels of an Address is valid.
     */
    private static boolean isAddressesAndLabelsValid(List<String> list) {
        boolean checkAddress = true;

        Set<String> set = new HashSet<>();

        for (String currString : list) {
            if (set.contains(currString)) {
                return false;
            }

            if (checkAddress && !currString.matches(ADDRESS_VALIDATION_REGEX)) {
                return false;
            }

            if (!checkAddress && !currString.matches(LABEL_VALIDATION_REGEX)) {
                return false;
            }

            set.add(currString);
            checkAddress = !checkAddress;
        }

        return true;
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
        if (!(other instanceof Address)) {
            return false;
        }

        Address otherAddress = (Address) other;
        return value.equals(otherAddress.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
