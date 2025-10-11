package seedu.address.storage;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.meeting.Meeting;
import seedu.address.model.meeting.MeetingName;
import seedu.address.model.meeting.Venue;
import seedu.address.model.meeting.When;

/**
 * Jackson-friendly version of {@link Meeting}.
 */
public class JsonAdaptedMeeting {
    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Meeting's %s field is missing!";

    public final String meetingName;
    public final String venue;
    public final LocalDateTime when;

    /**
     * Constructs a {@code JsonAdaptedMeeting} with the given {@code meetingName}, {@code venue}, and {@code when}.
     */
    @JsonCreator
    public JsonAdaptedMeeting(@JsonProperty("meetingName") String meetingName, @JsonProperty("venue") String venue,
                              @JsonProperty("when") LocalDateTime when) {
        this.meetingName = meetingName;
        this.venue = venue;
        this.when = when;
    }

    /**
     * Converts a given {@code Meeting} into this class for Jackson use.
     */
    public JsonAdaptedMeeting(Meeting source) {
        this.meetingName = source.meetingName.meetingName;
        this.venue = source.venue.value;
        this.when = source.when.value;
    }

    /**
     * Converts this Jackson-friendly adapted tag object into the model's {@code Meeting} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted tag.
     */
    public Meeting toModelType() throws IllegalValueException {
        if (this.meetingName == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    MeetingName.class.getSimpleName()));
        }
        if (!MeetingName.isValidMeetingName(this.meetingName)) {
            throw new IllegalValueException(MeetingName.MESSAGE_CONSTRAINTS);
        }
        final MeetingName modelMeetingName = new MeetingName(this.meetingName);

        if (this.venue == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Venue.class.getSimpleName()));
        }
        if (!Venue.isValidVenue(this.venue)) {
            throw new IllegalValueException(Venue.MESSAGE_CONSTRAINTS);
        }
        final Venue modelVenue = new Venue(this.venue);

        if (this.when == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    When.class.getSimpleName()));
        }
        if (!When.isValidWhen(this.when)) {
            throw new IllegalValueException(When.MESSAGE_CONSTRAINTS);
        }
        final When modelWhen = new When(this.when);
        return new Meeting(modelMeetingName, modelVenue, modelWhen);
    }
}
