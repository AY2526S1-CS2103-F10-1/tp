package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static seedu.address.logic.Messages.MESSAGE_INVALID_DATETIME;
import static seedu.address.logic.Messages.MESSAGE_INVALID_DATETIME_FORMAT;
import static seedu.address.logic.Messages.MESSAGE_INVALID_YEAR;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.logic.parser.exceptions.ParseException;

public class DateTimeParserTest {
    private static final String VALID_DATETIME_FORMAT_1 = "28/12/2025 14:00";
    private static final String VALID_DATETIME_FORMAT_2 = "12-01-2025 1400";
    private static final String VALID_LEAP_YEAR_DATE = "29/02/2028 1400";
    private static final String INVALID_DATETIME_1 = "13/13/2025 14:00";
    private static final String INVALID_DATETIME_2 = "12/12/2025 25:69";
    private static final String INVALID_DATETIME_3 = "31/04/2025 1400";
    private static final String INVALID_DATETIME_YEAR_1 = "-2025/12/12 14:00";
    private static final String INVALID_DATETIME_YEAR_2 = "-2025-12-12 14:00";
    private static final String INVALID_DATETIME_YEAR_3 = "12-12--2025 14:00";
    private static final String INVALID_DATETIME_YEAR_4 = "0000/12/12 14:00";

    private static final String INVALID_DATETIME_FORMAT_1 = "when";
    private static final String INVALID_DATETIME_FORMAT_2 = "9/2/25 1700";
    private static final String INVALID_DATETIME_FORMAT_3 = "9 May 2025 1400";

    private static final String INVALID_LEAP_YEAR_DATE = "29/02/2025 1400";
    private static final String DATETIME_STUB_1 = "2025-12-28T14:00:00";
    private static final String DATETIME_STUB_2 = "2025-01-12T14:00:00";
    private static final String DATETIME_STUB_3 = "2028-02-29T14:00:00";
    private static final LocalDateTime VALID_DATETIME_1 = LocalDateTime.parse(DATETIME_STUB_1);
    private static final LocalDateTime VALID_DATETIME_2 = LocalDateTime.parse(DATETIME_STUB_2);
    private static final String VALID_DATETIME_STRING_1 = "Dec 28 2025 14:00";
    private static final String VALID_DATETIME_STRING_2 = "Jan 12 2025 14:00";

    private static final List<String> VALID_DATETIME_FORMATS = List.of(
            "12-01-2025 1400",
            "2025-01-12 14:00",
            "2025-01-12 1400",
            "2025/01/12 14:00",
            "2025/01/12 1400",
            "12/01/2025 14:00",
            "12/01/2025 1400",
            "12-01-2025 14:00"
    );

    @Test
    public void parseDateTime_parseValidDateTime_success() throws ParseException {
        LocalDateTime expectedDateTime;
        LocalDateTime actualDateTime;

        expectedDateTime = LocalDateTime.parse(DATETIME_STUB_1);
        actualDateTime = DateTimeParser.parseDateTime(VALID_DATETIME_FORMAT_1);
        assertEquals(expectedDateTime, actualDateTime);

        expectedDateTime = LocalDateTime.parse(DATETIME_STUB_2);
        actualDateTime = DateTimeParser.parseDateTime(VALID_DATETIME_FORMAT_2);
        assertEquals(expectedDateTime, actualDateTime);

        expectedDateTime = LocalDateTime.parse(DATETIME_STUB_3);
        actualDateTime = DateTimeParser.parseDateTime(VALID_LEAP_YEAR_DATE);
        assertEquals(expectedDateTime, actualDateTime);

        for (String validDatetimeFormat : VALID_DATETIME_FORMATS) {
            expectedDateTime = LocalDateTime.parse(DATETIME_STUB_2);
            actualDateTime = DateTimeParser.parseDateTime(validDatetimeFormat);
            assertEquals(expectedDateTime, actualDateTime);
        }
    }

    @Test
    public void parseDateTime_parseInvalidDateTime_throwsParseException() {
        Exception ex = assertThrows(ParseException.class, () ->
                DateTimeParser.parseDateTime(INVALID_DATETIME_1));
        assertEquals(MESSAGE_INVALID_DATETIME, ex.getMessage());

        ex = assertThrows(ParseException.class, () -> DateTimeParser.parseDateTime(INVALID_DATETIME_2));
        assertEquals(MESSAGE_INVALID_DATETIME, ex.getMessage());

        ex = assertThrows(ParseException.class, () -> DateTimeParser.parseDateTime(INVALID_DATETIME_3));
        assertEquals(MESSAGE_INVALID_DATETIME, ex.getMessage());

        ex = assertThrows(ParseException.class, () -> DateTimeParser.parseDateTime(INVALID_LEAP_YEAR_DATE));
        assertEquals(MESSAGE_INVALID_DATETIME, ex.getMessage());

        ex = assertThrows(ParseException.class, () -> DateTimeParser.parseDateTime(INVALID_DATETIME_FORMAT_1));
        assertEquals(MESSAGE_INVALID_DATETIME_FORMAT, ex.getMessage());

        ex = assertThrows(ParseException.class, () -> DateTimeParser.parseDateTime(INVALID_DATETIME_FORMAT_2));
        assertEquals(MESSAGE_INVALID_DATETIME_FORMAT, ex.getMessage());

        ex = assertThrows(ParseException.class, () -> DateTimeParser.parseDateTime(INVALID_DATETIME_FORMAT_3));
        assertEquals(MESSAGE_INVALID_DATETIME_FORMAT, ex.getMessage());

        ex = assertThrows(ParseException.class, () -> DateTimeParser.parseDateTime(INVALID_DATETIME_YEAR_1));
        assertEquals(MESSAGE_INVALID_YEAR, ex.getMessage());

        ex = assertThrows(ParseException.class, () -> DateTimeParser.parseDateTime(INVALID_DATETIME_YEAR_2));
        assertEquals(MESSAGE_INVALID_YEAR, ex.getMessage());

        ex = assertThrows(ParseException.class, () -> DateTimeParser.parseDateTime(INVALID_DATETIME_YEAR_3));
        assertEquals(MESSAGE_INVALID_YEAR, ex.getMessage());

        ex = assertThrows(ParseException.class, () -> DateTimeParser.parseDateTime(INVALID_DATETIME_YEAR_4));
        assertEquals(MESSAGE_INVALID_YEAR, ex.getMessage());
    }

    @Test
    public void format_formatValidDateTime_success() {
        String expectedString = VALID_DATETIME_STRING_1;
        String actualString = DateTimeParser.format(VALID_DATETIME_1);
        assertEquals(expectedString, actualString);

        expectedString = VALID_DATETIME_STRING_2;
        actualString = DateTimeParser.format(VALID_DATETIME_2);
        assertEquals(expectedString, actualString);
    }
}
