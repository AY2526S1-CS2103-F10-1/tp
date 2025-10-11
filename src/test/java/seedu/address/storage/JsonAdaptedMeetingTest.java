package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.storage.JsonAdaptedMeeting.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.address.testutil.Assert.assertThrows;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.meeting.Meeting;
import seedu.address.model.meeting.MeetingName;
import seedu.address.model.meeting.Venue;
import seedu.address.model.meeting.When;

public class JsonAdaptedMeetingTest {
    private static final String VALID_MEETING_NAME = "Financial sharing";
    private static final String VALID_VENUE = "AMK Hub";
    private static final LocalDateTime VALID_DATETIME = LocalDateTime.parse("2025-11-10T14:30:00");

    private static final String INVALID_MEETING_NAME = "^";
    private static final String INVALID_VENUE = "^";

    @Test
    public void toModelType_validMeetingDetails_returnsMeeting() throws Exception {
        MeetingName meetingName = new MeetingName(VALID_MEETING_NAME);
        Venue venue = new Venue(VALID_VENUE);
        When when = new When(VALID_DATETIME);
        Meeting meeting = new Meeting(meetingName, venue, when);
        JsonAdaptedMeeting jsonMeeting = new JsonAdaptedMeeting(meeting);
        assertEquals(meeting, jsonMeeting.toModelType());
    }

    @Test
    public void toModelType_nullMeetingName_throwsIllegalValueException() {
        JsonAdaptedMeeting meeting = new JsonAdaptedMeeting(null, VALID_VENUE, VALID_DATETIME);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, MeetingName.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, meeting::toModelType);
    }

    @Test
    public void toModelType_nullVenue_throwsIllegalValueException() {
        JsonAdaptedMeeting meeting = new JsonAdaptedMeeting(VALID_MEETING_NAME, null, VALID_DATETIME);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Venue.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, meeting::toModelType);
    }

    @Test
    public void toModelType_nullDateTime_throwsIllegalValueException() {
        JsonAdaptedMeeting meeting = new JsonAdaptedMeeting(VALID_MEETING_NAME, VALID_VENUE, null);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, When.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, meeting::toModelType);
    }

    @Test
    public void toModelType_invalidMeetingName_throwsIllegalValueException() {
        JsonAdaptedMeeting meeting = new JsonAdaptedMeeting(INVALID_MEETING_NAME, VALID_VENUE, VALID_DATETIME);
        String expectedMessage = String.format(MeetingName.MESSAGE_CONSTRAINTS);
        assertThrows(IllegalValueException.class, expectedMessage, meeting::toModelType);
    }

    @Test
    public void toModelType_invalidVenue_throwsIllegalValueException() {
        JsonAdaptedMeeting meeting = new JsonAdaptedMeeting(VALID_MEETING_NAME, INVALID_VENUE, VALID_DATETIME);
        String expectedMessage = String.format(Venue.MESSAGE_CONSTRAINTS);
        assertThrows(IllegalValueException.class, expectedMessage, meeting::toModelType);
    }
}
