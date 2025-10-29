package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_MEETING;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_MEETING;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.meeting.Meeting;
import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;

/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * {@code DeleteMeetingCommand}.
 */
public class DeleteMeetingCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private Meeting firstMeetingStub;
    private Meeting secondMeetingStub2;

    @BeforeEach
    public void setUp() {
        try {
            firstMeetingStub = new Meeting(
                    "Some meeting 1",
                    "Some venue 1",
                    "2025-10-11 1400");
            secondMeetingStub2 = new Meeting(
                    "Some meeting 2",
                    "Some venue 2",
                    "2025-12-12 1800");
        } catch (ParseException ignored) {
            // This exception is expected and can be safely ignored
        }

        Person firstPerson = model.getPersonList()
                .get(INDEX_FIRST_PERSON.getZeroBased());
        Person editedPerson = new PersonBuilder(firstPerson)
                .withMeetings(firstMeetingStub, secondMeetingStub2)
                .build();

        model.setPerson(firstPerson, editedPerson);
    }

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Person firstPerson = model.getPersonList().get(INDEX_FIRST_PERSON.getZeroBased());

        Meeting meetingToDelete = firstPerson
                .getMeetings()
                .get(INDEX_FIRST_MEETING.getZeroBased());
        Meeting secondMeeting = firstPerson
                .getMeetings()
                .get(INDEX_SECOND_MEETING.getZeroBased());

        Person editedPerson = new PersonBuilder(firstPerson)
                .withMeetings(secondMeeting)
                .build();

        DeleteMeetingCommand deleteMeetingCommand = new DeleteMeetingCommand(
                INDEX_FIRST_PERSON, INDEX_FIRST_MEETING);

        String expectedMessage = String.format(
                DeleteMeetingCommand.MESSAGE_DELETE_MEETING_SUCCESS,
                Messages.format(meetingToDelete),
                Messages.format(firstPerson));

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.setPerson(firstPerson, editedPerson);

        assertCommandSuccess(deleteMeetingCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        Person firstPerson = model.getPersonList().get(INDEX_FIRST_PERSON.getZeroBased());

        Meeting meetingToDelete = firstPerson
                .getMeetings()
                .get(INDEX_FIRST_MEETING.getZeroBased());
        Meeting secondMeeting = firstPerson
                .getMeetings()
                .get(INDEX_SECOND_MEETING.getZeroBased());

        Person editedPerson = new PersonBuilder(firstPerson)
                .withMeetings(secondMeeting)
                .build();

        DeleteMeetingCommand deleteMeetingCommand = new DeleteMeetingCommand(
                INDEX_FIRST_PERSON, INDEX_FIRST_MEETING);

        String expectedMessage = String.format(
                DeleteMeetingCommand.MESSAGE_DELETE_MEETING_SUCCESS,
                Messages.format(meetingToDelete),
                Messages.format(firstPerson));

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.setPerson(firstPerson, editedPerson);
        showPersonAtIndex(expectedModel, INDEX_FIRST_PERSON);

        assertCommandSuccess(deleteMeetingCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getPersonList().size() + 1);
        DeleteMeetingCommand deleteMeetingCommand = new DeleteMeetingCommand(outOfBoundIndex, INDEX_FIRST_MEETING);

        assertCommandFailure(deleteMeetingCommand, model,
                DeleteMeetingCommand.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_invalidIndexMeeting_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getPersonList()
                .get(INDEX_FIRST_PERSON.getZeroBased()).getMeetingCount() + 1);
        DeleteMeetingCommand deleteMeetingCommand = new DeleteMeetingCommand(INDEX_FIRST_PERSON, outOfBoundIndex);

        assertCommandFailure(deleteMeetingCommand, model,
                DeleteMeetingCommand.MESSAGE_INVALID_MEETING_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        DeleteMeetingCommand deleteFirstCommand = new DeleteMeetingCommand(
                INDEX_FIRST_PERSON, INDEX_FIRST_MEETING);
        DeleteMeetingCommand deleteSecondCommand = new DeleteMeetingCommand(
                INDEX_FIRST_PERSON, INDEX_SECOND_MEETING);

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // same values -> returns true
        DeleteMeetingCommand deleteFirstMeetingCommandCopy = new DeleteMeetingCommand(
                INDEX_FIRST_PERSON,
                INDEX_FIRST_MEETING);
        assertTrue(deleteFirstCommand.equals(deleteFirstMeetingCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(deleteFirstCommand.equals(deleteSecondCommand));
    }

    @Test
    public void toStringMethod() {
        Index personIndex = Index.fromOneBased(1);
        Index meetingIndex = Index.fromOneBased(1);
        DeleteMeetingCommand deleteMeetingCommand = new DeleteMeetingCommand(personIndex, meetingIndex);
        String expected = DeleteMeetingCommand.class.getCanonicalName() + "{personIndex=" + personIndex
                + ", " + "meetingIndex=" + meetingIndex + "}";
        assertEquals(expected, deleteMeetingCommand.toString());
    }

}
