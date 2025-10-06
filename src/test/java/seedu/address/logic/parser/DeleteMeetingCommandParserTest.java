package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_MEETING;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.DeleteMeetingCommand;

public class DeleteMeetingCommandParserTest {
    private DeleteMeetingCommandParser parser = new DeleteMeetingCommandParser();

    @Test
    public void parse_validArgs_returnsDeleteMeetingCommand() {
        assertParseSuccess(parser, "1 i=1", new DeleteMeetingCommand(INDEX_FIRST_PERSON, INDEX_FIRST_MEETING));
    }

    @Test
    public void parse_emptyArgs_throwsParseException() {
        assertParseFailure(parser, "", String.format(DeleteMeetingCommand.MESSAGE_INVALID_BLANK));
    }

    @Test
    public void parse_emptyPersonIndexArgs_throwsParseException() {
        assertParseFailure(parser, " i=1", String.format(DeleteMeetingCommand.MESSAGE_INVALID_BLANK_PERSON_INDEX));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a", String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteMeetingCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidPersonIndexArgs_throwsParseException() {
        assertParseFailure(parser, "a i=1", String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteMeetingCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_emptyMeetingIndexArgs_throwsParseException() {
        assertParseFailure(parser, "1 i=", DeleteMeetingCommand.MESSAGE_INVALID_BLANK_MEETING_INDEX);
    }

    @Test
    public void parse_invalidMeetingIndexArgs_throwsParseException() {
        assertParseFailure(parser, "1 i=b", String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteMeetingCommand.MESSAGE_USAGE));
    }
}
