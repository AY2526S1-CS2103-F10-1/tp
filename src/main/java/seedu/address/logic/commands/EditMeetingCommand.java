package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MEETING;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MEETING_INDEX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PERSON_INDEX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_VENUE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_WHEN;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.meeting.Meeting;
import seedu.address.model.meeting.MeetingName;
import seedu.address.model.meeting.Venue;
import seedu.address.model.meeting.When;
import seedu.address.model.person.Person;

/**
 * Edits the details of an existing meeting of a person in the address book.
 */
public class EditMeetingCommand extends Command {
    public static final String COMMAND_WORD = "editmt";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the meeting identified for the "
            + "person identified by the index number used in the displayed person list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: " + PREFIX_PERSON_INDEX + "INDEX (must be a positive integer) "
            + PREFIX_MEETING_INDEX + "MEETING_INDEX (must be a positive integer) "
            + "[" + PREFIX_MEETING + "MEETING] "
            + "[" + PREFIX_VENUE + "VENUE] "
            + "[" + PREFIX_WHEN + "WHEN]\n"
            + "Example: " + COMMAND_WORD + " " + PREFIX_PERSON_INDEX + "1 "
            + PREFIX_MEETING_INDEX + "2 "
            + PREFIX_VENUE + "Starbucks at J8 "
            + PREFIX_WHEN + "2025-10-05 1600";

    public static final String MESSAGE_FORMAT = "Parameters:\n"
            + PREFIX_PERSON_INDEX + "INDEX (must be a positive integer) "
            + PREFIX_MEETING_INDEX + "MEETING_INDEX (must be a positive integer) "
            + "[" + PREFIX_MEETING + "MEETING] "
            + "[" + PREFIX_VENUE + "VENUE] "
            + "[" + PREFIX_WHEN + "WHEN]\n";

    public static final String MESSAGE_EDIT_MEETING_SUCCESS = "Edited Meeting: %1$s";
    public static final String MESSAGE_MEETING_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_INVALID_BLANK = "Please provide arguments after the command word.\n"
            + MESSAGE_FORMAT;

    private final Index personIndex;
    private final Index meetingIndex;
    private final EditMeetingDescriptor editMeetingDescriptor;

    /**
     * Constructs an {@code EditMeetingCommand} object based on the person and meeting specified and the fields to be
     * edited.
     * @param personIndex index of the person in the filtered person list to edit
     * @param meetingIndex index of the meeting for the person to edit
     * @param editMeetingDescriptor details of the meeting to be edited
     */
    public EditMeetingCommand(Index personIndex, Index meetingIndex, EditMeetingDescriptor editMeetingDescriptor) {
        requireNonNull(personIndex);
        requireNonNull(meetingIndex);
        requireNonNull(editMeetingDescriptor);

        this.personIndex = personIndex;
        this.meetingIndex = meetingIndex;
        this.editMeetingDescriptor = new EditMeetingDescriptor(editMeetingDescriptor);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getPersonList();

        if (personIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToEdit = lastShownList.get(personIndex.getZeroBased());
        List<Meeting> lastMeetingsList = personToEdit.getMeetings();

        if (meetingIndex.getZeroBased() >= personToEdit.getMeetingCount()) {
            throw new CommandException(Messages.MESSAGE_INVALID_MEETING_DISPLAYED_INDEX);
        }

        Meeting meetingToEdit = lastMeetingsList.get(meetingIndex.getZeroBased());
        Meeting editedMeeting = createEditedMeeting(meetingToEdit, editMeetingDescriptor);

        model.editMeeting(personToEdit, meetingIndex.getZeroBased(), editedMeeting);

        // Creates a new Person object with the updated meetings list
        Person editedPerson = new Person(personToEdit.getName(), personToEdit.getPhone(),
                personToEdit.getOtherPhones(), personToEdit.getEmail(),
                personToEdit.getAddress(), personToEdit.getTags(),
                personToEdit.getMeetings(), personToEdit.getFlagStatus());

        // Replaces the old person with the new person in the model to refresh the GUI
        model.setPerson(personToEdit, editedPerson);

        return new CommandResult(String.format(MESSAGE_EDIT_MEETING_SUCCESS, Messages.format(editedMeeting)));
    }

    /**
     * Creates and returns a {@code Meeting} with the details of {@code meetingToEdit}
     * edited with {@code editMeetingDescriptor}.
     */
    private static Meeting createEditedMeeting(Meeting meetingToEdit, EditMeetingDescriptor editMeetingDescriptor) {
        assert meetingToEdit != null;

        MeetingName updatedMeetingName = editMeetingDescriptor.getMeetingName().orElse(meetingToEdit.getMeetingName());
        Venue updatedVenue = editMeetingDescriptor.getVenue().orElse(meetingToEdit.getVenue());
        When updatedWhen = editMeetingDescriptor.getWhen().orElse(meetingToEdit.getWhen());

        return new Meeting(updatedMeetingName, updatedVenue, updatedWhen);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof EditMeetingCommand)) {
            return false;
        }

        EditMeetingCommand e = (EditMeetingCommand) other;
        return personIndex.equals(e.personIndex)
                && meetingIndex.equals(e.meetingIndex)
                && editMeetingDescriptor.equals(e.editMeetingDescriptor);
    }

    /**
     * Stores the details to edit the meeting with. Each non-empty field value will replace the
     * corresponding field value of the meeting.
     */
    public static class EditMeetingDescriptor {
        private MeetingName meetingName;
        private Venue venue;
        private When when;

        public EditMeetingDescriptor() {}

        /**
         * Copy constructor.
         * Constructs an {@code EditMeetingDescriptor} object based on the meeting details to be edited.
         */
        public EditMeetingDescriptor(EditMeetingDescriptor toCopy) {
            setMeetingName(toCopy.meetingName);
            setVenue(toCopy.venue);
            setWhen(toCopy.when);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(meetingName, venue, when);
        }

        public void setMeetingName(MeetingName meetingName) {
            this.meetingName = meetingName;
        }

        public Optional<MeetingName> getMeetingName() {
            return Optional.ofNullable(meetingName);
        }

        public void setVenue(Venue venue) {
            this.venue = venue;
        }

        public Optional<Venue> getVenue() {
            return Optional.ofNullable(venue);
        }

        public void setWhen(When when) {
            this.when = when;
        }

        public Optional<When> getWhen() {
            return Optional.ofNullable(when);
        }

        @Override
        public boolean equals(Object other) {
            if (other == this) {
                return true;
            }

            // instanceof handles null
            if (!(other instanceof EditMeetingDescriptor)) {
                return false;
            }

            EditMeetingDescriptor otherEditMeetingDescriptor = (EditMeetingDescriptor) other;
            return Objects.equals(meetingName, otherEditMeetingDescriptor.meetingName)
                    && Objects.equals(venue, otherEditMeetingDescriptor.venue)
                    && Objects.equals(when, otherEditMeetingDescriptor.when);
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this)
                    .add("meetingName", meetingName)
                    .add("venue", venue)
                    .add("when", when)
                    .toString();
        }
    }
}
