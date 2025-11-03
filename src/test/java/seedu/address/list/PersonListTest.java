package seedu.address.list;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIFTH_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_FOURTH_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SIXTH_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD_PERSON;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BENSON;
import static seedu.address.testutil.TypicalPersons.DANIEL;
import static seedu.address.testutil.TypicalPersons.FIONA;
import static seedu.address.testutil.TypicalPersons.HOON;
import static seedu.address.testutil.TypicalPersons.IDA;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javafx.collections.ObservableList;
import seedu.address.model.AddressBook;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;

public class PersonListTest {
    private ModelManager model;

    @BeforeEach
    public void setUp() {
        AddressBook addressBook = new AddressBook();
        addressBook.addPerson(BENSON);
        addressBook.addPerson(IDA);
        addressBook.addPerson(DANIEL);
        addressBook.addPerson(FIONA);
        addressBook.addPerson(ALICE);
        addressBook.addPerson(HOON);
        model = new ModelManager(addressBook, new UserPrefs());
    }

    @Test
    public void updatePersonListFilter_validPredicate_personListUpdated() {
        // Replace the third person with a flagged person
        Person person = new PersonBuilder().withFlagStatus(true).build();
        Person unflaggedPerson = model.getPersonList().get(INDEX_THIRD_PERSON.getZeroBased());
        model.setPerson(unflaggedPerson, person);

        // Update the person list filter to show only flagged persons
        model.updatePersonListFilter(p -> p.isFlagged());

        // Check that the person list only contains the single flagged person
        assertEquals(model.getPersonList().size(), 1);
        assertEquals(model.getPersonList().get(INDEX_FIRST_PERSON.getZeroBased()),
                person);
    }

    @Test
    public void updatePersonListFilter_nonMatchingPredicate_personListUpdated() {
        // Update the person list filter to show only flagged persons
        model.updatePersonListFilter(p -> p.isFlagged());

        // Check that the person list is empty
        assertEquals(model.getPersonList().size(), 0);
    }

    @Test
    public void getPersonList_withFlaggedStatus_orderPreserved() {

        Person firstPerson = model.getPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person secondPerson = model.getPersonList().get(INDEX_SECOND_PERSON.getZeroBased());
        Person thirdPerson = model.getPersonList().get(INDEX_THIRD_PERSON.getZeroBased());
        Person fourthPerson = model.getPersonList().get(INDEX_FOURTH_PERSON.getZeroBased());
        Person fifthPerson = model.getPersonList().get(INDEX_FIFTH_PERSON.getZeroBased());
        Person sixthPerson = model.getPersonList().get(INDEX_SIXTH_PERSON.getZeroBased());

        // Flag the first, third and fifth person
        Person firstPersonFlagged = new PersonBuilder(firstPerson).withFlagStatus(true).build();
        Person thirdPersonFlagged = new PersonBuilder(thirdPerson).withFlagStatus(true).build();
        Person fifthPersonFlagged = new PersonBuilder(fifthPerson).withFlagStatus(true).build();
        model.setPerson(firstPerson, firstPersonFlagged);
        model.setPerson(thirdPerson, thirdPersonFlagged);
        model.setPerson(fifthPerson, fifthPersonFlagged);

        // Get the sorted person list
        ObservableList<Person> sortedPersonList = model.getPersonList();

        // Check that the flagged persons are at the top in the original order
        assertEquals(sortedPersonList.get(INDEX_FIRST_PERSON.getZeroBased()), firstPersonFlagged);
        assertEquals(sortedPersonList.get(INDEX_SECOND_PERSON.getZeroBased()), thirdPersonFlagged);
        assertEquals(sortedPersonList.get(INDEX_THIRD_PERSON.getZeroBased()), fifthPersonFlagged);

        // Check that the unflagged persons are at the bottom in the original order
        assertEquals(sortedPersonList.get(INDEX_FOURTH_PERSON.getZeroBased()), secondPerson);
        assertEquals(sortedPersonList.get(INDEX_FIFTH_PERSON.getZeroBased()), fourthPerson);
        assertEquals(sortedPersonList.get(INDEX_SIXTH_PERSON.getZeroBased()), sixthPerson);
    }

    @Test
    public void equals() {
        PersonList firstPersonList = new PersonList(model.getAddressBook().getPersonList());
        PersonList secondPersonList = new PersonList(model.getAddressBook().getPersonList());

        // same object -> returns true
        assertEquals(firstPersonList, firstPersonList);

        // different types -> returns false
        assertFalse(firstPersonList.equals(1));

        // same values -> returns true
        assertEquals(firstPersonList, secondPersonList);
    }
}
