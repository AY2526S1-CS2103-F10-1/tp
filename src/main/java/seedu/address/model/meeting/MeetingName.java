package seedu.address.model.meeting;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Meeting's name.
 * Guarantees: immutable; is valid as declared in {@link #isValidMeetingName(String)}
 */
public class MeetingName {
    public static final String MESSAGE_CONSTRAINTS =
            "Meeting names should only contain alphanumeric characters and spaces, and it should not be blank";

    /*
     * The first character of the meeting name must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX = "^(?=.*\\p{L}|\\d)[\\p{L}\\p{M}0-9 '\\s.()/&,-]+$";

    public final String meetingName;

    /**
     * Constructs a {@code MeetingName}.
     *
     * @param name A valid meeting name.
     */
    public MeetingName(String name) {
        requireNonNull(name);
        checkArgument(isValidMeetingName(name), MESSAGE_CONSTRAINTS);
        meetingName = name;
    }

    /**
     * Returns true if a given string is a valid meeting name.
     */
    public static boolean isValidMeetingName(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    public boolean isBlank() {
        return meetingName == null || meetingName.isBlank();
    }

    @Override
    public String toString() {
        return meetingName;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof MeetingName)) {
            return false;
        }

        MeetingName otherName = (MeetingName) other;
        return meetingName.equals(otherName.meetingName);
    }

    @Override
    public int hashCode() {
        return meetingName.hashCode();
    }
}
