package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.logic.parser.exceptions.ParseException;

public class EmailTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Email(null));
    }

    @Test
    public void constructor_invalidEmail_throwsIllegalArgumentException() {
        String invalidEmail = "";
        assertThrows(IllegalArgumentException.class, () -> new Email(invalidEmail));
    }

    @Test
    public void constructor_invalidEmailAndLabel_doesNotThrowParseException() {
        String invalidEmail = "test@gmail.com(test)";
        assertDoesNotThrow(() -> new Email(invalidEmail));
    }

    @Test
    public void isValidEmail_invalidEmailOrLabels_throwParseException() {
        assertThrows(NullPointerException.class, () -> Email.isValidEmail(null)); // null email

        assertThrows(ParseException.class, () ->
                Email.isValidEmail("peterjack@example.c(Work)")); // missing space in between label

        assertThrows(ParseException.class, () ->
                Email.isValidEmail("test@gmail.com (test) (work)")); // Another email missing

        assertThrows(ParseException.class, () ->
                Email.isValidEmail("test@gmail.com (main) "
                        + "e1234567@u.nus.edu (main)")); // Duplicate labels

        assertThrows(ParseException.class, () ->
                Email.isValidEmail("test@gmail.com (main) "
                        + "test@gmail.com (school)")); // Duplicate emails

        assertThrows(ParseException.class, () ->
                Email.isValidEmail("test@gmail.com (personal) "
                        + "e1234567@u.nus.edu (pERSOnal)")); // Duplicate labels to test case-insensitivity

        assertThrows(ParseException.class, () ->
                Email.isValidEmail("test@gmail.com (main) "
                        + "TEst@GmAIL.com (school)")); // Duplicate emails to test case-insensitivity
    }

    @Test
    public void isValidEmail_validEmailAndLabels_returnTrue() {
        try {
            // valid email
            assertTrue(Email.isValidEmail("PeterJack_1190@example.com")); // underscore in local part
            assertTrue(Email.isValidEmail("PeterJack.1190@example.com")); // period in local part
            assertTrue(Email.isValidEmail("PeterJack+1190@example.com")); // '+' symbol in local part
            assertTrue(Email.isValidEmail("PeterJack-1190@example.com")); // hyphen in local part
            assertTrue(Email.isValidEmail("a@bc")); // minimal
            assertTrue(Email.isValidEmail("test@localhost")); // alphabets only
            assertTrue(Email.isValidEmail("123@145")); // numeric local part and domain name
            assertTrue(Email.isValidEmail("a1+be.d@example1.com")); // mixture of alphanumeric and special characters
            assertTrue(Email.isValidEmail("peter_jack@very-very-very-long-example.com")); // long domain name
            assertTrue(Email.isValidEmail("if.you.dream.it_you.can.do.it@example.com")); // long local part
            assertTrue(Email.isValidEmail("e1234567@u.nus.edu")); // more than one period in domain
            assertTrue(Email.isValidEmail(" e1234567@u.nus.edu ")); // Trailing spaces are allowed
            assertTrue(Email.isValidEmail("e1234567@u.nus.edu (school)")); // Label allowed with 1 email
            assertTrue(Email.isValidEmail("test@gmail.com (main) "
                    + "e1234567@u.nus.edu (school)")); // Multiple emails with labels allowed
            assertTrue(Email.isValidEmail("test@gmail.com (main) "
                    + "e1234567@u.nus.edu (school-work)")); // Label allowed with space inside
            assertTrue(Email.isValidEmail(" peterjack@example.com")); // leading space is allowed
            assertTrue(Email.isValidEmail("peterjack@example.com ")); // trailing space is allowed
        } catch (ParseException e) {
            fail();
        }
    }

    @Test
    public void isValidEmail_invalidEmailOrLabels_returnFalse() {
        try {
            // blank email
            assertFalse(Email.isValidEmail("")); // empty string
            assertFalse(Email.isValidEmail(" ")); // spaces only

            // missing parts
            assertFalse(Email.isValidEmail("@example.com")); // missing local part
            assertFalse(Email.isValidEmail("peterjackexample.com")); // missing '@' symbol
            assertFalse(Email.isValidEmail("peterjack@")); // missing domain name

            // invalid parts
            assertFalse(Email.isValidEmail("peterjack@-")); // invalid domain name
            assertFalse(Email.isValidEmail("peterjack@exam_ple.com")); // underscore in domain name
            assertFalse(Email.isValidEmail("peter jack@example.com")); // spaces in local part
            assertFalse(Email.isValidEmail("peterjack@exam ple.com")); // spaces in domain name
            assertFalse(Email.isValidEmail("peterjack@@example.com")); // double '@' symbol
            assertFalse(Email.isValidEmail("peter@jack@example.com")); // '@' symbol in local part
            assertFalse(Email.isValidEmail("-peterjack@example.com")); // local part starts with a hyphen
            assertFalse(Email.isValidEmail("peterjack-@example.com")); // local part ends with a hyphen
            assertFalse(Email.isValidEmail("peter..jack@example.com")); // local part has two consecutive periods
            assertFalse(Email.isValidEmail("peterjack@example@com")); // '@' symbol in domain name
            assertFalse(Email.isValidEmail("peterjack@.example.com")); // domain name starts with a period
            assertFalse(Email.isValidEmail("peterjack@example.com.")); // domain name ends with a period
            assertFalse(Email.isValidEmail("peterjack@-example.com")); // domain name starts with a hyphen
            assertFalse(Email.isValidEmail("peterjack@example.com-")); // domain name ends with a hyphen
            assertFalse(Email.isValidEmail("peterjack@example.c")); // top level domain has less than two chars
            assertFalse(Email.isValidEmail("test@gmail.com ()")); // Empty label
            assertFalse(Email.isValidEmail("test@gmail.com (  )")); // Label with spaces only
            assertFalse(Email.isValidEmail("test@gmail.com (--)")); // Label with hyphen only
            assertFalse(Email.isValidEmail("test@gmail.com ( -- - )")); // Label with spaces and hyphens only
            assertFalse(Email.isValidEmail("test@gmail.com (Test / No)")); // Label other characters
            assertFalse(Email.isValidEmail("test@gmail.com e1234567@u.nus.edu")); // Multiple emails missing labels
        } catch (ParseException e) {
            fail();
        }
    }

    @Test
    public void equals() {
        Email email = new Email("valid@email");

        // same values -> returns true
        assertTrue(email.equals(new Email("valid@email")));

        // same object -> returns true
        assertTrue(email.equals(email));

        // null -> returns false
        assertFalse(email.equals(null));

        // different types -> returns false
        assertFalse(email.equals(5.0f));

        // different values -> returns false
        assertFalse(email.equals(new Email("other.valid@email")));
    }
}
