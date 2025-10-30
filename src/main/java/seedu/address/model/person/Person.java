package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.meeting.Meeting;
import seedu.address.model.tag.Tag;

/**
 * Represents a Person in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Person {
    // Label message used for other identity/data fields
    public static final String LABEL_MESSAGE =
            "Labels are case-insensitive and can be made up of alphanumerical characters, spaces, and hyphens. "
                    + "It must adhere to the following constraints:\n"
                    + "1. The label cannot be made up of only spaces and/or hyphens only.";
    // Common label regex used for other identity/data fields
    // Can contain any alphanumerical characters, space, hyphen. But cannot be made up of space/hyphen only.
    private static final String ALPHANUMERIC_SPACE_HYPHEN = "(?=.*[a-zA-Z0-9])[a-zA-Z0-9 -]+";
    public static final String LABEL_VALIDATION_REGEX = "\\(" + ALPHANUMERIC_SPACE_HYPHEN + "\\)";

    // Identity fields
    private final Name name;
    private final Phone phone;
    private final OtherPhones otherPhones;
    private final Email email;

    // Data fields
    private final Address address;
    private final Set<Tag> tags = new HashSet<>();
    private final List<Meeting> meetings = new ArrayList<>();
    private final FlagStatus flagStatus;

    /**
     * Every field must be present and not null.
     */
    public Person(Name name, Phone phone, OtherPhones otherPhones,
                  Email email, Address address, Set<Tag> tags,
                  List<Meeting> meetings, FlagStatus isFlagged) {
        requireAllNonNull(name, phone, email, address, tags,
                meetings, isFlagged);
        this.name = name;
        this.phone = phone;
        this.otherPhones = otherPhones;
        this.email = email;
        this.address = address;
        this.tags.addAll(tags);
        this.meetings.addAll(meetings);
        this.flagStatus = isFlagged;
    }

    public Name getName() {
        return name;
    }

    public Phone getPhone() {
        return phone;
    }

    public OtherPhones getOtherPhones() {
        return otherPhones;
    }

    public Email getEmail() {
        return email;
    }

    public Address getAddress() {
        return address;
    }

    public FlagStatus getFlagStatus() {
        return flagStatus;
    }

    public boolean isFlagged() {
        return flagStatus.isFlagged();
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    public List<Meeting> getMeetings() {
        return Collections.unmodifiableList(meetings);
    }

    /**
     * Returns true if both persons have the same name.
     * This defines a weaker notion of equality between two persons.
     */
    public boolean isSamePerson(Person otherPerson) {
        if (otherPerson == this) {
            return true;
        }

        return otherPerson != null
                && otherPerson.getPhone().equals(getPhone());
    }

    /**
     * Removes a meeting from the person's meeting list.
     *
     * @param index The index of the meeting to be removed.
     */
    public void removeMeeting(int index) {
        meetings.remove(index);
    }

    /**
     * Returns the number of meetings the person has.
     */
    public int getMeetingCount() {
        return meetings.size();
    }

    /**
     * Edits a meeting from the person's meeting list.
     *
     * @param meetingToEdit the index of the meeting to be edited.
     * @param meeting the updated meeting to replace the old meeting.
     */
    public void editMeeting(int meetingToEdit, Meeting meeting) {
        assert meetingToEdit >= 0;
        assert meetingToEdit < meetings.size();
        requireNonNull(meeting);

        meetings.set(meetingToEdit, meeting);
    }

    /**
     * Returns a boolean if the main number contains the keyword
     *
     * @param keyword the keyword to check against
     */
    public boolean checkNumberContainsKeyword(String keyword) {

        assert keyword != null : "Keyword should not be null";

        return phone.value.contains(keyword);
    }

    /**
     * Returns true if both persons have the same identity and data fields.
     * This defines a stronger notion of equality between two persons.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Person)) {
            return false;
        }

        Person otherPerson = (Person) other;
        return name.equals(otherPerson.name)
                && phone.equals(otherPerson.phone)
                && email.equals(otherPerson.email)
                && address.equals(otherPerson.address)
                && tags.equals(otherPerson.tags)
                && meetings.equals(otherPerson.meetings)
                && flagStatus.equals(otherPerson.flagStatus);
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, phone, email, address, tags, flagStatus);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("name", name)
                .add("phone", phone)
                .add("email", email)
                .add("address", address)
                .add("tags", tags)
                .add("meetings", meetings)
                .add("isFlagged", flagStatus)
                .toString();
    }

}
