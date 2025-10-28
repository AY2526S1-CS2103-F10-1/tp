package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MEETING_INDEX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PERSON_INDEX;

import java.util.stream.Stream;

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

        validateEmptyPreamble(argumentMultimap.getPreamble());

        argumentMultimap.verifyNoDuplicatePrefixesFor(PREFIX_PERSON_INDEX, PREFIX_MEETING_INDEX);

        Index personIndex = parseIndexString(personIndexString);
        Index meetingIndex = parseIndexString(meetingIndexString);

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
     * Parses and validates the given index string.
     *
     * @throws ParseException If the value is not a valid index.
     */
    private Index parseIndexString(String indexString) throws ParseException {
        try {
            return ParserUtil.parseIndex(indexString);
        } catch (ParseException ive) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteMeetingCommand.MESSAGE_USAGE));
        }
    }


    /**
     * Ensures that the command preamble (the text before the first prefix) is empty.
     * <p>
     * A non-empty preamble indicates invalid input formatting, since this command
     * does not allow free-form arguments before its required prefixes.
     *
     * @param preamble The raw preamble string extracted from the user input.
     * @throws ParseException If the preamble is not empty.
     */
    private static void validateEmptyPreamble(String preamble) throws ParseException {
        if (!preamble.isBlank()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteMeetingCommand.MESSAGE_USAGE));
        }
    }
}
