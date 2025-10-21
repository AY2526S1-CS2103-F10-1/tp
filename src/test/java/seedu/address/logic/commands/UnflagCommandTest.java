package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;

public class UnflagCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @BeforeEach
    public void setUp() {
        // Flag the first person for testing unflag functionality
        Person personToFlag = model.getPersonList().get(INDEX_THIRD_PERSON.getZeroBased());
        model.setPerson(personToFlag,
                new PersonBuilder(personToFlag).withFlagStatus(true).build());
    }

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Person personToUnflag = model.getPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        UnflagCommand unflagCommand = new UnflagCommand(INDEX_FIRST_PERSON);

        String expectedMessage = String.format(UnflagCommand.MESSAGE_FLAG_PERSON_SUCCESS,
                Messages.format(personToUnflag));

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        Person unflaggedPerson = new PersonBuilder(personToUnflag).withFlagStatus(false).build();
        expectedModel.setPerson(personToUnflag, unflaggedPerson);

        assertCommandSuccess(unflagCommand, model, expectedMessage, expectedModel);

        // Checks that unflagged person has been moved to original third position to ensure unflag functionality
        assertEquals(unflaggedPerson, model.getPersonList().get(INDEX_THIRD_PERSON.getZeroBased()));
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getPersonList().size() + 1);
        UnflagCommand unflagCommand = new UnflagCommand(outOfBoundIndex);

        assertCommandFailure(unflagCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_alreadyUnflaggedUnfilteredList_throwsCommandException() {
        UnflagCommand unflagCommand = new UnflagCommand(INDEX_SECOND_PERSON);

        assertCommandFailure(unflagCommand, model, UnflagCommand.MESSAGE_ALREADY_FLAGGED);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        Person personToUnflag = model.getPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        UnflagCommand unflagCommand = new UnflagCommand(INDEX_FIRST_PERSON);

        String expectedMessage = String.format(UnflagCommand.MESSAGE_FLAG_PERSON_SUCCESS,
                Messages.format(personToUnflag));

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.setPerson(personToUnflag, new PersonBuilder(personToUnflag).withFlagStatus(false).build());
        showPersonAtIndex(expectedModel, INDEX_THIRD_PERSON);

        assertCommandSuccess(unflagCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        Index outOfBoundIndex = INDEX_SECOND_PERSON;

        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getPersonList().size());

        UnflagCommand unflagCommand = new UnflagCommand(outOfBoundIndex);

        assertCommandFailure(unflagCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
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
        UnflagCommand unflagCommand = new UnflagCommand(targetIndex);
        String expected = UnflagCommand.class.getCanonicalName() + "{targetIndex=" + targetIndex + "}";
        assertEquals(expected, unflagCommand.toString());
    }

}
