package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.logic.parser.exceptions.ParseException;

public class AddressTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Address(null));
    }

    @Test
    public void constructor_invalidAddress_throwsIllegalArgumentException() {
        String invalidAddress = "";
        assertThrows(IllegalArgumentException.class, () -> new Address(invalidAddress));
    }

    @Test
    public void constructor_invalidAddressAndLabel_doesNotThrowParseException() {
        String invalidAddress = "Kent Ridge Vale Street 55, #01-23(test)";
        assertDoesNotThrow(() -> new Address(invalidAddress));
    }

    @Test
    public void isValidAddress_invalidAddressOrLabels_throwParseException() {
        assertThrows(NullPointerException.class, () -> Address.isValidAddress(null)); // null address

        assertThrows(ParseException.class, () ->
                Address.isValidAddress("Kent Ridge (School) Jurong East(Home)")); // Label together with address

        assertThrows(ParseException.class, () ->
                Address.isValidAddress("Kent Ridge (home) (school)")); // Another address missing

        assertThrows(ParseException.class, () ->
                Address.isValidAddress("Kent Ridge (home) "
                        + "Jurong (home)")); // duplicate labels

        assertThrows(ParseException.class, () ->
                Address.isValidAddress("Kent Ridge (home) "
                        + "Kent Ridge (school)")); // duplicate addresses

        assertThrows(ParseException.class, () ->
                Address.isValidAddress("Kent Ridge (home) "
                        + "Jurong (hOMe)")); // duplicate labels to test case-insensitivity

        assertThrows(ParseException.class, () ->
                Address.isValidAddress("Kent Ridge (home) "
                        + "kEnT RIDge (school)")); // duplicate addresses to test case-insensitivity
    }

    @Test
    public void isValidAddress_validAddressAndLabels_returnTrue() {
        try {
            assertTrue(Address.isValidAddress("Blk 456, Den Road, #01-355"));
            assertTrue(Address.isValidAddress("-")); // one character
            assertTrue(Address.isValidAddress("Leng Inc; 1234 Market St; "
                    + "San Francisco CA 2349879; USA")); // long address
            assertTrue(Address.isValidAddress("Blk 456, Den Road, #01-355 (House)")); // 1 Address with label
            assertTrue(Address.isValidAddress("Blk 456, Den Road, #01-355 (House) "
                    + "Jurong Town, #09-355 (School) Blk 123, O Road, #01-355 (Work)")); // Multiple Address with label
            assertTrue(Address.isValidAddress(" Blk 456, Den Road, "
                    + "#01-355 (House) ")); // Trailing spaces allowed
            assertTrue(Address.isValidAddress(" Blk 456, Den Road, "
                    + "#01-355 (House 2) ")); // Space allowed in label
        } catch (ParseException e) {
            fail();
        }
    }

    @Test
    public void isValidAddress_invalidAddressOrLabels_returnFalse() {
        try {
            assertFalse(Address.isValidAddress("")); // empty string
            assertFalse(Address.isValidAddress(" ")); // spaces only
            assertFalse(Address.isValidAddress("Kent Ridge ()")); // Empty label
            assertFalse(Address.isValidAddress("Kent Ridge (  )")); // Label with spaces only
            assertFalse(Address.isValidAddress("Kent Ridge (--)")); // Label with hyphen only
            assertFalse(Address.isValidAddress("Kent Ridge ( -- - )")); // Label with spaces and hyphens only
            assertFalse(Address.isValidAddress("Kent Ridge (Test / No)")); // Label other characters
        } catch (ParseException e) {
            fail();
        }
    }

    @Test
    public void equals() {
        Address address = new Address("Valid Address");

        // same values -> returns true
        assertTrue(address.equals(new Address("Valid Address")));


        // same object -> returns true
        assertTrue(address.equals(address));

        // null -> returns false
        assertFalse(address.equals(null));

        // different types -> returns false
        assertFalse(address.equals(5.0f));

        // different values -> returns false
        assertFalse(address.equals(new Address("Other Valid Address")));
    }
}
