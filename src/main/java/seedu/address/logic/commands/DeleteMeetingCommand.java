package seedu.address.logic.commands;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MEETING_INDEX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PERSON_INDEX;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.meeting.Meeting;
import seedu.address.model.person.Person;

/**
 * Deletes a meeting from an existing person in the address book.
 */
public class DeleteMeetingCommand extends Command {

    public static final String COMMAND_WORD = "deletemt";

    public static final String MESSAGE_FORMAT = "Parameters:\n"
            + PREFIX_PERSON_INDEX + "INDEX (must be a positive integer)\n"
            + PREFIX_MEETING_INDEX + "MEETING_INDEX (must be a positive integer)\n";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the meeting for the person identified by the index number used in the displayed person list"
            + " and the index number of the meeting in the person's meeting list. \n"
            + MESSAGE_FORMAT
            + "Example: " + COMMAND_WORD + " " + PREFIX_PERSON_INDEX + "1 " + PREFIX_MEETING_INDEX + "1";

    public static final String MESSAGE_INVALID_MEETING_DISPLAYED_INDEX = "The meeting index provided is invalid";
    public static final String MESSAGE_INVALID_BLANK = "Please provide arguments after the command word.\n"
            + MESSAGE_FORMAT;
    public static final String MESSAGE_INVALID_BLANK_MEETING_INDEX = "Please provide the meeting index.\n"
            + MESSAGE_FORMAT;
    public static final String MESSAGE_INVALID_BLANK_PERSON_INDEX = "Please provide the person index.\n"
            + MESSAGE_FORMAT;
    public static final String MESSAGE_DELETE_MEETING_SUCCESS = "Deleted Meeting: %1$s from Person: %2$s";

    private final Index personIndex;
    private final Index meetingIndex;

    /**
     * @param personIndex of the person in the filtered person list to edit
     * @param meetingIndex of the meeting in the person's meeting list to delete
     */
    public DeleteMeetingCommand(Index personIndex, Index meetingIndex) {
        requireAllNonNull(personIndex, meetingIndex);

        this.personIndex = personIndex;
        this.meetingIndex = meetingIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        List<Person> lastShownList = model.getPersonList();

        if (personIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToEdit = lastShownList.get(personIndex.getZeroBased());

        if (meetingIndex.getZeroBased() >= personToEdit.getMeetingCount()) {
            throw new CommandException(MESSAGE_INVALID_MEETING_DISPLAYED_INDEX);
        }

        // Removes meetings from the person's meeting list
        Meeting meetingToDelete = personToEdit.getMeetings().get(meetingIndex.getZeroBased());
        model.deleteMeetingFromPerson(personToEdit, meetingIndex.getZeroBased());

        // Creates a new Person object with the updated meetings list
        Person editedPerson = new Person(personToEdit.getName(), personToEdit.getPhone(),
                personToEdit.getOtherPhones(), personToEdit.getEmail(),
                personToEdit.getAddress(), personToEdit.getTags(),
                personToEdit.getMeetings(), personToEdit.getFlagStatus());

        // Replaces the old person with the new person in the model to refresh the GUI
        model.setPerson(personToEdit, editedPerson);

        return new CommandResult(String.format(MESSAGE_DELETE_MEETING_SUCCESS,
                Messages.format(meetingToDelete),
                Messages.format(personToEdit)));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof DeleteMeetingCommand)) {
            return false;
        }

        DeleteMeetingCommand otherCommand = (DeleteMeetingCommand) other;
        return personIndex.equals(otherCommand.personIndex)
                && meetingIndex.equals(otherCommand.meetingIndex);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("personIndex", personIndex)
                .add("meetingIndex", meetingIndex)
                .toString();
    }
}
