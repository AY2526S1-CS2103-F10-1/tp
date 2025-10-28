package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.logic.parser.exceptions.ParseException;

public class OtherPhonesTest {

    @Test
    public void constructor_invalidPhone_throwsIllegalArgumentException() {
        String invalidPhone = "+65";
        assertThrows(IllegalArgumentException.class, () -> new OtherPhones(invalidPhone));
    }

    @Test
    public void isValidPhone() throws ParseException {
        // null phone number
        assertThrows(NullPointerException.class, () -> Phone.isValidPhone(null));

        // invalid phone numbers
        assertFalse(OtherPhones.isValidPhone("91")); // less than 3 numbers
        assertFalse(OtherPhones.isValidPhone("phone")); // non-numeric
        assertFalse(OtherPhones.isValidPhone("9011p041")); // alphabets within digits

        assertThrows(ParseException.class, () -> OtherPhones.isValidPhone("9312 1534 (work) 9999"));


        // valid phone numbers
        assertTrue(OtherPhones.isValidPhone("")); // empty string
        assertTrue(OtherPhones.isValidPhone(" ")); // spaces only
        assertTrue(OtherPhones.isValidPhone("911")); // exactly 3 numbers
        assertTrue(OtherPhones.isValidPhone("93121534"));
        assertTrue(OtherPhones.isValidPhone("124293842033123")); // long phone numbers
        assertTrue(OtherPhones.isValidPhone("12429 (work) 99999 (office)")); // long phone numbers
    }

    @Test
    public void mainPhoneExist() throws ParseException {
        Phone mainPhone = new Phone("999");

        // invalid phone numbers
        assertFalse(OtherPhones.mainPhoneExists("", mainPhone)); // empty string
        assertFalse(OtherPhones.mainPhoneExists(" ", mainPhone)); // spaces only
        assertFalse(OtherPhones.mainPhoneExists("9312 1534", mainPhone)); // spaces within digits
        assertFalse(OtherPhones.mainPhoneExists("9312 1534 (work) 9999 (test)",
                mainPhone)); // each phone number must have a tag

        // valid phone numbers
        assertTrue(OtherPhones.mainPhoneExists("999", mainPhone)); // exactly 3 numbers
        assertTrue(OtherPhones.mainPhoneExists("999 (test)", mainPhone));
        assertTrue(OtherPhones.mainPhoneExists("998 (work) 999 (test)", mainPhone)); // long phone numbers
    }
    @Test
    public void equals() {
        OtherPhones phone = new OtherPhones("999");

        // same values -> returns true
        assertTrue(phone.equals(new OtherPhones("999")));

        // same object -> returns true
        assertTrue(phone.equals(phone));

        // null -> returns false
        assertFalse(phone.equals(null));

        // different types -> returns false
        assertFalse(phone.equals(5.0f));

        // different values -> returns false
        assertFalse(phone.equals(new OtherPhones("995")));
    }
}
