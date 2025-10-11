package seedu.address.logic.commands;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MEETING;
import static seedu.address.logic.parser.CliSyntax.PREFIX_VENUE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_WHEN;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.ArrayList;
import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.meeting.Meeting;
import seedu.address.model.person.Person;

/**
 * Adds a meeting to an existing person in the address book.
 */
public class AddMeetingCommand extends Command {
    public static final String COMMAND_WORD = "addmeeting";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a meeting for the person identified "
            + "by the index number used in the last person listing. "
            + "A new meeting would be added to the person's list of current meetings.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + PREFIX_MEETING + "MEETING "
            + PREFIX_VENUE + "VENUE "
            + PREFIX_WHEN + "WHEN\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_MEETING + "Financial advice sharing "
            + PREFIX_VENUE + "AMK Hub "
            + PREFIX_WHEN + "2025-11-01 1600";

    public static final String MESSAGE_ADD_MEETING_SUCCESS = "Added meeting to Person: %1$s";
    public static final String MESSAGE_ADD_MEETING_FAILURE = "Failed to add meeting to Person: %1$s";
    public static final String MESSAGE_BLANK_MEETING_NAME = "Name of meeting is missing";
    public static final String MESSAGE_BLANK_VENUE = "Venue of meeting is missing";
    public static final String MESSAGE_BLANK_DATETIME = "Date and time of meeting is missing";

    private final Index index;
    private final Meeting meeting;

    /**
     * Creates an AddMeetingCommand to add a {@code Meeting}
     * @param index of the person in the filtered person list to edit
     * @param meeting details of the meeting to be added
     */
    public AddMeetingCommand(Index index, Meeting meeting) {
        requireAllNonNull(index, meeting);

        this.index = index;
        this.meeting = meeting;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        List<Person> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        if (meeting.meetingName.meetingName.isBlank()) {
            throw new CommandException(MESSAGE_BLANK_MEETING_NAME);
        }

        if (meeting.venue.value.isBlank()) {
            throw new CommandException(MESSAGE_BLANK_VENUE);
        }

        if (meeting.when.value == null) {
            throw new CommandException(MESSAGE_BLANK_DATETIME);
        }

        Person personToEdit = lastShownList.get(index.getZeroBased());
        List<Meeting> editedMeetings = new ArrayList<>(personToEdit.getMeetings());
        editedMeetings.add(meeting);
        Person editedPerson = new Person(personToEdit.getName(), personToEdit.getPhone(), personToEdit.getEmail(),
                personToEdit.getAddress(), personToEdit.getTags(), editedMeetings);
        model.setPerson(personToEdit, editedPerson);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);

        return new CommandResult(generateSuccessMessage(editedPerson));
    }

    private String generateSuccessMessage(Person personToEdit) {
        String message = !(meeting.meetingName.meetingName.isBlank() || meeting.venue.value.isBlank()
                || meeting.when.value == null)
                ? MESSAGE_ADD_MEETING_SUCCESS : MESSAGE_ADD_MEETING_FAILURE;
        return String.format(message, Messages.format(personToEdit));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof AddMeetingCommand)) {
            return false;
        }

        AddMeetingCommand e = (AddMeetingCommand) other;
        return index.equals(e.index)
                && meeting.equals(e.meeting);
    }
}

