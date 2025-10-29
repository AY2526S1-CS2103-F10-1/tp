package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.parser.ParserUtil.MESSAGE_INVALID_INDEX;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.meeting.MeetingName;
import seedu.address.model.meeting.Venue;
import seedu.address.model.meeting.When;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;
import seedu.address.model.tag.Tag;

public class ParserUtilTest {
    private static final String INVALID_NAME = "R@chel";
    private static final String INVALID_PHONE = "+651234";
    private static final String INVALID_ADDRESS = " ";
    private static final String INVALID_EMAIL = "example.com";
    private static final String INVALID_TAG = "#friend";
    private static final String INVALID_MEETING = "Fin@nci@l $h@ring";
    private static final String INVALID_VENUE = "$Hub";
    private static final String INVALID_WHEN = "2025-13-13 1400";

    private static final String VALID_NAME = "Rachel Walker";
    private static final String VALID_PHONE = "123456";
    private static final String VALID_ADDRESS = "123 Main Street #0505";
    private static final String VALID_EMAIL = "rachel@example.com";
    private static final String VALID_TAG_1 = "friend";
    private static final String VALID_TAG_2 = "neighbour";
    private static final String VALID_MEETING = "Financial sharing";
    private static final String VALID_VENUE = "AMK Hub";
    private static final String VALID_WHEN = "2025-12-12 1400";

    private static final String WHITESPACE = " \t\r\n";

