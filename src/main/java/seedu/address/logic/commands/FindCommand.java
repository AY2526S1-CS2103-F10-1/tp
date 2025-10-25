package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.*;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.NumberContainsKeywordPredicate;
import seedu.address.model.person.Person;
import seedu.address.model.person.TagContainsKeywordPredicate;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

/**
 * Finds and lists all persons in address book whose details (Name, Phone or Tag) contains any of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class FindCommand extends Command {

    public static final String COMMAND_WORD = "find";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all persons whose names contain any of "
            + "the specified keywords (case-insensitive) and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "John (OPTIONAL)"
            + PREFIX_MAIN_PHONE + "999 (OPTIONAL)"
            + PREFIX_TAG + "friends (OPTIONAL)";

    private final List<Predicate<Person>> listOfPredicate = new ArrayList<>();

    /**
     * @param predicates to filter a person by (Name, phone, tags)
     */
    public FindCommand(List<Predicate<Person>> predicates) {
        for (Predicate<Person> p : predicates) {
            listOfPredicate.add(p);
        }
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);

        @SuppressWarnings("unchecked")
        // Converting list of predicates into array, this is allowed as list of predicates
        // already ensures that all elements is a Predicate<Person> so the casting is sage.
        Predicate<Person> [] predicatesArray = listOfPredicate.toArray(new Predicate[0]);
        model.updatePersonListFilter(predicatesArray);

        return new CommandResult(
                String.format(Messages.MESSAGE_PERSONS_LISTED_OVERVIEW, model.getPersonList().size()));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof FindCommand)) {
            return false;
        }

        FindCommand otherFindCommand = (FindCommand) other;
        if (otherFindCommand.listOfPredicate.size() != listOfPredicate.size()) {
            return false;
        }

        for (int i = 0; i < listOfPredicate.size(); i++) {
            if (!listOfPredicate.get(i).equals(
                    otherFindCommand.listOfPredicate.get(i))) {
                return false;
            }
        }
        return true;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("predicate", listOfPredicate)
                .toString();
    }
}
