package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_DATETIME_FORMAT;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Utility class for parsing and formatting {@link LocalDateTime} objects
 * using a restricted set of accepted datetime formats.
 * <p></p>
 * This class provides safe conversion between {@code String} and {@code LocalDateTime},
 * ensuring that only valid datetime formats are accepted.
 * If the input string does not match any of the allowed patterns,
 * a {@link ParseException} is thrown.
 */
public class DateTimeParser {
    /**
     * List of accepted datetime formatters
     */
    private static final List<DateTimeFormatter> ALLOWED_FORMATTERS = List.of(
            DateTimeFormatter.ofPattern("dd-MM-yyyy HHmm"),
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"),
            DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm"),
            DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm"),
            DateTimeFormatter.ofPattern("yyyy/MM/dd HHmm"),
            DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"),
            DateTimeFormatter.ofPattern("dd/MM/yyyy HHmm"),
            DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm")
    );
    private static final String OUTPUT_FORMAT = "MMM dd yyyy HH:mm";

    /**
     * Parses a {@code String} representation of a datetime into a {@link LocalDateTime} object.
     * @param dateTime the input string to parse (non-null)
     * @return a corresponding {@link LocalDateTime} object
     * @throws ParseException if none of the patterns match
     */
    public static LocalDateTime parseDateTime(String dateTime) throws ParseException {
        requireNonNull(dateTime);
        for (DateTimeFormatter formatter : ALLOWED_FORMATTERS) {
            try {
                return LocalDateTime.parse(dateTime, formatter);
            } catch (DateTimeParseException ignored) {
                // This exception is expected and can be safely ignored
            }
        }
        throw new ParseException(MESSAGE_INVALID_DATETIME_FORMAT);
    }

    /**
     * Formats a {@link LocalDateTime} object into a {@code String} using the output format.
     * @param dateTime the {@link LocalDateTime} to format (non-null)
     * @return formatted datetime String
     */
    public static String format(LocalDateTime dateTime) {
        requireNonNull(dateTime);
        return dateTime.format(DateTimeFormatter.ofPattern(OUTPUT_FORMAT));
    }
}
