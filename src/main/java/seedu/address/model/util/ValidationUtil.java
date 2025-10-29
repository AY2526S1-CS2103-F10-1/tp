package seedu.address.model.util;

import static seedu.address.model.person.Person.LABEL_VALIDATION_REGEX;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Contains utility methods for validating {@code person}'s fields.
 */
public class ValidationUtil {
    /**
     * Validates a list of parameters and labels and check if they are valid.
     * @param list The list of parameters and labels.
     * @param parameterRegex The regex to validate the parameter.
     * @param parameterName The actual parameter name used in exception messages.
     * @return A boolean indicating if the parameters and labels are valid.
     * @throws ParseException If there are duplicate parameters/labels.
     */
    public static boolean isParameterAndLabelsValid(List<String> list, String parameterRegex,
            String parameterName) throws ParseException {
        // If true we are checking if the email is valid, if it is false we are checking if label is valid.
        boolean checkParameter = true;

        Set<String> currParameters = new HashSet<>();
        Set<String> currLabels = new HashSet<>();

        String exceptionMessage = null;

        for (String currString : list) {
            // Parameters and labels are case-insensitive
            currString = currString.toLowerCase();

            // Check for duplicate parameter/label
            if (checkParameter && currParameters.contains(currString)) {
                exceptionMessage = String.format("One or more of the %ss you "
                        + "entered contains duplicates!", parameterName);

                throw new ParseException(exceptionMessage);
            }

            if (!checkParameter && currLabels.contains(currString)) {
                exceptionMessage = String.format("One or more of the labels you entered for %s "
                        + "contains duplicates!", parameterName);

                throw new ParseException(exceptionMessage);
            }

            // Validates parameter/label
            if (checkParameter && !currString.matches(parameterRegex)) {
                return false;
            }

            if (!checkParameter && !currString.matches(LABEL_VALIDATION_REGEX)) {
                return false;
            }

            // Store current parameter/label to check for duplicates later
            if (checkParameter) {
                currParameters.add(currString);
            }

            if (!checkParameter) {
                currLabels.add(currString);
            }

            // Toggle between checking parameter and label
            checkParameter = !checkParameter;
        }

        return true;
    }
}
