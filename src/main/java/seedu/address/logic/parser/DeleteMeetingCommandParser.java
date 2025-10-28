package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MEETING_INDEX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PERSON_INDEX;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.DeleteMeetingCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new {@code DeleteMeetingCommand} object.
 */
public class DeleteMeetingCommandParser implements Parser<DeleteMeetingCommand> {

    /**
     * Parses the given {@code String} of arguments into a {@code DeleteMeetingCommand}.
     * <p>
     * The arguments must include both person and meeting indices.
     * Validation ensures that the indices are present, non-empty, and valid positive integers.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    @Override
    public DeleteMeetingCommand parse(String args) throws ParseException {
        requireNonNull(args);

        if (args.isBlank()) {
            throw new ParseException(DeleteMeetingCommand.MESSAGE_INVALID_BLANK);
        }

        ArgumentMultimap argumentMultimap = ArgumentTokenizer.tokenize(args,
                PREFIX_PERSON_INDEX, PREFIX_MEETING_INDEX);
        String personIndexString = argumentMultimap.getValue(PREFIX_PERSON_INDEX).orElse("");
        String meetingIndexString = argumentMultimap.getValue(PREFIX_MEETING_INDEX).orElse("");

        verifyNoBlankPrefixValues(personIndexString, meetingIndexString);

        argumentMultimap.verifyNoDuplicatePrefixesFor(PREFIX_PERSON_INDEX, PREFIX_MEETING_INDEX);

        Index personIndex = parsePersonIndex(personIndexString);
        Index meetingIndex = parseMeetingIndex(meetingIndexString);

        return new DeleteMeetingCommand(personIndex, meetingIndex);
    }

    /**
     * Ensures that the person and meeting indices are both present and non-empty.
     *
     * @throws ParseException If either prefix is missing or blank.
     */
    private void verifyNoBlankPrefixValues(String personIndexString, String meetingIndexString) throws ParseException {

        if (personIndexString.isBlank() && meetingIndexString.isBlank()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteMeetingCommand.MESSAGE_USAGE));
        }

        if (personIndexString.isBlank()) {
            throw new ParseException(DeleteMeetingCommand.MESSAGE_INVALID_BLANK_PERSON_INDEX);
        }

        if (meetingIndexString.isBlank()) {
            throw new ParseException(DeleteMeetingCommand.MESSAGE_INVALID_BLANK_MEETING_INDEX);
        }
    }

    /**
     * Parses and validates the given person index string.
     *
     * @return A valid {@code Index} representing the person index.
     * @throws ParseException If the value is not a valid positive integer.
     */
    private Index parsePersonIndex(String personIndexString) throws ParseException {
        try {
            return ParserUtil.parseIndex(personIndexString);
        } catch (ParseException ive) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteMeetingCommand.MESSAGE_INVALID_PERSON_INDEX),
                    ive);
        }
    }

    /**
     * Parses and validates the given meeting index string.
     *
     * @return A valid {@code Index} representing the meeting index.
     * @throws ParseException If the value is not a valid positive integer.
     */
    private Index parseMeetingIndex(String meetingIndexString) throws ParseException {
        try {
            return ParserUtil.parseIndex(meetingIndexString);
        } catch (ParseException ive) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteMeetingCommand.MESSAGE_INVALID_MEETING_INDEX),
                    ive);
        }
    }
}
