package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Collections;

import org.junit.jupiter.api.Test;

public class TagContainsKeywordPredicateTest {

    @Test
    public void test_TagContainsKeywords_returnsTrue() {
        TagContainsKeywordPredicate predicate = new TagContainsKeywordPredicate(
                Collections.singletonList("friend"));



    }
}
