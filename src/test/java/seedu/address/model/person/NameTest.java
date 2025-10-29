package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.logic.parser.exceptions.ParseException;

public class NameTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Name(null));
    }

    @Test
    public void constructor_invalidName_throwsIllegalArgumentException() {
        String invalidName = "";
        assertThrows(IllegalArgumentException.class, () -> new Name(invalidName));
    }

    @Test
    public void isValidName_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> Name.isValidName(null));
    }

    @Test
    public void isValidName_blankName_throwsParseException() {
        assertThrows(ParseException.class, () -> Name.isValidName("")); // empty string
        assertThrows(ParseException.class, () -> Name.isValidName(" ")); // spaces only
    }

    @Test
    public void isValidName_blankName_throwsBlankNameParseException() {
        try {
            String blankName = "";
            Name.isValidName(blankName);
            fail();
        } catch (ParseException e) {
            assertEquals(Name.MESSAGE_CONSTRAINTS_NO_BLANK_NAME, e.getMessage());
        }
    }

    @Test
    public void isValidName_onlyNonValidCharacter_throwsParseException() {
        String nonValidCharacter = "^";
        assertThrows(ParseException.class, () -> Name.isValidName(nonValidCharacter));
    }

    @Test
    public void isValidName_onlyNonValidCharacter_throwsAtLeastOneAlphanumericParseException() {
        try {
            String nonValidCharacter = "^";
            Name.isValidName(nonValidCharacter);
            fail();
        } catch (ParseException e) {
            assertEquals(Name.MESSAGE_CONSTRAINTS_AT_LEAST_ONE_ALPHANUMERIC, e.getMessage());
        }
    }

    @Test
    public void isValidName_containsNonValidCharacter_throwsParseException() {
        String containsNonValidCharacter = "bobby^";
        assertThrows(ParseException.class, () -> Name.isValidName(containsNonValidCharacter));
    }

    @Test
    public void isValidName_containsNonValidCharacter_throwsMessageConstraintsParseException() {
        try {
            String containsNonValidCharacter = "bobby^";
            Name.isValidName(containsNonValidCharacter);
            fail();
        } catch (ParseException e) {
            assertEquals(e.getMessage(), Name.MESSAGE_CONSTRAINTS);
        }
    }

    @Test
    public void isValidName_startsWithNonAlphabet_throwsParseException() {
        assertThrows(ParseException.class, () -> Name.isValidName("-bob")); // hyphen
        assertThrows(ParseException.class, () -> Name.isValidName("/bob")); // slash
        assertThrows(ParseException.class, () -> Name.isValidName("'bob")); // apostrophe
        assertThrows(ParseException.class, () -> Name.isValidName(".bob")); // period
        assertThrows(ParseException.class, () -> Name.isValidName("’bob")); // curly apostrophe
    }

    @Test
    public void isValidName_startsWithNonAlphabet_throwsInvalidStartEndParseException() {
        try {
            String startsWithNonAlphabet = "-bobby";
            Name.isValidName(startsWithNonAlphabet);
            fail();
        } catch (ParseException e) {
            assertEquals(e.getMessage(), Name.MESSAGE_CONSTRAINTS_INVALID_START_END);
        }
    }

    @Test
    public void isValidName_endsWithNonAlphabet_throwsParseException() {
        assertThrows(ParseException.class, () -> Name.isValidName("bob-")); // hyphen
        assertThrows(ParseException.class, () -> Name.isValidName("bob/")); // slash
        assertThrows(ParseException.class, () -> Name.isValidName("bob'")); // apostrophe
        assertThrows(ParseException.class, () -> Name.isValidName("bob.")); // period
        assertThrows(ParseException.class, () -> Name.isValidName("bob’")); // curly apostrophe
    }

    @Test
    public void isValidName_endsWithNonAlphabet_throwsInvalidStartEndParseException() {
        try {
            String endsWithNonAlphabet = "bobby-";
            Name.isValidName(endsWithNonAlphabet);
            fail();
        } catch (ParseException e) {
            assertEquals(e.getMessage(), Name.MESSAGE_CONSTRAINTS_INVALID_START_END);
        }
    }

    @Test
    public void isValidName_consecutiveSpecialCharacter_throwsParseException() {
        assertThrows(ParseException.class, () -> Name.isValidName("bob--by")); // hyphen
        assertThrows(ParseException.class, () -> Name.isValidName("bob  by")); // space
        assertThrows(ParseException.class, () -> Name.isValidName("bob//by")); // slash
        assertThrows(ParseException.class, () -> Name.isValidName("bob''by")); // apostrophe
        assertThrows(ParseException.class, () -> Name.isValidName("bob...by")); // period
        assertThrows(ParseException.class, () -> Name.isValidName("bob’’by")); // curly apostrophe
    }

    @Test
    public void isValidName_consecutiveSpecialCharacter_throwsNoConsecutiveParseException() {
        try {
            String consecutiveSpecialCharacter = "bob----------by";
            Name.isValidName(consecutiveSpecialCharacter);
            fail();
        } catch (ParseException e) {
            assertEquals(e.getMessage(), Name.MESSAGE_CONSTRAINTS_NO_CONSECUTIVE_SPECIAL_CHAR);
        }
    }

    @Test public void isValidName_over95Characters_throwsOver95CharsParseException() {
        try {
            String namewithNinetySixChars = "Sundararajan Venkatachalam Krishnamachari "
                    + "Ramanathan Subramanian Narayanaswamy Iyer Kumar Singhh";
            Name.isValidName(namewithNinetySixChars);
            fail();
        } catch (ParseException e) {
            assertEquals(e.getMessage(), Name.MESSAGE_CONSTRAINTS_NAME_OVER_95_CHARS);
        }
    }

    @Test
    public void isValidName() {
        // valid name
        try {
            assertTrue(Name.isValidName("peter jack")); // alphabets only
            assertTrue(Name.isValidName("12345")); // numbers only
            assertTrue(Name.isValidName("peter the 2nd")); // alphanumeric characters
            assertTrue(Name.isValidName("Capital Tan")); // with capital letters
            assertTrue(Name.isValidName("David Roger Jackson Ray Jr 2nd")); // long names
            assertTrue(Name.isValidName("Jean-Luc Picard")); // hyphens
            assertTrue(Name.isValidName("O'Connor")); // apostrophes
            assertTrue(Name.isValidName("Dr. John A. Smith")); // periods
            assertTrue(Name.isValidName("Anna/Marie")); // slashes
            assertTrue(Name.isValidName("José Ángel")); // accented characters
            assertTrue(Name.isValidName("D'Artagnan-Smith Jr.")); // mixed special characters
            assertTrue(Name.isValidName("Łukasz Żółć")); // non-ASCII alphabets
            assertTrue(Name.isValidName("A")); // single character
            assertTrue(Name.isValidName("7")); // single digit
            assertTrue(Name.isValidName("Élise-Marie O’Neill/Smith Jr.")); // multiple valid special characters
            assertTrue(Name.isValidName("Sundararajan Venkatachalam Krishnamachari "
                    + "Ramanathan Subramanian Narayanaswamy Iyer Kumar Singh")); // boundary value (95 characters)
        } catch (ParseException e) {
            // This block should not be reached
        }
    }

    @Test
    public void equals() {
        Name name = new Name("Valid Name");

        // same values -> returns true
        assertTrue(name.equals(new Name("Valid Name")));

        // same object -> returns true
        assertTrue(name.equals(name));

        // null -> returns false
        assertFalse(name.equals(null));

        // different types -> returns false
        assertFalse(name.equals(5.0f));

        // different values -> returns false
        assertFalse(name.equals(new Name("Other Valid Name")));
    }
}
