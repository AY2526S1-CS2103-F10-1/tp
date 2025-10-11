package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.StringUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods used for parsing strings in the various *Parser classes.
 */
public class ParserUtil {

    public static final String MESSAGE_INVALID_INDEX = "Index is not a non-zero unsigned integer.";

    /**
     * Parses {@code oneBasedIndex} into an {@code Index} and returns it. Leading and trailing whitespaces will be
     * trimmed.
     * @throws ParseException if the specified index is invalid (not non-zero unsigned integer).
     */
    public static Index parseIndex(String oneBasedIndex) throws ParseException {
        String trimmedIndex = oneBasedIndex.trim();
        if (!StringUtil.isNonZeroUnsignedInteger(trimmedIndex)) {
            throw new ParseException(MESSAGE_INVALID_INDEX);
        }
        return Index.fromOneBased(Integer.parseInt(trimmedIndex));
    }

    /**
     * Parses a {@code String name} into a {@code Name}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code name} is invalid.
     */
    public static Name parseName(String name) throws ParseException {
        requireNonNull(name);
        String trimmedName = name.trim();
        if (!Name.isValidName(trimmedName)) {
            throw new ParseException(Name.MESSAGE_CONSTRAINTS);
        }
        return new Name(trimmedName);
    }

    /**
     * Parses a {@code String phone} into a {@code Phone}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code phone} is invalid.
     */
    public static Phone parsePhone(String phone) throws ParseException {
        requireNonNull(phone);
        String trimmedPhone = phone.trim();
        if (!Phone.isValidPhone(trimmedPhone)) {
            throw new ParseException(Phone.MESSAGE_CONSTRAINTS);
        }
        return new Phone(trimmedPhone);
    }

    /**
     * Parses a {@code String address} into an {@code Address}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code address} is invalid.
     */
    public static Address parseAddress(String address) throws ParseException {
        requireNonNull(address);
        String trimmedAddress = address.trim();
        if (!Address.isValidAddress(trimmedAddress)) {
            throw new ParseException(Address.MESSAGE_CONSTRAINTS);
        }
        return new Address(trimmedAddress);
    }

    /**
     * Parses a {@code String email} into an {@code Email}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code email} is invalid.
     */
    public static Email parseEmail(String email) throws ParseException {
        requireNonNull(email);
        String trimmedEmail = email.trim();
        if (!Email.isValidEmail(trimmedEmail)) {
            throw new ParseException(Email.MESSAGE_CONSTRAINTS);
        }
        return new Email(trimmedEmail);
    }

    /**
     * Parses a {@code String text} into an {@code List<String>} of parameters and labels.
     * For example: some parameter (label) some parameter2 (label2).
     *
     * @param text The text that we are trying to split into parameters and labels.
     * @return A list of parameters and labels or empty list if incorrect format.
     */
    public static List<String> parseParametersAndLabels(String text) {
        text = text.trim();
        List<String> parametersAndLabels = new ArrayList<>();
        int textLength = text.length();
        boolean extractParameter = true;
        int i = 0;

        while (i < textLength) {
            int end = 0;

            if (extractParameter) {
                end = text.indexOf("(", i);

                if (hasParameterSpacingIssue(i, end, text)) {
                    return new ArrayList<>();
                }

                end = end != -1 ? end - 1 : textLength;
            } else {
                end = text.indexOf(")", i);

                if (hasLabelSpacingIssue(end, text, textLength)) {
                    return new ArrayList<>();
                }

                end = end != -1 ? end + 1 : textLength;
            }

            assert i <= end : "Issue with splitParametersAndLabels: end value < i";

            String currString = text.substring(i, end);
            parametersAndLabels.add(currString);
            i = end + 1;
            extractParameter = !extractParameter;
        }

        if (isListIncorrectSize(parametersAndLabels)) {
            return new ArrayList<>();
        }

        return parametersAndLabels;
    }

    /**
     * Checks for spacing issues like missing/extra spaces between the parameter and label.
     *
     * @param parameterStartIndex The start index of our current parameter.
     * @param openBracketIndex The index of the first open bracket after {@code parameterStartIndex}.
     * @param text The string that we are checking on.
     * @return A boolean indicating if there are spacing issues.
     */
    private static boolean hasParameterSpacingIssue(int parameterStartIndex, int openBracketIndex,
            String text) {

        // Check if we can find the open bracket
        if (openBracketIndex == -1) {
            return false;
        }

        // Ensure no out of bounds issues later for < 0
        if (openBracketIndex < 2) {
            return true;
        }

        // Ensure the end of out parameter is not before the start
        if (openBracketIndex - 2 < parameterStartIndex) {
            return true;
        }

        // Checks for missing or extra spaces
        return text.charAt(openBracketIndex - 1) != ' ' || text.charAt(openBracketIndex - 2) == ' ';
    }

    /**
     * Checks for spacing issues like missing/extra spaces between the label and parameter.
     *
     * @param closeBracketIndex The index of the first close bracket after.
     * @param text The string that we are checking on.
     * @return A boolean indicating if there are spacing issues.
     */
    private static boolean hasLabelSpacingIssue(int closeBracketIndex, String text, int textLength) {
        // Check if we can find the close bracket
        if (closeBracketIndex == -1) {
            return false;
        }

        // Check if the close bracket is at the end of the text
        if (closeBracketIndex == textLength - 1) {
            return false;
        }

        // Check for out of bounds issues
        if (closeBracketIndex + 2 >= textLength) {
            return true;
        }

        // Checks for missing or extra spaces
        return text.charAt(closeBracketIndex + 1) != ' ' || text.charAt(closeBracketIndex + 2) == ' ';
    }

    /**
     * Checks if the total number of parameters and labels extracted have the correct size.
     *
     * @param list The list containing parameters and labels.
     * @return A boolean indicating if the list has the correct size
     */
    private static boolean isListIncorrectSize(List<String> list) {
        if (list.isEmpty()) {
            return true;
        }

        return list.size() >= 2 && list.size() % 2 == 1;
    }

    /**
     * Parses a {@code String tag} into a {@code Tag}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code tag} is invalid.
     */
    public static Tag parseTag(String tag) throws ParseException {
        requireNonNull(tag);
        String trimmedTag = tag.trim();
        if (!Tag.isValidTagName(trimmedTag)) {
            throw new ParseException(Tag.MESSAGE_CONSTRAINTS);
        }
        return new Tag(trimmedTag);
    }

    /**
     * Parses {@code Collection<String> tags} into a {@code Set<Tag>}.
     */
    public static Set<Tag> parseTags(Collection<String> tags) throws ParseException {
        requireNonNull(tags);
        final Set<Tag> tagSet = new HashSet<>();
        for (String tagName : tags) {
            tagSet.add(parseTag(tagName));
        }
        return tagSet;
    }
}
