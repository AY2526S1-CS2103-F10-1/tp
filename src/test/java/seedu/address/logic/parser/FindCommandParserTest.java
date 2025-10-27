package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.FindCommand;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.NumberContainsKeywordPredicate;
import seedu.address.model.person.Person;
import seedu.address.model.person.TagContainsKeywordPredicate;

public class FindCommandParserTest {

    private FindCommandParser parser = new FindCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_emptyNameArg_throwsParseException() {
        assertParseFailure(parser, "find n=", String.format(
                MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_emptyNumberArg_throwsParseException() {
        assertParseFailure(parser, "find mn=",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_emptyTagArg_throwsParseException() {
        assertParseFailure(parser, "find t=", String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validNameArgs_returnsFindCommand() {
        // no leading and trailing whitespaces
        List<Predicate<Person>> predicates = new ArrayList<>();
        predicates.add(new NameContainsKeywordsPredicate(Arrays.asList("Alice", "Bob")));
        FindCommand expectedFindCommand = new FindCommand(predicates);

        assertParseSuccess(parser, "find n=Alice Bob", expectedFindCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, "find n= \n Alice \n Bob", expectedFindCommand);
    }

    @Test
    public void parse_validNumberArgs_returnsFindCommand() {
        // no leading and trailing whitespaces
        List<Predicate<Person>> predicates = new ArrayList<>();
        predicates.add(new NumberContainsKeywordPredicate("999"));
        FindCommand expectedFindCommand = new FindCommand(predicates);

        assertParseSuccess(parser, "find mn=999", expectedFindCommand);
    }

    @Test
    public void parse_validTagArgs_returnsFindCommand() {
        // no leading and trailing whitespaces
        List<Predicate<Person>> predicates = new ArrayList<>();
        predicates.add(new TagContainsKeywordPredicate(Arrays.asList("Alice", "Bob")));
        FindCommand expectedFindCommand = new FindCommand(predicates);

        assertParseSuccess(parser, "find t=Alice Bob", expectedFindCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, "find t= Alice Bob", expectedFindCommand);

    }
}
