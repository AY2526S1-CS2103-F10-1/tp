package seedu.address.model.person;

import seedu.address.commons.util.StringUtil;
import seedu.address.commons.util.ToStringBuilder;

import java.util.List;
import java.util.function.Predicate;

/**
 * Checks a {@code Person}'s {@code Phone} matches any of the keywords given.
 */
public class NumberContainsKeywordPredicate implements Predicate<Person>{
    private final String keyword;

    public NumberContainsKeywordPredicate(String keywords) {
        this.keyword = keywords;
    }

    /**
     *
     * @param person the input argument
     * @return boolean on whether the person's phone contains the keyword
     */
    @Override
    public boolean test(Person person) {
        return person.getPhone().value.contains(keyword);
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




