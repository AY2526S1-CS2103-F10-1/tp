package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;
import static seedu.address.logic.parser.ParserUtil.parseParametersAndLabels;
import static seedu.address.model.person.Person.LABEL_MESSAGE;
import static seedu.address.model.person.Person.LABEL_VALIDATION_REGEX;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Represents a Person's email in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidEmail(String)}
 */
public class Email {
    private static final Logger logger = LogsCenter.getLogger(Email.class);
    private static final String SPECIAL_CHARACTERS = "+_.-";
    public static final String MESSAGE_CONSTRAINTS = "Emails should be of the format local-part@domain "
            + "and adhere to the following constraints:\n"
            + "1. The local-part should only contain alphanumeric characters and these special characters, excluding "
            + "the parentheses, (" + SPECIAL_CHARACTERS + "). The local-part may not start or end with any special "
            + "characters.\n"
            + "2. This is followed by a '@' and then a domain name. The domain name is made up of domain labels "
            + "separated by periods.\n"
            + "The domain name must:\n"
            + "    - end with a domain label at least 2 characters long\n"
            + "    - have each domain label start and end with alphanumeric characters\n\n"
            + "    - have each domain label consist of alphanumeric characters, separated only by hyphens, if any.\n"
            + LABEL_MESSAGE
            + "\n\n"
            + "Multiple emails are allowed but must adhere to the following conditions: \n"
            + "1. For 1 email only, the label is optional so: EMAIL or EMAIL (LABEL).\n"
            + "2. For multiple emails, the label is compulsory so: EMAIL1 (LABEL1) EMAIL2 (LABEL2) ... "
            + "EMAILN (LABELN).";

    // alphanumeric and special characters
    private static final String ALPHANUMERIC_NO_UNDERSCORE = "[^\\W_]+"; // alphanumeric characters except underscore
    private static final String LOCAL_PART_REGEX = "^" + ALPHANUMERIC_NO_UNDERSCORE + "([" + SPECIAL_CHARACTERS + "]"
            + ALPHANUMERIC_NO_UNDERSCORE + ")*";
    private static final String DOMAIN_PART_REGEX = ALPHANUMERIC_NO_UNDERSCORE
            + "(-" + ALPHANUMERIC_NO_UNDERSCORE + ")*";
    private static final String DOMAIN_LAST_PART_REGEX = "(" + DOMAIN_PART_REGEX + "){2,}$"; // At least two chars
    private static final String DOMAIN_REGEX = "(" + DOMAIN_PART_REGEX + "\\.)*" + DOMAIN_LAST_PART_REGEX;
    public static final String EMAIL_VALIDATION_REGEX = LOCAL_PART_REGEX + "@" + DOMAIN_REGEX;

    public final String value;

    /**
     * Constructs an {@code Email}.
     *
     * @param email A valid email address.
     */
    public Email(String email) {
        requireNonNull(email);

        try {
            checkArgument(isValidEmail(email), MESSAGE_CONSTRAINTS);
        } catch (ParseException e) {
            logger.warning("ParseException thrown for Email constructor for: " + email);
        }

        value = email;
    }

    /**
     * Returns if a given string is a valid email.
     *
     * @param test The {@code String email} test if it is valid.
     * @return A boolean indicating if the email is valid or not.
     */
    public static boolean isValidEmail(String test) throws ParseException {
        String trimmedEmail = test.trim();

        if (trimmedEmail.isEmpty()) {
            return false;
        }

        List<String> paramsAndLabels = parseParametersAndLabels(Email.class.getName().toLowerCase(),
                test, false);

        if (paramsAndLabels.isEmpty()) {
            return false;
        }

        return isEmailsAndLabelsValid(paramsAndLabels);
    }

    /**
     * <p>
     * Check if the parameters and labels of an Email is valid.
     * Emails and labels must be distinct they cannot be repeated.
     * For it to be valid we accept:
     * </p><br><p>
     * 1) If there is only one email we accept either: EMAIL or EMAIL (LABEL)
     * </p><p>
     * 2) If there is more than one email every email must be accompanied by a label like: EMAIL1 (LABEL1)
     * EMAIL2 (LABEL2) ...
     * </p>
     * @param list The {@code List<String>} of the parameters and labels of an Email.
     * @return A boolean indicating if the parameters and labels of an Email is valid.
     */
    private static boolean isEmailsAndLabelsValid(List<String> list) {
        // If true we are checking if the email is valid, if it is false we are checking if label is valid.
        boolean checkEmail = true;

        Set<String> set = new HashSet<>();

        for (String currString : list) {
            if (set.contains(currString)) {
                return false;
            }

            if (checkEmail && !currString.matches(EMAIL_VALIDATION_REGEX)) {
                return false;
            }

            if (!checkEmail && !currString.matches(LABEL_VALIDATION_REGEX)) {
                return false;
            }

            set.add(currString);

            // Toggle between checking address and label
            checkEmail = !checkEmail;
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
        if (!(other instanceof Email)) {
            return false;
        }

        Email otherEmail = (Email) other;
        return value.equals(otherEmail.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
