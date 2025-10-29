package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.PersonBuilder;

public class NumberContainsKeywordPredicateTest {

    @Test
    public void equals() {
        String firstPredicateNumber = "999";
        String secondPredicateNumber = "888";

        NumberContainsKeywordPredicate firstPredicate = new NumberContainsKeywordPredicate(firstPredicateNumber);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        NumberContainsKeywordPredicate firstPredicateCopy = new NumberContainsKeywordPredicate(firstPredicateNumber);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different person -> returns false
        assertFalse(firstPredicate.equals(secondPredicateNumber));
    }

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

