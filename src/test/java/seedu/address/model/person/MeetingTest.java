package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.meeting.Meeting;

public class MeetingTest {
    @Test
    public void toString_success() throws ParseException {
        String meetingName = "meeting";
        String venue = "venue";
        String when = "2025-10-11 1400";
        Meeting meeting = new Meeting(meetingName, venue, when);
        String expectedString = meetingName + " at " + venue + " (Oct 11 2025 14:00)";
        assertEquals(expectedString, meeting.toString());
    }

    @Test
    public void equals() throws ParseException {
        Meeting meeting = new Meeting("meeting", "venue", "2025-10-11 1400");

        assertTrue(meeting.equals(meeting));

        Meeting sameMeeting = new Meeting(meeting.meetingName, meeting.venue, meeting.when);
        assertTrue(meeting.equals(sameMeeting));

        assertFalse(meeting.equals(1));

        assertFalse(meeting.equals(null));

        Meeting differentMeeting = new Meeting("another meeting", "another venue",
                "2025-12-12 1800");
        assertFalse(meeting.equals(differentMeeting));
    }
}
