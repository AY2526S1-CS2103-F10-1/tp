package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.Messages.MESSAGE_NO_PREAMBLE_BEFORE_PREFIX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MEETING_INDEX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PERSON_INDEX;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_MEETING;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_MEETING;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.DeleteMeetingCommand;

public class DeleteMeetingCommandParserTest {
    private DeleteMeetingCommandParser parser = new DeleteMeetingCommandParser();

    @Test
    public void parse_validArgs_returnsDeleteMeetingCommand() {
        assertParseSuccess(parser, " p=1 i=1", new DeleteMeetingCommand(INDEX_FIRST_PERSON, INDEX_FIRST_MEETING));
    }

    @Test
    public void parse_emptyArgs_throwsParseException() {
        assertParseFailure(parser, "", String.format(DeleteMeetingCommand.MESSAGE_INVALID_BLANK));
    }

    @Test
    public void parse_noPersonIndex_throwsParseException() {
        assertParseFailure(parser, " i=1", String.format(DeleteMeetingCommand.MESSAGE_INVALID_BLANK_PERSON_INDEX));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a", String.format(
                MESSAGE_INVALID_COMMAND_FORMAT,
                DeleteMeetingCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_duplicatePrefixes_throwsParseException() {
        // Duplicate person index prefix
        String dupPersonIndexInput = " " + PREFIX_PERSON_INDEX + INDEX_FIRST_PERSON.getOneBased()
                + " " + PREFIX_MEETING_INDEX + INDEX_FIRST_MEETING.getOneBased()
                + " " + PREFIX_PERSON_INDEX + INDEX_FIRST_PERSON.getOneBased();
        assertParseFailure(parser, dupPersonIndexInput,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PERSON_INDEX));

        // Duplicate meeting index prefix
        String dupMeetingIndexInput = " " + PREFIX_MEETING_INDEX + INDEX_SECOND_MEETING.getOneBased()
                + " " + PREFIX_PERSON_INDEX + INDEX_FIRST_PERSON.getOneBased()
                + " " + PREFIX_MEETING_INDEX + INDEX_FIRST_MEETING.getOneBased();
        assertParseFailure(parser, dupMeetingIndexInput,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_MEETING_INDEX));
    }

    @Test
    public void parse_emptyPersonIndexArgs_throwsParseException() {
        assertParseFailure(parser, " p=     i=1",
                String.format(DeleteMeetingCommand.MESSAGE_INVALID_BLANK_PERSON_INDEX));
    }

    @Test
    public void parse_invalidPersonIndexArgs_throwsParseException() {
        assertParseFailure(parser, "a i=1", String.format(DeleteMeetingCommand.MESSAGE_INVALID_BLANK_PERSON_INDEX));
    }

    @Test
    public void parse_emptyMeetingIndexArgs_throwsParseException() {
        assertParseFailure(parser, " p=1 i=", DeleteMeetingCommand.MESSAGE_INVALID_BLANK_MEETING_INDEX);
    }

    @Test
    public void parse_invalidMeetingIndexArgs_throwsParseException() {
        assertParseFailure(parser, " p=1 i=b", String.format(
                MESSAGE_INVALID_COMMAND_FORMAT,
                DeleteMeetingCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_extraPrefix_throwsParseException() {
        assertParseFailure(parser, " p=1 i=1 c=1", String.format(
                MESSAGE_INVALID_COMMAND_FORMAT,
                DeleteMeetingCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_nonEmptyPreambleBeforePrefix_throwsParseException() {
        assertParseFailure(parser, "a;dlkfja;sldkjf p=1 i=1", String.format(
                MESSAGE_NO_PREAMBLE_BEFORE_PREFIX,
                DeleteMeetingCommand.MESSAGE_USAGE));
    }
}
