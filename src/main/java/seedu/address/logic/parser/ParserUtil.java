package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.StringUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.meeting.MeetingName;
import seedu.address.model.meeting.Venue;
import seedu.address.model.meeting.When;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.OtherPhones;
import seedu.address.model.person.Phone;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods used for parsing strings in the various *Parser classes.
 */
public class ParserUtil {
    public static final String MESSAGE_INVALID_INDEX = "Index is not a non-zero unsigned integer.";
    private static final Logger logger = LogsCenter.getLogger(ParserUtil.class);
    private static final int FIELD_MAXIMUM_LENGTH = 200;
    private static final String EXCEED_FIELD_MAXIMUM_LENGTH_ERROR_MESSAGE = "Input for %s has exceeded "
            + "the maximum length of %d characters!";

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
     * Parses a {@code String otherPhones} into a {@code OtherPhones}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code otherPhones} is invalid.
     */
    public static OtherPhones parseOtherPhones(String otherPhones, Phone mainPhone) throws ParseException {
        requireNonNull(otherPhones);
        if (!otherPhones.equals("") && !OtherPhones.isValidPhone(otherPhones)) {
            throw new ParseException(OtherPhones.MESSAGE_CONSTRAINTS);
        }

        if (OtherPhones.mainPhoneExists(otherPhones, mainPhone)) {
            throw new ParseException(OtherPhones.MESSAGE_DUPLICATE_CONSTRAINTS);
        }
        return new OtherPhones(otherPhones);
    }

    /**
     * Parses a {@code String otherPhones} into a {@code OtherPhones}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code otherPhones} is invalid.
     */
    public static OtherPhones parseEditOtherPhones(String otherPhones) throws ParseException {
        requireNonNull(otherPhones);
        if (!otherPhones.equals("") && !OtherPhones.isValidPhone(otherPhones)) {
            throw new ParseException(OtherPhones.MESSAGE_CONSTRAINTS);
        }

        return new OtherPhones(otherPhones);
    }

    /**
     * Parses a {@code String address} into an {@code Address}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException If the given {@code address} is invalid.
     */
    public static Address parseAddress(String address) throws ParseException {
        requireNonNull(address);
        String trimmedAddress = address.trim();

        if (trimmedAddress.length() > FIELD_MAXIMUM_LENGTH) {
            String exceptionMessage = String.format(EXCEED_FIELD_MAXIMUM_LENGTH_ERROR_MESSAGE,
                    Address.ERROR_MESSAGE_DISPLAY_NAME, FIELD_MAXIMUM_LENGTH);
            throw new ParseException(exceptionMessage);
        }

        if (!Address.isValidAddress(trimmedAddress)) {
            throw new ParseException(Address.MESSAGE_CONSTRAINTS);
        }

        return new Address(trimmedAddress);
    }

    /**
     * Parses a {@code String email} into an {@code Email}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException If the given {@code email} is invalid.
     */
    public static Email parseEmail(String email) throws ParseException {
        requireNonNull(email);

        String trimmedEmail = email.trim();

        if (trimmedEmail.length() > FIELD_MAXIMUM_LENGTH) {
            String exceptionMessage = String.format(EXCEED_FIELD_MAXIMUM_LENGTH_ERROR_MESSAGE,
                    Email.ERROR_MESSAGE_DISPLAY_NAME, FIELD_MAXIMUM_LENGTH);
            throw new ParseException(exceptionMessage);
        }

        if (!Email.isValidEmail(trimmedEmail)) {
            throw new ParseException(Email.MESSAGE_CONSTRAINTS);
        }

        return new Email(trimmedEmail);
    }

    /**
     * Parses a {@code String text} into an {@code List<String>} of parameters and labels.
     * For example: some parameter (label) some parameter2 (label2).
     * The {@code List<String>} of parameters and labels should not be empty.
     * @param parameterName Name of the parameter.
     * @param text The text that we are trying to split into parameters and labels.
     * @param isLabelAlwaysCompulsory Indicates if for 1 parameter if the label is compulsory.
     * @return A list of parameters and labels.
     * @throws ParseException If the format of the parameter and/or labels are invalid.
     */
    public static List<String> parseParametersAndLabels(String parameterName,
            String text, boolean isLabelAlwaysCompulsory) throws ParseException {
        text = text.trim();
        List<String> parametersAndLabels = extractParametersAndLabels(parameterName, text);
        checkForIncorrectListSize(parameterName, parametersAndLabels, isLabelAlwaysCompulsory);

        assert !parametersAndLabels.isEmpty() : "Issue with extractParametersAndLabels: the list returned is empty";

        String loggerMessage = String.format("Successfully extracted %ss and labels of size: %d.", parameterName,
                parametersAndLabels.size());
        logger.info(loggerMessage);

        return parametersAndLabels;
    }

    /**
     * Extracts parameters and labels from a {@code String text}.
     * At any point 0 <= i < end.
     * @param parameterName Name of the parameter.
     * @param text The text that we are trying to extract parameters and labels from.
     * @return The list which contains the extracted parameters and labels.
     * @throws ParseException If the parameters and/or labels are invalid.
     */
    public static List<String> extractParametersAndLabels(String parameterName, String text) throws ParseException {
        // True means we are extracting parameter, false means we are extracting label
        boolean extractParameter = true;
        int textLength = text.length();
        int i = 0;
        List<String> list = new ArrayList<>();

        while (i < textLength) {
            int end = getEndIndexOfParameterOrLabel(parameterName, text, textLength, i, extractParameter);

            assert i <= end : "Issue with splitParametersAndLabels: end value < i";
            assert i >= 0 : "Issue with splitParametersAndLabels: i < 0";

            String currString = text.substring(i, end);
            list.add(currString);
            i = end + 1;

            // Switch between extracting parameter and label
            extractParameter = !extractParameter;
        }

        return list;
    }

    /**
     * Deals with getting the end index of the current parameter or label from a {@code String text}.
     * Value of end should always be 0 <= end <= textLength.
     * @param parameterName Name of the parameter.
     * @param text The text that we are trying to get the end index of the current parameter or label from.
     * @param textLength The length of the text.
     * @param currIndex The current start index of the parameter or label.
     * @param extractParameter Boolean which indicates if we are extracting a parameter or label, true means
     *     we are extracting a parameter and false means we are extracting a label.
     * @return The list which contains the extracted parameters and labels.
     * @throws ParseException If the parameters and/or labels are invalid.
     */
    private static int getEndIndexOfParameterOrLabel(String parameterName, String text, int textLength,
            int currIndex, boolean extractParameter) throws ParseException {
        int end = 0;

        if (extractParameter) {
            // extract parameter

            end = text.indexOf("(", currIndex);

            checkForParameterSpacingIssue(parameterName, currIndex, end, text);

            end = end != -1 ? end - 1 : textLength;
        } else {
            // extract label

            end = text.indexOf(")", currIndex);

            checkForLabelSpacingIssue(parameterName, end, text, textLength);

            end = end != -1 ? end + 1 : textLength;
        }

        assert end >= 0 : "end value returned is < 0";
        assert end <= textLength : "end value returned is > textLength";

        return end;
    }

    /**
     * Checks for spacing issues like missing/extra spaces between the parameter and label.
     * @param parameterName Name of the parameter.
     * @param parameterStartIndex The start index of our current parameter.
     * @param openBracketIndex The index of the first open bracket after {@code parameterStartIndex}.
     * @param text The string that we are checking on.
     * @throws ParseException If the parameters are invalid.
     */
    private static void checkForParameterSpacingIssue(String parameterName, int parameterStartIndex,
            int openBracketIndex, String text) throws ParseException {
        String exceptionMessage;

        // Check if we can find the open bracket
        if (openBracketIndex == -1) {
            return;
        }

        // Ensure no out of bounds issues later for < 0
        if (openBracketIndex < 2) {
            exceptionMessage = String.format("Your first label for %1$s is at the start or it is missing "
                    + "a space from the previous parameter.", parameterName);
            throw new ParseException(exceptionMessage);
        }

        // Check if the open bracket that we found is the start index of the parameter
        if (parameterStartIndex == openBracketIndex) {
            exceptionMessage = String.format("One or more of your %1$ss are missing."
                    + " Each %1$s should be accompanied by a label.", parameterName);
            throw new ParseException(exceptionMessage);
        }

        // Check for missing space before open bracket and parameter
        if (text.charAt(openBracketIndex - 1) != ' ') {
            exceptionMessage = String.format("One or more of your %1$ss are missing a space"
                    + " to separate it from the next label.", parameterName);
            throw new ParseException(exceptionMessage);
        }

        // Check for extra spaces in between open bracket and parameter
        if (text.charAt(openBracketIndex - 2) == ' ') {
            exceptionMessage = String.format("One or more of your %1$ss have extra spaces in between it"
                    + " and the next label.", parameterName);
            throw new ParseException(exceptionMessage);
        }
    }

    /**
     * Checks for spacing issues like missing/extra spaces between the label and parameter.
     * @param parameterName Name of the parameter.
     * @param closeBracketIndex The index of the first close bracket after.
     * @throws ParseException If the labels are invalid.
     */
    private static void checkForLabelSpacingIssue(String parameterName, int closeBracketIndex,
            String text, int textLength) throws ParseException {
        String exceptionMessage;

        // Check if we can find the close bracket
        if (closeBracketIndex == -1) {
            return;
        }

        // Check if the close bracket is at the end of the text
        if (closeBracketIndex == textLength - 1) {
            return;
        }

        // Check for out of bounds issues
        if (closeBracketIndex + 2 >= textLength) {
            exceptionMessage = String.format("Your label at the end is missing a space"
                    + " to separate it from the last %1$s.", parameterName);
            throw new ParseException(exceptionMessage);
        }

        // Check if there is a missing space with current label and next parameter
        if (text.charAt(closeBracketIndex + 1) != ' ') {
            exceptionMessage = String.format("One or more of your labels are missing a space"
                    + " to separate it from the next %1$s.", parameterName);
            throw new ParseException(exceptionMessage);
        }

        // Check if there are extra spaces with current label and next parameter
        if (text.charAt(closeBracketIndex + 2) == ' ') {
            exceptionMessage = String.format("One or more of your labels have extra spaces in between it"
                    + " and the next %1$s.", parameterName);
            throw new ParseException(exceptionMessage);
        }
    }

    /**
     * Checks if the total number of parameters and labels extracted have the correct size.
     * @param parameterName Name of the parameter.
     * @param list The list containing parameters and labels.
     * @param isLabelAlwaysCompulsory Indicates if for 1 parameter if the label is compulsory.
     * @throws ParseException If the size of the list containing the extracted parameter and labels are invalid.
     */
    private static void checkForIncorrectListSize(String parameterName, List<String> list,
            boolean isLabelAlwaysCompulsory) throws ParseException {
        String exceptionMessage;
        int listMinSize = 2;

        // If label is always compulsory we have to ensure even if it is 1 parameter it must have a label
        if (isLabelAlwaysCompulsory) {
            listMinSize = 1;
        }

        if (list.isEmpty()) {
            exceptionMessage = String.format("%1$s cannot be empty or be made up of only spaces!",
                    parameterName);
            throw new ParseException(exceptionMessage);
        }

        // Check if the size of the list is correct
        if (list.size() >= listMinSize && list.size() % 2 == 1) {
            exceptionMessage = String.format("Every %1$s must be accompanied by a label."
                    + " For example %1$s1 (label1) %1$s2 (label2).", parameterName);
            throw new ParseException(exceptionMessage);
        }
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

    /**
     * Parses a {@code String meetingName} into a {@code MeetingName}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code meetingName} is invalid.
     */
    public static MeetingName parseMeetingName(String meetingName) throws ParseException {
        requireNonNull(meetingName);
        String trimmedMeetingName = meetingName.trim();
        if (!MeetingName.isValidMeetingName(trimmedMeetingName)) {
            throw new ParseException(MeetingName.MESSAGE_CONSTRAINTS);
        }
        return new MeetingName(trimmedMeetingName);
    }

    /**
     * Parses a {@code String venue} into a {@code Venue}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code venue} is invalid.
     */
    public static Venue parseVenue(String venue) throws ParseException {
        requireNonNull(venue);
        String trimmedVenue = venue.trim();
        if (!Venue.isValidVenue(trimmedVenue)) {
            throw new ParseException(Venue.MESSAGE_CONSTRAINTS);
        }
        return new Venue(trimmedVenue);
    }

    /**
     * Parses a {@code String when} into a {@code When}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code when} is invalid.
     */
    public static When parseWhen(String when) throws ParseException {
        requireNonNull(when);
        String trimmedWhen = when.trim();
        // This call will throw ParseException if invalid
        When.isValidWhen(trimmedWhen);
        return new When(trimmedWhen);
    }
}
