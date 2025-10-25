package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MEETING;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PERSON_INDEX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_VENUE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_WHEN;

import java.util.stream.Stream;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.AddMeetingCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.meeting.Meeting;
import seedu.address.model.meeting.MeetingName;
import seedu.address.model.meeting.Venue;
import seedu.address.model.meeting.When;

/**
 * Parses input arguments and creates a new {@code AddMeetingCommand} object
 */
public class AddMeetingCommandParser implements Parser<AddMeetingCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the {@code AddMeetingCommand}
     * and returns a {@code AddMeetingCommand} object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddMeetingCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_PERSON_INDEX, PREFIX_MEETING,
                PREFIX_VENUE, PREFIX_WHEN);

        if (!arePrefixesPresent(argMultimap, PREFIX_PERSON_INDEX, PREFIX_MEETING, PREFIX_VENUE, PREFIX_WHEN)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddMeetingCommand.MESSAGE_USAGE));
        }

        if (!argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddMeetingCommand.MESSAGE_USAGE));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_PERSON_INDEX, PREFIX_MEETING, PREFIX_VENUE, PREFIX_WHEN);

        Index index;
        try {
            index = ParserUtil.parseIndex(argMultimap.getValue(PREFIX_PERSON_INDEX).get());
        } catch (IllegalValueException ive) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddMeetingCommand.MESSAGE_USAGE),
                    ive);
        }

        MeetingName meetingName = ParserUtil.parseMeetingName(argMultimap.getValue(PREFIX_MEETING).get());
        Venue venue = ParserUtil.parseVenue(argMultimap.getValue(PREFIX_VENUE).get());
        When when = ParserUtil.parseWhen(argMultimap.getValue(PREFIX_WHEN).get());

        Meeting meeting = new Meeting(meetingName, venue, when);

        return new AddMeetingCommand(index, meeting);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
