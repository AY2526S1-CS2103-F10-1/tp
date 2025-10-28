package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.Messages.MESSAGE_INVALID_DOES_NOT_EXIST;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_MEETING_NAME;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_MEETING_VENUE;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_MEETING_WHEN;
import static seedu.address.logic.commands.CommandTestUtil.MEETING_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.MEETING_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.MEETING_VENUE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.MEETING_VENUE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.MEETING_WHEN_AMY;
import static seedu.address.logic.commands.CommandTestUtil.MEETING_WHEN_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MEETING_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MEETING_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_VENUE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_VENUE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_WHEN_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_WHEN_BOB;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MEETING;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MEETING_INDEX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PERSON_INDEX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_VENUE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_WHEN;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_MEETING;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.EditMeetingCommand;
import seedu.address.logic.commands.EditMeetingCommand.EditMeetingDescriptor;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.meeting.MeetingName;
import seedu.address.model.meeting.Venue;
import seedu.address.testutil.EditMeetingDescriptorBuilder;

public class EditMeetingCommandParserTest {
    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditMeetingCommand.MESSAGE_USAGE);

    private EditMeetingCommandParser parser = new EditMeetingCommandParser();

    private Index meetingIndex = INDEX_FIRST_MEETING;
    private Index personIndex = INDEX_FIRST_PERSON;

    @Test
    public void parse_missingParts_failure() {
        // no person index specified
        String userInput = " " + PREFIX_MEETING_INDEX + meetingIndex.getOneBased() + MEETING_NAME_AMY
                + MEETING_VENUE_AMY + MEETING_WHEN_AMY;

        assertParseFailure(parser, userInput, MESSAGE_INVALID_FORMAT);

        // no meeting index specified
        userInput = " " + PREFIX_MEETING_INDEX + meetingIndex.getOneBased() + MEETING_NAME_AMY + MEETING_VENUE_AMY
                + MEETING_WHEN_AMY;

        assertParseFailure(parser, userInput, MESSAGE_INVALID_FORMAT);

        // no field specified
        userInput = " " + PREFIX_PERSON_INDEX + personIndex.getOneBased() + " " + PREFIX_MEETING_INDEX
                + meetingIndex.getOneBased();
        assertParseFailure(parser, userInput, EditMeetingCommand.MESSAGE_MEETING_NOT_EDITED);

        // no index and no field specified
        userInput = "";
        assertParseFailure(parser, userInput, EditMeetingCommand.MESSAGE_INVALID_BLANK);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        String userInput = " " + PREFIX_PERSON_INDEX + "-1" + " " + PREFIX_MEETING_INDEX
                + meetingIndex.getOneBased() + MEETING_NAME_BOB + MEETING_VENUE_BOB + MEETING_WHEN_BOB;

        assertParseFailure(parser, userInput, MESSAGE_INVALID_FORMAT);

        userInput = " " + PREFIX_PERSON_INDEX + personIndex.getOneBased() + " " + PREFIX_MEETING_INDEX
                + "-1" + MEETING_NAME_AMY + MEETING_VENUE_AMY + MEETING_WHEN_AMY;

        assertParseFailure(parser, userInput, MESSAGE_INVALID_FORMAT);

        // zero index
        userInput = " " + PREFIX_PERSON_INDEX + "0" + " " + PREFIX_MEETING_INDEX
                + meetingIndex.getOneBased() + MEETING_NAME_BOB + MEETING_VENUE_BOB + MEETING_WHEN_BOB;

        assertParseFailure(parser, userInput, MESSAGE_INVALID_FORMAT);

        userInput = " " + PREFIX_PERSON_INDEX + personIndex.getOneBased() + " " + PREFIX_MEETING_INDEX
                + "0" + MEETING_NAME_BOB + MEETING_VENUE_BOB + MEETING_WHEN_BOB;

        assertParseFailure(parser, userInput, MESSAGE_INVALID_FORMAT);

        // non-empty preamble after command
        userInput = "abc " + PREFIX_PERSON_INDEX + personIndex.getOneBased() + " " + PREFIX_MEETING_INDEX
                + meetingIndex.getOneBased() + MEETING_VENUE_AMY + MEETING_WHEN_AMY;

        assertParseFailure(parser, userInput, MESSAGE_INVALID_FORMAT);

        // invalid arguments being parsed as preamble
        userInput = " " + PREFIX_PERSON_INDEX + "1 some random string" + " " + PREFIX_MEETING_INDEX
                + meetingIndex.getOneBased() + MEETING_NAME_AMY + MEETING_VENUE_AMY + MEETING_WHEN_AMY;

        assertParseFailure(parser, userInput, MESSAGE_INVALID_FORMAT);

        userInput = " " + PREFIX_PERSON_INDEX + personIndex.getOneBased() + " " + PREFIX_MEETING_INDEX
                + "1 some random string" + MEETING_NAME_BOB + MEETING_VENUE_BOB + MEETING_WHEN_BOB;

        assertParseFailure(parser, userInput, MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_allFieldsPresent_success() throws ParseException {
        String userInput = " " + PREFIX_PERSON_INDEX + personIndex.getOneBased() + " " + PREFIX_MEETING_INDEX
                + meetingIndex.getOneBased() + MEETING_NAME_AMY + MEETING_VENUE_AMY + MEETING_WHEN_AMY;
        EditMeetingDescriptor descriptor = new EditMeetingDescriptorBuilder().withMeetingName(VALID_MEETING_AMY)
                .withVenue(VALID_VENUE_AMY).withWhen(VALID_WHEN_AMY).build();
        EditMeetingCommand expectedCommand = new EditMeetingCommand(INDEX_FIRST_PERSON, INDEX_FIRST_MEETING,
                descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_someFieldsSpecified_success() throws ParseException {
        String userInput = " " + PREFIX_PERSON_INDEX + personIndex.getOneBased() + " " + PREFIX_MEETING_INDEX
                + meetingIndex.getOneBased() + MEETING_VENUE_AMY + MEETING_WHEN_AMY;
        EditMeetingDescriptor descriptor = new EditMeetingDescriptorBuilder().withVenue(VALID_VENUE_AMY)
                .withWhen(VALID_WHEN_AMY).build();
        EditMeetingCommand expectedCommand = new EditMeetingCommand(INDEX_FIRST_PERSON, INDEX_FIRST_MEETING,
                descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_oneFieldSpecified_success() throws ParseException {
        String userInput = " " + PREFIX_PERSON_INDEX + personIndex.getOneBased() + " " + PREFIX_MEETING_INDEX
                + meetingIndex.getOneBased() + MEETING_NAME_BOB;
        EditMeetingDescriptor descriptor = new EditMeetingDescriptorBuilder().withMeetingName(VALID_MEETING_BOB)
                .build();
        EditMeetingCommand expectedCommand = new EditMeetingCommand(INDEX_FIRST_PERSON, INDEX_FIRST_MEETING,
                descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);

        userInput = " " + PREFIX_PERSON_INDEX + personIndex.getOneBased() + " " + PREFIX_MEETING_INDEX
                + meetingIndex.getOneBased() + MEETING_VENUE_BOB;
        descriptor = new EditMeetingDescriptorBuilder().withVenue(VALID_VENUE_BOB).build();
        expectedCommand = new EditMeetingCommand(INDEX_FIRST_PERSON, INDEX_FIRST_MEETING,
                descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);

        userInput = " " + PREFIX_PERSON_INDEX + personIndex.getOneBased() + " " + PREFIX_MEETING_INDEX
                + meetingIndex.getOneBased() + MEETING_WHEN_BOB;
        descriptor = new EditMeetingDescriptorBuilder().withWhen(VALID_WHEN_BOB).build();
        expectedCommand = new EditMeetingCommand(INDEX_FIRST_PERSON, INDEX_FIRST_MEETING,
                descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_invalidValue_failure() throws ParseException {
        String userInput = " " + PREFIX_PERSON_INDEX + personIndex.getOneBased() + " " + PREFIX_MEETING_INDEX
                + meetingIndex.getOneBased() + INVALID_MEETING_NAME + MEETING_VENUE_AMY + MEETING_WHEN_AMY;

        assertParseFailure(parser, userInput, MeetingName.MESSAGE_CONSTRAINTS);

        userInput = " " + PREFIX_PERSON_INDEX + personIndex.getOneBased() + " " + PREFIX_MEETING_INDEX
                + meetingIndex.getOneBased() + MEETING_NAME_AMY + INVALID_MEETING_VENUE + MEETING_WHEN_AMY;

        assertParseFailure(parser, userInput, Venue.MESSAGE_CONSTRAINTS);

        userInput = " " + PREFIX_PERSON_INDEX + personIndex.getOneBased() + " " + PREFIX_MEETING_INDEX
                + meetingIndex.getOneBased() + MEETING_NAME_AMY + MEETING_VENUE_AMY + INVALID_MEETING_WHEN;

        assertParseFailure(parser, userInput, MESSAGE_INVALID_DOES_NOT_EXIST);
    }

    @Test
    public void parse_multipleRepeatedFields_failure() throws ParseException {
        String userInput = " " + PREFIX_PERSON_INDEX + personIndex.getOneBased() + " " + PREFIX_MEETING_INDEX
                + meetingIndex.getOneBased() + INVALID_MEETING_NAME + MEETING_NAME_AMY;
        assertParseFailure(parser, userInput, Messages.getErrorMessageForDuplicatePrefixes(PREFIX_MEETING));

        userInput = " " + PREFIX_PERSON_INDEX + personIndex.getOneBased() + " " + PREFIX_MEETING_INDEX
                + meetingIndex.getOneBased() + INVALID_MEETING_VENUE + MEETING_VENUE_AMY;
        assertParseFailure(parser, userInput, Messages.getErrorMessageForDuplicatePrefixes(PREFIX_VENUE));

        userInput = " " + PREFIX_PERSON_INDEX + personIndex.getOneBased() + " " + PREFIX_MEETING_INDEX
                + meetingIndex.getOneBased() + INVALID_MEETING_WHEN + MEETING_WHEN_AMY;
        assertParseFailure(parser, userInput, Messages.getErrorMessageForDuplicatePrefixes(PREFIX_WHEN));

        userInput = " " + PREFIX_PERSON_INDEX + personIndex.getOneBased() + " " + PREFIX_MEETING_INDEX
                + meetingIndex.getOneBased() + MEETING_NAME_AMY + MEETING_WHEN_AMY + MEETING_VENUE_AMY
                + MEETING_NAME_AMY + MEETING_VENUE_AMY + MEETING_WHEN_AMY;
        assertParseFailure(parser, userInput, Messages.getErrorMessageForDuplicatePrefixes(PREFIX_MEETING, PREFIX_VENUE,
                PREFIX_WHEN));

        userInput = " " + PREFIX_PERSON_INDEX + personIndex.getOneBased() + " " + PREFIX_MEETING_INDEX
                + meetingIndex.getOneBased() + INVALID_MEETING_NAME + INVALID_MEETING_VENUE + INVALID_MEETING_WHEN
                + INVALID_MEETING_NAME + INVALID_MEETING_VENUE + INVALID_MEETING_WHEN;
        assertParseFailure(parser, userInput, Messages.getErrorMessageForDuplicatePrefixes(PREFIX_MEETING, PREFIX_VENUE,
                PREFIX_WHEN));
    }
}
