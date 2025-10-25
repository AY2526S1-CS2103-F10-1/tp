package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.*;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.NumberContainsKeywordPredicate;
import seedu.address.model.person.Person;
import seedu.address.model.person.TagContainsKeywordPredicate;

/**
 * Parses input arguments and creates a new FindCommand object
 */
public class FindCommandParser implements Parser<FindCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FindCommand
     * and returns a FindCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindCommand parse(String args) throws ParseException {
        List<Predicate<Person>> listOfPredicates = new ArrayList<>();
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }

        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_MAIN_PHONE, PREFIX_TAG);

        String nameKeywords = argMultimap.getValue(PREFIX_NAME).orElse("");
        if (!nameKeywords.equals("")) {
            listOfPredicates.add(
                    new NameContainsKeywordsPredicate(Arrays.asList(nameKeywords)));
        }
        String phoneKeywords = argMultimap.getValue(PREFIX_MAIN_PHONE).orElse("");
        if (!phoneKeywords.equals("")) {
            listOfPredicates.add(
                    new NumberContainsKeywordPredicate(phoneKeywords));
        }
        String tagKeywords = argMultimap.getValue(PREFIX_TAG).orElse("");
        if (!tagKeywords.equals("")) {
            listOfPredicates.add(
                    new TagContainsKeywordPredicate(Arrays.asList(tagKeywords)));
        }

        if (listOfPredicates.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }

        return new FindCommand(listOfPredicates);
    }

}
