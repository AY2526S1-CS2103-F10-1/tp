package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;
import static seedu.address.logic.parser.ParserUtil.parseParametersAndLabels;
import static seedu.address.model.util.ValidationUtil.isParameterAndLabelsValid;

import java.util.List;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.parser.exceptions.ParseException;
/**
 * Represents a Peron's other numbers in the address books.
 */
public class OtherPhones {
    public static final String MESSAGE_DUPLICATE_CONSTRAINTS =
            "The other numbers should not be a duplicate of the main number.";

    public static final String MESSAGE_CONSTRAINTS =
            "Phone numbers should only contain numbers, and it should be at "
                   + "least 3 digits long and numbers should be separated by a tag e.g. 9999 (work) 8888 (office)";

    // Single phone number pattern
    private static final String SINGLE_PHONE_REGEX =
            "^\\s*(?:\\+\\d{1,3}\\s*)?\\d{3,}(?:\\s*x\\d+)?\\s*$";

    private static final Logger logger = LogsCenter.getLogger(OtherPhones.class);
    private static final String ERROR_MESSAGE_DISPLAY_NAME = "other number";
    public final String numbers;



    /**
     * Constructs a {@code OtherPhones}
     * @param phones Valid phone numbers with a tag each or a single phone number
     */
    public OtherPhones(String phones) {
        if (phones != null && !phones.equals("")) {
            phones = phones.trim();

            try {
                checkArgument(isValidPhone(phones), MESSAGE_CONSTRAINTS);
            } catch (ParseException e) {
                logger.warning("ParseException thrown for phone constructor for: " + phones);
            }
        }

        this.numbers = phones;
    }

    /**
     * Returns true if a given string is a valid phone.
     * @param phones The {@code String phone} to check if it is valid.
     * @return A boolean indicating if the address is valid.
     * @throws ParseException if phones and/or labels are invalid or if they contain duplicates
     */
    public static boolean isValidPhone(String phones) throws ParseException {
        requireNonNull(phones);
        assert phones != null : "Phones provided in isValidPhone is null!";

        String trimmedPhones = phones.trim();

        if (trimmedPhones.isEmpty()) {
            return true;
        }

        List<String> paramsAndLabels = parseParametersAndLabels(
                ERROR_MESSAGE_DISPLAY_NAME, trimmedPhones, false);

        return isPhonesAndLabelsValid(paramsAndLabels);
    }

    /**
     * <p>
     * Check if the parameters and labels of a Phone is valid.
     * Phones and labels must be distinct they cannot be repeated.
     * For it to be valid we accept:
     * </p><br><p>
     * 1) If there is only one phone we accept either: Phone or Phone (LABEL)
     * </p><p>
     * 2) If there is more than one Phone every Phone must be accompanied by a label like: Phone1 (LABEL1)
     * Phone2 (LABEL2) ...
     * </p>
     * @param phones The {@code List<String>} of the parameters and labels of a Phone.
     * @return A boolean indicating if the parameters and labels of a Phone is valid.
     * @throws ParseException If there are duplicate other numbers/labels.
     */
    public static boolean isPhonesAndLabelsValid(List<String> phones) throws ParseException {
        return isParameterAndLabelsValid(phones, SINGLE_PHONE_REGEX, ERROR_MESSAGE_DISPLAY_NAME);
    }

    /**
     * Returns true if otherPhones contains the main number
     * @param otherPhones A String that contains all the other phone numbers.
     * @oaram mainPhone The main phone that is tied to the person.
     * @return boolean
     */
    public static boolean mainPhoneExists(String otherPhones, Phone mainPhone) throws ParseException {
        otherPhones = otherPhones.trim();
        if (otherPhones == null || otherPhones.equals("")) {
            return false;
        }

        assert mainPhone != null : "Main Phone cannot be null: Main phone should have been created";
        List<String> paramsAndLabels = parseParametersAndLabels(
                OtherPhones.class.getName().toLowerCase(), otherPhones, false);

        for (String currPhone : paramsAndLabels) {
            if (!currPhone.matches(SINGLE_PHONE_REGEX)) {
                continue;
            }
            if (mainPhone.equals(new Phone(currPhone))) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return numbers;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof OtherPhones)) {
            return false;
        }

        OtherPhones otherPhone = (OtherPhones) other;
        return numbers.equals(otherPhone.numbers);
    }

    @Override
    public int hashCode() {
        return numbers.hashCode();
    }

}
