package seedu.address.model.person;

import seedu.address.commons.util.StringUtil;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.tag.Tag;

import java.util.List;
import java.util.Set;
import java.util.function.Predicate;

/**
 * Checks a {@code Person}'s {@code Tag} matches any of the keywords given.
 */
public class TagContainsKeywordPredicate implements Predicate<Person>{
    private final List<String> keywords;

    public TagContainsKeywordPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Person person) {
        Set<Tag> tags = person.getTags();
        return keywords.isEmpty() || keywords.stream()
                .anyMatch(keyword ->
                        tags.stream().anyMatch(
                                tag -> StringUtil.containsWordIgnoreCase(tag.tagName, keyword)
                        ));
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

        TagContainsKeywordPredicate otherNumberContainsKeywordPredicate = (TagContainsKeywordPredicate) other;
        return keywords.equals(otherNumberContainsKeywordPredicate.keywords);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("keywords", keywords).toString();
    }

}





