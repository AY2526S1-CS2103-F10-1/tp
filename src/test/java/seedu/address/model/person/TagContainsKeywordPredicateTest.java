package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.Test;
import seedu.address.testutil.PersonBuilder;

public class TagContainsKeywordPredicateTest {

    @Test
    public void test_TagContainsKeywords_returnsTrue() {
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
