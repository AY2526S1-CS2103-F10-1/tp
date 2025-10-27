package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MAIN_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
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
        List<Predicate<Person>> predicates = new ArrayList<>();
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }

        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_MAIN_PHONE, PREFIX_TAG);

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_NAME, PREFIX_MAIN_PHONE, PREFIX_TAG);

        String nameKeywords = argMultimap.getValue(PREFIX_NAME).orElse("");
        if (!nameKeywords.equals("")) {
            predicates.add(
                    new NameContainsKeywordsPredicate(Arrays.asList(nameKeywords.split("\\s+"))));
        }
        String phoneKeywords = argMultimap.getValue(PREFIX_MAIN_PHONE).orElse("");
        if (!phoneKeywords.equals("")) {
            predicates.add(
                    new NumberContainsKeywordPredicate(phoneKeywords));
        }
        String tagKeywords = argMultimap.getValue(PREFIX_TAG).orElse("");
        if (!tagKeywords.equals("")) {
            predicates.add(
                    new TagContainsKeywordPredicate(Arrays.asList(tagKeywords.split("\\s+"))));
        }

        if (predicates.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }

        return new FindCommand(predicates);
    }

}
