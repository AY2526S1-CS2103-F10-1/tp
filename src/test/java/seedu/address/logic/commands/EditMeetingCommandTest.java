package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.address.logic.commands.EditMeetingCommand.EditMeetingDescriptor;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_MEETING;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_MEETING;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.meeting.Meeting;
import seedu.address.model.meeting.MeetingName;
import seedu.address.model.meeting.Venue;
import seedu.address.model.meeting.When;
import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;

public class EditMeetingCommandTest {

    private static final String MEETING_NAME_STUB = "Some meeting";
    private static final String VENUE_STUB = "Some venue";
    private static final String WHEN_STUB = "2025-10-19 1500";
    private static final String MEETING_NAME_STUB_2 = "Some meeting 2";
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private Venue venue;
    private When when;


    @BeforeEach
    public void setUp() throws Exception {
        MeetingName meetingName = new MeetingName(MEETING_NAME_STUB);
        venue = new Venue(VENUE_STUB);
        when = new When(WHEN_STUB);

        Person firstPerson = model.getPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person editedPerson = new PersonBuilder(firstPerson).withMeetings(
                new Meeting(meetingName, venue, when)).build();
        model.setPerson(firstPerson, editedPerson);
    }

    @Test
    public void execute_editMeetingUnfilteredList_success() throws Exception {
        Person firstPerson = model.getPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        MeetingName updatedMeetingName = new MeetingName(MEETING_NAME_STUB_2);
        Person editedPerson = new PersonBuilder(firstPerson).withMeetings(
                new Meeting(updatedMeetingName, venue, when)).build();

        EditMeetingDescriptor editMeetingDescriptor = new EditMeetingDescriptor();
        editMeetingDescriptor.setMeetingName(updatedMeetingName);

        EditMeetingCommand editMeetingCommand = new EditMeetingCommand(INDEX_FIRST_PERSON, INDEX_FIRST_MEETING,
                editMeetingDescriptor);

        Meeting editedMeeting = editedPerson.getMeetings().get(INDEX_FIRST_MEETING.getZeroBased());

        String expectedMessage = String.format(EditMeetingCommand.MESSAGE_EDIT_MEETING_SUCCESS,
                Messages.format(editedMeeting));

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.setPerson(firstPerson, editedPerson);

        assertCommandSuccess(editMeetingCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_editMeetingFilteredList_success() throws Exception {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        Person firstPerson = model.getPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        MeetingName updatedMeetingName = new MeetingName(MEETING_NAME_STUB_2);
        Person editedPerson = new PersonBuilder(firstPerson).withMeetings(
                new Meeting(updatedMeetingName, venue, when)).build();

        EditMeetingDescriptor editMeetingDescriptor = new EditMeetingDescriptor();
        editMeetingDescriptor.setMeetingName(updatedMeetingName);

        EditMeetingCommand editMeetingCommand = new EditMeetingCommand(INDEX_FIRST_PERSON, INDEX_FIRST_MEETING,
                editMeetingDescriptor);

        Meeting editedMeeting = editedPerson.getMeetings().get(INDEX_FIRST_MEETING.getZeroBased());

        String expectedMessage = String.format(EditMeetingCommand.MESSAGE_EDIT_MEETING_SUCCESS,
                Messages.format(editedMeeting));

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.setPerson(firstPerson, editedPerson);
        showPersonAtIndex(expectedModel, INDEX_FIRST_PERSON);

        assertCommandSuccess(editMeetingCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidPersonIndexUnfilteredList_failure() throws Exception {
        Index outOfBoundIndex = Index.fromOneBased(model.getPersonList().size() + 1);

        MeetingName updatedMeetingName = new MeetingName(MEETING_NAME_STUB_2);

        EditMeetingDescriptor editMeetingDescriptor = new EditMeetingDescriptor();
        editMeetingDescriptor.setMeetingName(updatedMeetingName);

        EditMeetingCommand editMeetingCommand = new EditMeetingCommand(outOfBoundIndex, INDEX_FIRST_MEETING,
                editMeetingDescriptor);

        assertCommandFailure(editMeetingCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_invalidPersonIndexFilteredList_failure() throws Exception {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);
        Index outOfBoundIndex = INDEX_SECOND_PERSON;

        MeetingName updatedMeetingName = new MeetingName(MEETING_NAME_STUB_2);

        EditMeetingDescriptor editMeetingDescriptor = new EditMeetingDescriptor();
        editMeetingDescriptor.setMeetingName(updatedMeetingName);

        EditMeetingCommand editMeetingCommand = new EditMeetingCommand(outOfBoundIndex, INDEX_FIRST_MEETING,
                editMeetingDescriptor);

        assertCommandFailure(editMeetingCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_invalidMeetingIndexUnfilteredList_failure() throws Exception {
        Person person = model.getPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Index outOfBoundIndex = Index.fromZeroBased(person.getMeetingCount() + 1);

        MeetingName updatedMeetingName = new MeetingName(MEETING_NAME_STUB_2);

        EditMeetingDescriptor editMeetingDescriptor = new EditMeetingDescriptor();
        editMeetingDescriptor.setMeetingName(updatedMeetingName);

        EditMeetingCommand editMeetingCommand = new EditMeetingCommand(INDEX_FIRST_PERSON, outOfBoundIndex,
                editMeetingDescriptor);

        assertCommandFailure(editMeetingCommand, model, Messages.MESSAGE_INVALID_MEETING_DISPLAYED_INDEX);
    }

    @Test
    public void execute_invalidMeetingIndexFilteredList_failure() throws Exception {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);
        Person person = model.getPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Index outOfBoundIndex = Index.fromZeroBased(person.getMeetingCount() + 1);

        MeetingName updatedMeetingName = new MeetingName(MEETING_NAME_STUB_2);

        EditMeetingDescriptor editMeetingDescriptor = new EditMeetingDescriptor();
        editMeetingDescriptor.setMeetingName(updatedMeetingName);

        EditMeetingCommand editMeetingCommand = new EditMeetingCommand(INDEX_FIRST_PERSON, outOfBoundIndex,
                editMeetingDescriptor);

        assertCommandFailure(editMeetingCommand, model, Messages.MESSAGE_INVALID_MEETING_DISPLAYED_INDEX);
    }

    @Test
    public void equals() throws Exception {
        MeetingName meetingName = new MeetingName(MEETING_NAME_STUB);
        Meeting meeting = new Meeting(meetingName, venue, when);
        MeetingName diffMeetingName = new MeetingName(MEETING_NAME_STUB_2);

        Meeting anotherMeeting = new Meeting(diffMeetingName, venue, when);
        EditMeetingDescriptor editMeetingDescriptor = new EditMeetingDescriptor();
        editMeetingDescriptor.setMeetingName(anotherMeeting.getMeetingName());
        EditMeetingCommand editMeetingCommand = new EditMeetingCommand(INDEX_FIRST_PERSON, INDEX_FIRST_MEETING,
                editMeetingDescriptor);
        EditMeetingCommand sameEditMeetingCommand = new EditMeetingCommand(INDEX_FIRST_PERSON, INDEX_FIRST_MEETING,
                editMeetingDescriptor);
        EditMeetingCommand editMeetingCommandWithDiffPerson = new EditMeetingCommand(INDEX_SECOND_PERSON,
                INDEX_FIRST_MEETING, editMeetingDescriptor);
        EditMeetingCommand editMeetingCommandWithDiffMeeting = new EditMeetingCommand(INDEX_FIRST_PERSON,
                INDEX_SECOND_MEETING, editMeetingDescriptor);

        EditMeetingDescriptor anotherEditMeetingDescriptor = new EditMeetingDescriptor();

        assertTrue(editMeetingCommand.equals(editMeetingCommand));

        assertTrue(editMeetingCommand.equals(sameEditMeetingCommand));

        assertFalse(editMeetingCommand.equals(editMeetingCommandWithDiffPerson));

        assertFalse(editMeetingCommand.equals(editMeetingCommandWithDiffMeeting));

        assertFalse(editMeetingCommand.equals(null));

        assertFalse(editMeetingCommand.equals(new AddMeetingCommand(INDEX_FIRST_PERSON, meeting)));

        assertFalse(editMeetingCommand.equals(new EditMeetingCommand(INDEX_FIRST_PERSON, INDEX_FIRST_MEETING,
                anotherEditMeetingDescriptor)));
    }
}
