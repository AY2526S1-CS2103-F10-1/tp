package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;
import static seedu.address.logic.parser.ParserUtil.parseParametersAndLabels;
import static seedu.address.model.person.Person.LABEL_MESSAGE;
import static seedu.address.model.util.ValidationUtil.isParameterAndLabelsValid;

import java.util.List;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Represents a Person's address in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidAddress(String)}
 */
public class Address {
    public static final String MESSAGE_CONSTRAINTS = "For address, one or more addresses/labels are invalid!\n\n"
            + "Addresses are case-insensitive and "
            + "can take any values, and it should not be blank.\n\n"
            + LABEL_MESSAGE
            + "\n\n"
            + "Multiple addresses are allowed but must adhere to the following conditions: \n"
            + "1. For 1 address only, the label is optional so: ADDRESS or ADDRESS (LABEL).\n"
            + "2. For multiple addresses, the label is compulsory so: ADDRESS1 (LABEL1) ADDRESS2 (LABEL2) ... "
            + "ADDRESSN (LABELN).";
    /*
     * The first character of the address must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String ADDRESS_VALIDATION_REGEX = "[^\\s].*";
    public static final String ERROR_MESSAGE_DISPLAY_NAME = "address";
    private static final Logger logger = LogsCenter.getLogger(Address.class);
    private static final int FIELD_MAXIMUM_LENGTH = 200;
    public final String value;
    /**
     * Constructs an {@code Address}.
     *
     * @param address A valid address.
     */
    public Address(String address) {
        requireNonNull(address);

        try {
            checkArgument(isValidAddress(address), MESSAGE_CONSTRAINTS);
        } catch (ParseException e) {
            logger.warning("ParseException thrown for Address constructor for: " + address);
        }

        value = address;
    }

    /**
     * Returns true if a given string is a valid address.
     * @param addresses The {@code String addresses} to check if it is valid.
     * @return A boolean indicating if the address is valid.
     * @throws ParseException if addresses and/or labels are invalid or if they contain duplicates
     */
    public static boolean isValidAddress(String addresses) throws ParseException {
        requireNonNull(addresses);
        assert addresses != null : "Address provided in isValidAddress is null!";

        String trimmedAddress = addresses.trim();

        if (trimmedAddress.isEmpty()) {
            return false;
        }

        if (trimmedAddress.length() > FIELD_MAXIMUM_LENGTH) {
            String exceptionMessage = "Input for %s has exceeded "
                    + "the maximum length of %d characters!";

            String exceptionMessageFormatted = String.format(exceptionMessage,
                    Address.ERROR_MESSAGE_DISPLAY_NAME, FIELD_MAXIMUM_LENGTH);

            throw new ParseException(exceptionMessageFormatted);
        }

        List<String> paramsAndLabels = parseParametersAndLabels(ERROR_MESSAGE_DISPLAY_NAME,
                trimmedAddress, false);

        if (paramsAndLabels.isEmpty()) {
            return false;
        }

        return isAddressesAndLabelsValid(paramsAndLabels);
    }

    /**
     * <p>
     * Check if the parameters and labels of an Address is valid.
     * Address and labels must be distinct they cannot be repeated.
     * For it to be valid we accept:
     * </p><br><p>
     * 1) If there is only one address we accept either: ADDRESS or ADDRESS (LABEL)
     * </p><p>
     * 2) If there is more than one address every address must be accompanied by a label like: ADDRESS1 (LABEL1)
     * ADDRESS2 (LABEL2) ...
     * </p>
     * @param list The {@code List<String>} of the parameters and labels of an Address.
     * @return A boolean indicating if the parameters and labels of an Address is valid.
     * @throws ParseException If there are duplicate addresses/labels.
     */
    private static boolean isAddressesAndLabelsValid(List<String> list) throws ParseException {
        return isParameterAndLabelsValid(list, ADDRESS_VALIDATION_REGEX, ERROR_MESSAGE_DISPLAY_NAME);
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
