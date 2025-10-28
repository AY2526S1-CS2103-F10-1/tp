package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.FlagCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new FlagCommand object
 */
public class FlagCommandParser implements Parser<FlagCommand> {

    @Override
    public FlagCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);

            assert(index.getZeroBased() >= 0) : "Index should be non-negative";

            return new FlagCommand(index);
        } catch (ParseException e) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FlagCommand.MESSAGE_USAGE, e)
            );
        }
    }
}
