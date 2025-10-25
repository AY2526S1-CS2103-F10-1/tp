package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.PersonBuilder;

public class NumberContainsKeywordPredicateTest {

    @Test
    public void test_numberContainsKeywords_returnTrue() {
        //Exact Number
        NumberContainsKeywordPredicate predicate = new NumberContainsKeywordPredicate(
                "999");
        assertTrue(predicate.test(new PersonBuilder().withPhone("999").build()));

        //Substring Number
        predicate = new NumberContainsKeywordPredicate(
                "999");
        assertTrue(predicate.test(new PersonBuilder().withPhone("9999999").build()));
    }

    @Test
    public void test_numberDoesNotContainKeywords_returnsFalse() {
        // Non-matching
        NumberContainsKeywordPredicate predicate = new NumberContainsKeywordPredicate(
                "999");
        assertFalse(predicate.test(new PersonBuilder().withPhone("888").build()));
    }
}

