package seedu.address.model.person;

import java.util.function.Predicate;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.util.ToStringBuilder;

/**
 * Checks a {@code Person}'s {@code Phone} contains the keywords given.
 */
public class NumberContainsKeywordPredicate implements Predicate<Person> {
    private static final Logger logger = LogsCenter.getLogger(NameContainsKeywordsPredicate.class);

    private final String keyword;

    public NumberContainsKeywordPredicate(String keywords) {
        this.keyword = keywords;
    }

    /**
     * @param person the input argument
     * @return boolean on whether the person's phone contains the keyword
     */
    @Override
    public boolean test(Person person) {
        String logMessage = String.format("NumberContainsKeywordsPredicate check using: "
                + "Keywords=%s and Number=%s.", keyword, person.getPhone());
        logger.info(logMessage);

        return person.checkNumberContainsKeyword(keyword);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof NumberContainsKeywordPredicate)) {
            return false;
        }

        NumberContainsKeywordPredicate otherNumberContainsKeywordPredicate = (NumberContainsKeywordPredicate) other;
        return keyword.equals(otherNumberContainsKeywordPredicate.keyword);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("keywords", keyword).toString();
    }

}




