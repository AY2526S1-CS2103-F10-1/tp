package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MEETING;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MEETING_INDEX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PERSON_INDEX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_VENUE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_WHEN;

import java.util.stream.Stream;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.EditMeetingCommand;
import seedu.address.logic.commands.EditMeetingCommand.EditMeetingDescriptor;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new EditMeetingCommand object
 */
public class EditMeetingCommandParser implements Parser<EditMeetingCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the {@code EditMeetingCommand}
     * and returns an @{code EditMeetingCommand} object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public EditMeetingCommand parse(String args) throws ParseException {
        requireNonNull(args);

        // checks if args is blank (empty string or just white space)
        if (args.isBlank()) {
            throw new ParseException(EditMeetingCommand.MESSAGE_INVALID_BLANK);
        }

        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_PERSON_INDEX, PREFIX_MEETING_INDEX, PREFIX_MEETING,
                        PREFIX_VENUE, PREFIX_WHEN);

        if (!arePrefixesPresent(argMultimap, PREFIX_PERSON_INDEX, PREFIX_MEETING_INDEX)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditMeetingCommand.MESSAGE_USAGE));
        }
        if (!argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditMeetingCommand.MESSAGE_USAGE));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_PERSON_INDEX, PREFIX_MEETING_INDEX, PREFIX_MEETING,
                PREFIX_VENUE, PREFIX_WHEN);

        Index personIndex;
        Index meetingIndex;

        try {
            personIndex = ParserUtil.parseIndex(argMultimap.getValue(PREFIX_PERSON_INDEX).get());
            meetingIndex = ParserUtil.parseIndex(argMultimap.getValue(PREFIX_MEETING_INDEX).get());
        } catch (IllegalValueException ive) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditMeetingCommand.MESSAGE_USAGE),
                    ive);
        }

        EditMeetingDescriptor editMeetingDescriptor = new EditMeetingCommand.EditMeetingDescriptor();

        if (argMultimap.getValue(PREFIX_MEETING).isPresent()) {
            editMeetingDescriptor.setMeetingName(ParserUtil.parseMeetingName(
                    argMultimap.getValue(PREFIX_MEETING).get()));
        }

        if (argMultimap.getValue(PREFIX_VENUE).isPresent()) {
            editMeetingDescriptor.setVenue(ParserUtil.parseVenue(argMultimap.getValue(PREFIX_VENUE).get()));
        }

        if (argMultimap.getValue(PREFIX_WHEN).isPresent()) {
            editMeetingDescriptor.setWhen(ParserUtil.parseWhen(argMultimap.getValue(PREFIX_WHEN).get()));
        }

        if (!editMeetingDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditMeetingCommand.MESSAGE_MEETING_NOT_EDITED);
        }

        return new EditMeetingCommand(personIndex, meetingIndex, editMeetingDescriptor);
    }

    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
