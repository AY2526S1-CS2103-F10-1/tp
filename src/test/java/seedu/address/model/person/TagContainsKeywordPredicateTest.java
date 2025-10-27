package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.PersonBuilder;

public class TagContainsKeywordPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("first");
        List<String> secondPredicateKeywordList = Arrays.asList("first", "second");

        TagContainsKeywordPredicate firstPredicate = new TagContainsKeywordPredicate(firstPredicateKeywordList);
        TagContainsKeywordPredicate secondPredicate = new TagContainsKeywordPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        TagContainsKeywordPredicate firstPredicateCopy = new TagContainsKeywordPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different person -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_tagContainsKeywords_returnsTrue() {
        //One tag
        TagContainsKeywordPredicate predicate = new TagContainsKeywordPredicate(
                Collections.singletonList("friend"));
        assertTrue(predicate.test(new PersonBuilder().withTags("friend").build()));

        //Multiple tags
        predicate = new TagContainsKeywordPredicate(
                Collections.singletonList("friend"));
        assertTrue(predicate.test(new PersonBuilder().withTags("friend", "food", "buy").build()));

        //Mixed Cases
        predicate = new TagContainsKeywordPredicate(
                Collections.singletonList("friend"));
        assertTrue(predicate.test(new PersonBuilder().withTags("fRieNd").build()));
    }

    @Test
    public void test_tagDoesNotContainKeywords_returnsFalse() {
        // Non-matching keywords
        TagContainsKeywordPredicate predicate = new TagContainsKeywordPredicate(
                Collections.singletonList("friend")
        );
        assertFalse(predicate.test(new PersonBuilder().withTags("Alice").build()));

        // Keywords match phone, email and address, but does not match name
        predicate = new TagContainsKeywordPredicate(Arrays.asList("12345", "alice@email.com", "Main", "Street"));
        assertFalse(predicate.test(new PersonBuilder().withTags("Alice").withPhone("12345")
                .withEmail("alice@email.com").withAddress("Main Street").withTags("friend").build()));
    }
}
