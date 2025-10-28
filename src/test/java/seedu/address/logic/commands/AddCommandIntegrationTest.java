package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.logic.parser.AddCommandParser;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;

/**
 * Contains integration tests (interaction with the Model) for {@code AddCommand}.
 */
public class AddCommandIntegrationTest {

    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_newPerson_success() {
        Person validPerson = new PersonBuilder().build();

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.addPerson(validPerson);

        assertCommandSuccess(new AddCommand(validPerson), model,
                String.format(AddCommand.MESSAGE_SUCCESS, Messages.format(validPerson)),
                expectedModel);
    }

    @Test
    public void addCommand_validName_personAddedToModel() throws Exception {
        AddCommandParser parser = new AddCommandParser();

        AddCommand command = parser.parse(" n=Jean-Luc mn=91234567 e=test@example.com a=123 Street");
        command.execute(model);

        int addedPersonIndex = model.getPersonList().size() - 1;
        Person addedPerson = model.getPersonList().get(addedPersonIndex);
        assertEquals("Jean-Luc", addedPerson.getName().toString());
    }

    @Test
    public void addCommand_oneValidEmail_personAddedToModel() throws Exception {
        AddCommandParser parser = new AddCommandParser();

        AddCommand command = parser.parse(" n=Jean-Luc mn=91234567 e=test@example.com a=123 Street t=Friend");
        command.execute(model);

        int addedPersonIndex = model.getPersonList().size() - 1;
        Person addedPerson = model.getPersonList().get(addedPersonIndex);
        assertEquals("test@example.com", addedPerson.getEmail().toString());
    }

    @Test
    public void addCommand_oneValidEmailWithLabel_personAddedToModel() throws Exception {
        AddCommandParser parser = new AddCommandParser();

        AddCommand command = parser.parse(" n=Jean-Luc mn=91234567 e=test@example.com (main) "
                + "a=123 Street t=Friend");
        command.execute(model);

        int addedPersonIndex = model.getPersonList().size() - 1;
        Person addedPerson = model.getPersonList().get(addedPersonIndex);
        assertEquals("test@example.com (main)", addedPerson.getEmail().toString());
    }

    @Test
    public void addCommand_multipleValidEmail_personAddedToModel() throws Exception {
        AddCommandParser parser = new AddCommandParser();

        AddCommand command = parser.parse(" n=Jean-Luc mn=91234567 e=test@example.com (main) "
                + "john@work.com (work) a=123 Street t=Friend");
        command.execute(model);

        int addedPersonIndex = model.getPersonList().size() - 1;
        Person addedPerson = model.getPersonList().get(addedPersonIndex);
        assertEquals("test@example.com (main) john@work.com (work)",
                addedPerson.getEmail().toString());
    }

    @Test
    public void addCommand_oneValidAddress_personAddedToModel() throws Exception {
        AddCommandParser parser = new AddCommandParser();

        AddCommand command = parser.parse(" n=Jean-Luc mn=91234567 e=test@example.com "
                + "a=Kent Ridge Road, #01-23 t=Friend");
        command.execute(model);

        int addedPersonIndex = model.getPersonList().size() - 1;
        Person addedPerson = model.getPersonList().get(addedPersonIndex);
        assertEquals("Kent Ridge Road, #01-23", addedPerson.getAddress().toString());
    }

    @Test
    public void addCommand_oneValidAddressWithLabel_personAddedToModel() throws Exception {
        AddCommandParser parser = new AddCommandParser();

        AddCommand command = parser.parse(" n=Jean-Luc mn=91234567 e=test@example.com "
                + "a=Kent Ridge Road, #01-23 (School) t=Friend");
        command.execute(model);

        int addedPersonIndex = model.getPersonList().size() - 1;
        Person addedPerson = model.getPersonList().get(addedPersonIndex);
        assertEquals("Kent Ridge Road, #01-23 (School)", addedPerson.getAddress().toString());
    }

    @Test
    public void addCommand_multipleValidAddressWithLabel_personAddedToModel() throws Exception {
        AddCommandParser parser = new AddCommandParser();

        AddCommand command = parser.parse(" n=Jean-Luc mn=91234567 e=test@example.com "
                + "a=Kent Ridge Road, #01-23 (School) Istana (House) t=Friend");
        command.execute(model);

        int addedPersonIndex = model.getPersonList().size() - 1;
        Person addedPerson = model.getPersonList().get(addedPersonIndex);
        assertEquals("Kent Ridge Road, #01-23 (School) Istana (House)",
                addedPerson.getAddress().toString());
    }

    @Test
    public void execute_duplicatePerson_throwsCommandException() {
        Person personInList = model.getAddressBook().getPersonList().get(0);
        assertCommandFailure(new AddCommand(personInList), model,
                AddCommand.MESSAGE_DUPLICATE_PERSON);
    }

    @Test
    public void addCommand_otherNumbers_personAddedToModel() throws Exception {
        AddCommandParser parser = new AddCommandParser();

        AddCommand command = parser.parse(" n=Jean-Luc mn=91234567 on=9999 e=test@example.com (main) "
                + "john@work.com (work) a=123 Street t=Friend");
        command.execute(model);

        int addedPersonIndex = model.getPersonList().size() - 1;
        Person addedPerson = model.getPersonList().get(addedPersonIndex);
        assertEquals("9999",
                addedPerson.getOtherPhones().toString());
    }

    @Test
    public void addCommand_multipleOtherNumbers_personAddedToModel() throws Exception {
        AddCommandParser parser = new AddCommandParser();

        AddCommand command = parser.parse(" n=Jean-Luc mn=91234567 "
                + "on=9999 (work) 99998 (test) e=test@example.com (main) "
                + "john@work.com (work) a=123 Street t=Friend");
        command.execute(model);

        int addedPersonIndex = model.getPersonList().size() - 1;
        Person addedPerson = model.getPersonList().get(addedPersonIndex);
        assertEquals("9999 (work) 99998 (test)",
                addedPerson.getOtherPhones().toString());

    }

}
