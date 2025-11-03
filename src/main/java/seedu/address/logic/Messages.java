package seedu.address.logic;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.address.logic.parser.Prefix;
import seedu.address.model.meeting.Meeting;
import seedu.address.model.person.Person;

/**
 * Container for user visible messages.
 */
public class Messages {

    public static final String MESSAGE_UNKNOWN_COMMAND = "Unknown command";
    public static final String MESSAGE_NO_PREAMBLE_BEFORE_PREFIX = "Invalid command format! "
            + "Strictly no preamble before prefix! \n%1$s";
    public static final String MESSAGE_INVALID_COMMAND_FORMAT = "Invalid command format! \n%1$s";
    public static final String MESSAGE_INVALID_PERSON_DISPLAYED_INDEX = "The person index provided is invalid";
    public static final String MESSAGE_PERSONS_LISTED_OVERVIEW = "%1$d persons listed!";
    public static final String MESSAGE_DUPLICATE_FIELDS =
                "Multiple values specified for the following single-valued field(s): ";
    public static final String MESSAGE_INVALID_DOES_NOT_EXIST = "Invalid datetime value (e.g. 31 April or 29 Feb in "
            + "non-leap year)";
    public static final String MESSAGE_INVALID_DATETIME_FORMAT = "Invalid datetime format. "
            + "Accepted formats: dd/MM/yyyy HH[:]mm, yyyy-MM-dd HH[:]mm";
    public static final String MESSAGE_INVALID_YEAR = "Invalid datetime value. Year value must at least be 1";
    public static final String MESSAGE_INVALID_MEETING_DISPLAYED_INDEX = "The meeting index provided is invalid";
    public static final String MESSAGE_FLAGS_CANNOT_BE_EMPTY = "The prefix flags cannot be empty";
    /**
     * Returns an error message indicating the duplicate prefixes.
     */
    public static String getErrorMessageForDuplicatePrefixes(Prefix... duplicatePrefixes) {
        assert duplicatePrefixes.length > 0;

        Set<String> duplicateFields =
                Stream.of(duplicatePrefixes).map(Prefix::toString).collect(Collectors.toSet());

        return MESSAGE_DUPLICATE_FIELDS + String.join(" ", duplicateFields);
    }

    /**
     * Formats the {@code person} for display to the user.
     */
    public static String format(Person person) {
        final StringBuilder builder = new StringBuilder();
        builder.append(person.getName())
                .append("; Phone: ")
                .append(person.getPhone())
                .append("; Other Phones: ")
                .append(person.getOtherPhones())
                .append("; Email: ")
                .append(person.getEmail())
                .append("; Address: ")
                .append(person.getAddress())
                .append("; Tags: ");
        person.getTags().forEach(builder::append);
        return builder.toString();
    }

    /**
     * Formats the {@code meeting} for display to the user.
     */
    public static String format(Meeting meeting) {
        final StringBuilder builder = new StringBuilder();
        builder.append(meeting.getMeetingName())
                .append("; Venue: ")
                .append(meeting.getVenue())
                .append("; When: ")
                .append(meeting.getWhen());
        return builder.toString();
    }
}