    @Test
    public void parseIndex_invalidInput_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseIndex("10 a"));
    }

    @Test
    public void parseIndex_outOfRangeInput_throwsParseException() {
        assertThrows(ParseException.class, MESSAGE_INVALID_INDEX, ()
            -> ParserUtil.parseIndex(Long.toString(Integer.MAX_VALUE + 1)));
    }

    @Test
    public void parseIndex_validInput_success() throws Exception {
        // No whitespaces
        assertEquals(INDEX_FIRST_PERSON, ParserUtil.parseIndex("1"));

        // Leading and trailing whitespaces
        assertEquals(INDEX_FIRST_PERSON, ParserUtil.parseIndex("  1  "));
    }

    @Test
    public void parseName_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseName((String) null));
    }

    @Test
    public void parseName_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseName(INVALID_NAME));
    }

    @Test
    public void parseName_validValueWithoutWhitespace_returnsName() throws Exception {
        Name expectedName = new Name(VALID_NAME);
        assertEquals(expectedName, ParserUtil.parseName(VALID_NAME));
    }

    @Test
    public void parseName_validValueWithWhitespace_returnsTrimmedName() throws Exception {
        String nameWithWhitespace = WHITESPACE + VALID_NAME + WHITESPACE;
        Name expectedName = new Name(VALID_NAME);
        assertEquals(expectedName, ParserUtil.parseName(nameWithWhitespace));
    }

    @Test
    public void parsePhone_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parsePhone((String) null));
    }

    @Test
    public void parsePhone_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parsePhone(INVALID_PHONE));
    }

    @Test
    public void parsePhone_validValueWithoutWhitespace_returnsPhone() throws Exception {
        Phone expectedPhone = new Phone(VALID_PHONE);
        assertEquals(expectedPhone, ParserUtil.parsePhone(VALID_PHONE));
    }

    @Test
    public void parsePhone_validValueWithWhitespace_returnsTrimmedPhone() throws Exception {
        String phoneWithWhitespace = WHITESPACE + VALID_PHONE + WHITESPACE;
        Phone expectedPhone = new Phone(VALID_PHONE);
        assertEquals(expectedPhone, ParserUtil.parsePhone(phoneWithWhitespace));
    }

    @Test
    public void parseAddress_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseAddress((String) null));
    }

    @Test
    public void parseAddress_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseAddress(INVALID_ADDRESS));
    }

    @Test
    public void parseAddress_emptyString_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseAddress(INVALID_ADDRESS));
    }

    @Test
    public void parseAddress_invalidLength_throwsParseException() {
        String invalidAddressLength = "15, 29a Upper Paya Lebar Road, Singapore 533015 (Home 1) "
                + "Blk 742, 04-187, Yishun Avenue 5, Singapore 760742 (Home 2) 290, 03-22, Orchard Road, "
                + "Singapore 238801 (Home 3) Jurong West Street 2112 (Home 4)";

        assertThrows(ParseException.class, () -> ParserUtil.parseAddress(invalidAddressLength));
    }

    @Test
    public void parseAddress_validValueLengthJustNice_returnsAddress() throws Exception {
        String validAddressLengthJustNice = "15, 29a Upper Paya Lebar Road, Singapore 533015 (Home 1) "
                + "Blk 742, 04-187, Yishun Avenue 5, Singapore 760742 (Home 2) 290, 03-22, Orchard Road, "
                + "Singapore 238801 (Home 3) Jurong West Street 211 (Home 4)";

        Address expectedAddress = new Address(validAddressLengthJustNice);
        assertEquals(expectedAddress, ParserUtil.parseAddress(validAddressLengthJustNice));
    }

    @Test
    public void parseAddress_validValueWithoutWhitespace_returnsAddress() throws Exception {
        Address expectedAddress = new Address(VALID_ADDRESS);
        assertEquals(expectedAddress, ParserUtil.parseAddress(VALID_ADDRESS));
    }

    @Test
    public void parseAddress_validValueWithWhitespace_returnsTrimmedAddress() throws Exception {
        String addressWithWhitespace = WHITESPACE + VALID_ADDRESS + WHITESPACE;
        Address expectedAddress = new Address(VALID_ADDRESS);
        assertEquals(expectedAddress, ParserUtil.parseAddress(addressWithWhitespace));
    }

    @Test
    public void parseEmail_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseEmail((String) null));
    }

    @Test
    public void parseEmail_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseEmail(INVALID_EMAIL));
    }

    @Test
    public void parseEmail_invalidLength_throwsParseException() {
        String invalidEmailLength = "test1@gmail.com (work1) test2@gmail.com (work2) "
                + "test3@gmail.com (work3) test4@gmail.com (work4) test5@gmail.com (work5) test6@gmail.com "
                + "(work6) test7@gmail.com (work7) test8@gmail.com (work8) test9@gmail.com (work9) "
                + "test10@gmail.com (work10) test11@gmail.com (work11) test12@gmail.com (work12)";

        assertThrows(ParseException.class, () -> ParserUtil.parseEmail(invalidEmailLength));
    }

    @Test
    public void parseEmail_validValueLengthJustNice_returnsEmail() throws Exception {
        String validEmailLengthJustNice = "test1@gmail.com (work 1) test2@gmail.com (work 2) "
                + "test3@gmail.com (work 3) test4@gmail.com (work 4) test5@gmail.com (work 5) "
                + "test6@gmail.com (work 6) test7@gmail.com (work 7) test8@gmail.com (work 8)";
        Email expectedEmail = new Email(validEmailLengthJustNice);
        assertEquals(expectedEmail, ParserUtil.parseEmail(validEmailLengthJustNice));
    }

    @Test
    public void parseEmail_validValueWithoutWhitespace_returnsEmail() throws Exception {
        Email expectedEmail = new Email(VALID_EMAIL);
        assertEquals(expectedEmail, ParserUtil.parseEmail(VALID_EMAIL));
    }

    @Test
    public void parseEmail_validValueWithWhitespace_returnsTrimmedEmail() throws Exception {
        String emailWithWhitespace = WHITESPACE + VALID_EMAIL + WHITESPACE;
        Email expectedEmail = new Email(VALID_EMAIL);
        assertEquals(expectedEmail, ParserUtil.parseEmail(emailWithWhitespace));
    }

    @Test
    public void parseTag_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseTag(null));
    }

    @Test
    public void parseTag_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseTag(INVALID_TAG));
    }

    @Test
    public void parseTag_validValueWithoutWhitespace_returnsTag() throws Exception {
        Tag expectedTag = new Tag(VALID_TAG_1);
        assertEquals(expectedTag, ParserUtil.parseTag(VALID_TAG_1));
    }

    @Test
    public void parseTag_validValueWithWhitespace_returnsTrimmedTag() throws Exception {
        String tagWithWhitespace = WHITESPACE + VALID_TAG_1 + WHITESPACE;
        Tag expectedTag = new Tag(VALID_TAG_1);
        assertEquals(expectedTag, ParserUtil.parseTag(tagWithWhitespace));
    }

    @Test
    public void parseTags_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseTags(null));
    }

    @Test
    public void parseTags_collectionWithInvalidTags_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseTags(Arrays.asList(VALID_TAG_1, INVALID_TAG)));
    }

    @Test
    public void parseTags_emptyCollection_returnsEmptySet() throws Exception {
        assertTrue(ParserUtil.parseTags(Collections.emptyList()).isEmpty());
    }

    @Test
    public void parseTags_collectionWithValidTags_returnsTagSet() throws Exception {
        Set<Tag> actualTagSet = ParserUtil.parseTags(Arrays.asList(VALID_TAG_1, VALID_TAG_2));
        Set<Tag> expectedTagSet = new HashSet<Tag>(Arrays.asList(new Tag(VALID_TAG_1), new Tag(VALID_TAG_2)));

        assertEquals(expectedTagSet, actualTagSet);
    }

    @Test
    public void parseMeeting_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseMeetingName(null));
    }

    @Test
    public void parseMeeting_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseMeetingName(INVALID_MEETING));
    }

    @Test
    public void parseMeeting_validValueWithoutWhitespace_returnsMeeting() throws Exception {
        MeetingName expectedMeetingName = new MeetingName(VALID_MEETING);
        assertEquals(expectedMeetingName, ParserUtil.parseMeetingName(VALID_MEETING));
    }

    @Test
    public void parseMeeting_validValueWithWhitespace_returnsTrimmedMeeting() throws Exception {
        String meetingWithWhitespace = WHITESPACE + VALID_MEETING + WHITESPACE;
        MeetingName expectedMeetingName = new MeetingName(VALID_MEETING);
        assertEquals(expectedMeetingName, ParserUtil.parseMeetingName(meetingWithWhitespace));
    }

    @Test
    public void parseVenue_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseVenue(null));
    }

    @Test
    public void parseVenue_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseVenue(INVALID_VENUE));
    }

    @Test
    public void parseVenue_validValueWithoutWhitespace_returnsVenue() throws Exception {
        Venue expectedVenue = new Venue(VALID_VENUE);
        assertEquals(expectedVenue, ParserUtil.parseVenue(VALID_VENUE));
    }

    @Test
    public void parseVenue_validValueWithWhitespace_returnsTrimmedVenue() throws Exception {
        String venueWithWhitespace = WHITESPACE + VALID_VENUE + WHITESPACE;
        Venue expectedVenue = new Venue(VALID_VENUE);
        assertEquals(expectedVenue, ParserUtil.parseVenue(venueWithWhitespace));
    }

    @Test
    public void parseWhen_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseWhen(null));
    }

    @Test
    public void parseWhen_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseWhen(INVALID_WHEN));
    }

    @Test
    public void parseWhen_validValueWithoutWhitespace_returnsWhen() throws Exception {
        When expectedWhen = new When(VALID_WHEN);
        assertEquals(expectedWhen, ParserUtil.parseWhen(VALID_WHEN));
    }

    @Test
    public void parseWhen_validValueWithWhitespace_returnsTrimmedWhen() throws Exception {
        String whenWithWhitespace = WHITESPACE + VALID_WHEN + WHITESPACE;
        When expectedWhen = new When(VALID_WHEN);
        assertEquals(expectedWhen, ParserUtil.parseWhen(whenWithWhitespace));
    }
}
