package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MEETING;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PERSON_INDEX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_VENUE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_WHEN;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.AddMeetingCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.meeting.Meeting;
import seedu.address.model.meeting.MeetingName;
import seedu.address.model.meeting.Venue;
import seedu.address.model.meeting.When;

public class AddMeetingCommandParserTest {
    private AddMeetingCommandParser parser = new AddMeetingCommandParser();
    private final String nonEmptyMeeting = "Some meeting";
    private final String nonEmptyVenue = "Some venue";
    private final String nonEmptyWhen = "2025-10-11 1400";
    private final String nonEmptyPreamble = "Some preamble";

    @Test
    public void parse_indexSpecified_success() throws ParseException {
        MeetingName meetingName = new MeetingName(nonEmptyMeeting);
        Venue venue = new Venue(nonEmptyVenue);
        When when = new When(nonEmptyWhen);
        Index targetIndex = INDEX_FIRST_PERSON;
        String userInput = " " + PREFIX_PERSON_INDEX + targetIndex.getOneBased() + " " + PREFIX_MEETING
                + nonEmptyMeeting + " " + PREFIX_VENUE + nonEmptyVenue + " " + PREFIX_WHEN + nonEmptyWhen;
        AddMeetingCommand expectedCommand = new AddMeetingCommand(INDEX_FIRST_PERSON,
                new Meeting(meetingName, venue, when));
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_nonEmptyPreamble_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddMeetingCommand.MESSAGE_USAGE);
        Index targetIndex = INDEX_FIRST_PERSON;
        String userInput = " " + nonEmptyPreamble + " " + PREFIX_PERSON_INDEX + targetIndex.getOneBased() + " "
                + PREFIX_MEETING + nonEmptyMeeting + " " + PREFIX_VENUE + nonEmptyVenue + " " + PREFIX_WHEN
                + nonEmptyWhen;
        assertParseFailure(parser, userInput, expectedMessage);
    }

    @Test
    public void parse_invalidIndex_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddMeetingCommand.MESSAGE_USAGE);
        String targetIndex = "-1";
        String userInput = " " + PREFIX_PERSON_INDEX + targetIndex + " " + PREFIX_MEETING
                + nonEmptyMeeting + " " + PREFIX_VENUE + nonEmptyVenue + " " + PREFIX_WHEN + nonEmptyWhen;
        assertParseFailure(parser, userInput, expectedMessage);

        targetIndex = "0";
        userInput = " " + PREFIX_PERSON_INDEX + targetIndex + " " + PREFIX_MEETING + nonEmptyMeeting + " "
                + PREFIX_VENUE + nonEmptyVenue + " " + PREFIX_WHEN + nonEmptyWhen;
        assertParseFailure(parser, userInput, expectedMessage);
    }

    @Test
    public void parse_missingCompulsoryField_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddMeetingCommand.MESSAGE_USAGE);
        Index targetIndex = INDEX_FIRST_PERSON;

        // missing m= prefix
        String userInput = " " + PREFIX_PERSON_INDEX + targetIndex.getOneBased() + " " + PREFIX_VENUE + nonEmptyVenue
                + " " + PREFIX_WHEN + nonEmptyWhen;
        assertParseFailure(parser, userInput, expectedMessage);

        // missing v= prefix
        userInput = " " + PREFIX_PERSON_INDEX + targetIndex.getOneBased() + " " + PREFIX_MEETING + nonEmptyMeeting
                + " " + PREFIX_WHEN + nonEmptyWhen;
        assertParseFailure(parser, userInput, expectedMessage);

        // missing w= prefix
        userInput = " " + PREFIX_PERSON_INDEX + targetIndex.getOneBased() + " " + PREFIX_MEETING + nonEmptyMeeting
                + " " + PREFIX_VENUE + nonEmptyVenue;
        assertParseFailure(parser, userInput, expectedMessage);

        // no parameters
        assertParseFailure(parser, AddMeetingCommand.COMMAND_WORD, expectedMessage);

        // no index
        assertParseFailure(parser, AddMeetingCommand.COMMAND_WORD + " " + nonEmptyMeeting + " " + nonEmptyVenue
                + " " + nonEmptyWhen, expectedMessage);
    }
}
