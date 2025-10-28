package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SEVENTH_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;

public class FlagCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() {
        // test the boundary index values by getting the last (7th) person
        Person personToFlag = model.getPersonList().get(INDEX_SEVENTH_PERSON.getZeroBased());
        FlagCommand flagCommand = new FlagCommand(INDEX_SEVENTH_PERSON);

        String expectedMessage = String.format(FlagCommand.MESSAGE_FLAG_PERSON_SUCCESS,
                Messages.format(personToFlag));

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        Person flaggedPerson = new PersonBuilder(personToFlag).withFlagStatus(true).build();
        expectedModel.setPerson(personToFlag, flaggedPerson);

        assertCommandSuccess(flagCommand, model, expectedMessage, expectedModel);

        // Checks that flagged third person has been moved to first position to ensure flag functionality
        assertEquals(flaggedPerson, model.getPersonList().get(INDEX_FIRST_PERSON.getZeroBased()));
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getPersonList().size() + 1);
        FlagCommand flagCommand = new FlagCommand(outOfBoundIndex);

        assertCommandFailure(flagCommand, model, FlagCommand.MESSAGE_INVALID_INDEX);
    }

    @Test
    public void execute_alreadyFlaggedUnfilteredList_throwsCommandException() {
        Person personToFlag = model.getPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        model.setPerson(personToFlag, new PersonBuilder(personToFlag).withFlagStatus(true).build());
        FlagCommand flagCommand = new FlagCommand(INDEX_FIRST_PERSON);

        assertCommandFailure(flagCommand, model, FlagCommand.MESSAGE_ALREADY_FLAGGED);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        Person personToFlag = model.getPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        FlagCommand flagCommand = new FlagCommand(INDEX_FIRST_PERSON);

        String expectedMessage = String.format(FlagCommand.MESSAGE_FLAG_PERSON_SUCCESS,
                Messages.format(personToFlag));

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.setPerson(personToFlag, new PersonBuilder(personToFlag).withFlagStatus(true).build());
        showPersonAtIndex(expectedModel, INDEX_FIRST_PERSON);

        assertCommandSuccess(flagCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        Index outOfBoundIndex = INDEX_SECOND_PERSON;

        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getPersonList().size());

        FlagCommand flagCommand = new FlagCommand(outOfBoundIndex);

        assertCommandFailure(flagCommand, model, FlagCommand.MESSAGE_INVALID_INDEX);
    }

    @Test
    public void equals() {
        FlagCommand flagFirstCommand = new FlagCommand(INDEX_FIRST_PERSON);
        FlagCommand flagSecondCommand = new FlagCommand(INDEX_SECOND_PERSON);

        // same object -> returns true
        assertTrue(flagFirstCommand.equals(flagFirstCommand));

        // same values -> returns true
        FlagCommand flagFirstCommandCopy = new FlagCommand(INDEX_FIRST_PERSON);
        assertTrue(flagFirstCommand.equals(flagFirstCommandCopy));

        // different types -> returns false
        assertFalse(flagFirstCommand.equals(1));

        // null -> returns false
        assertFalse(flagFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(flagFirstCommand.equals(flagSecondCommand));
    }

    @Test
    public void toStringMethod() {
        Index targetIndex = Index.fromOneBased(1);
        FlagCommand flagCommand = new FlagCommand(targetIndex);
        String expected = FlagCommand.class.getCanonicalName() + "{targetIndex=" + targetIndex + "}";
        assertEquals(expected, flagCommand.toString());
    }

}
