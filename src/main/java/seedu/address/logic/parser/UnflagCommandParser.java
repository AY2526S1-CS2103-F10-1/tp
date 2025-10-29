package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.UnflagCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new UnflagCommand object
 */
public class UnflagCommandParser implements Parser<UnflagCommand> {

    @Override
    public UnflagCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);

            assert(index.getZeroBased() >= 0) : "Index should be non-negative";

            return new UnflagCommand(index);
        } catch (ParseException e) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, UnflagCommand.MESSAGE_USAGE, e)
            );
        }
    }
}

