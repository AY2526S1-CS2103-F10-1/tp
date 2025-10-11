package seedu.address.model.meeting;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;

import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Represents a Meeting in the address book.
 */
public class Meeting {
    public final MeetingName meetingName;
    public final Venue venue;
    public final When when;

    /**
     * Constructs a {@code Meeting}
     * @param meetingName name of the meeting
     * @param venue venue of the meeting
     * @param when date and time of the meeting
     */
    public Meeting(MeetingName meetingName, Venue venue, When when) throws ParseException {
        requireAllNonNull(meetingName, venue, when);
        this.meetingName = meetingName;
        this.venue = venue;
        this.when = when;
    }

    /**
     * Constructs a {@code Meeting}
     * @param meetingName name of the meeting
     * @param venue venue of the meeting
     * @param when date and time of the meeting in {@code String} format
     */
    public Meeting(String meetingName, String venue, String when) throws ParseException {
        requireAllNonNull(meetingName, venue, when);
        this.meetingName = new MeetingName(meetingName);
        this.venue = new Venue(venue);
        this.when = new When(when);
    }

    @Override
    public String toString() {
        return String.format("%s at %s (%s)", meetingName, venue, when);
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || other instanceof Meeting
                && meetingName.equals(((Meeting) other).meetingName)
                && venue.equals(((Meeting) other).venue)
                && when.equals(((Meeting) other).when);
    }

    @Override
    public int hashCode() {
        return Objects.hash(meetingName, venue, when);
    }
}
