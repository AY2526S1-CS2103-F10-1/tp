package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

/**
 * Contains unit tests for methods in ParserUtil class
 */
public class ParserUtilSecondTest {
    @Test
    public void parseParametersAndLabels_emptyString_returnEmptyList() {
        assertEquals(new ArrayList<>(), ParserUtil.parseParametersAndLabels(""));
    }

    @Test
    public void parseParametersAndLabels_validInput_returnCorrectList() {
        String input = "TEST PARAMETER 1";
        List<String> correctList = new ArrayList<>(List.of("TEST PARAMETER 1"));
        assertEquals(correctList, ParserUtil.parseParametersAndLabels(input));
    }

    @Test
    public void parseParametersAndLabels_validInput2_returnCorrectList() {
        String input = "TEST PARAMETER 1 (L a b e l )";
        List<String> correctList = new ArrayList<>(List.of("TEST PARAMETER 1", "(L a b e l )"));
        assertEquals(correctList, ParserUtil.parseParametersAndLabels(input));
    }

    @Test
    public void parseParametersAndLabels_validInput3_returnCorrectList() {
        String input = "TEST PARAMETER 1 (L a b e l 1) TEST PARAMETER 2 (L a b e l 2)";
        List<String> correctList = new ArrayList<>(List.of("TEST PARAMETER 1", "(L a b e l 1)",
                "TEST PARAMETER 2", "(L a b e l 2)"));
        assertEquals(correctList, ParserUtil.parseParametersAndLabels(input));
    }

    @Test
    public void parseParametersAndLabels_validInput4_returnCorrectList() {
        String input = "TEST PARAMETER 1 (L a b e l 1";
        List<String> correctList = new ArrayList<>(List.of("TEST PARAMETER 1", "(L a b e l 1"));
        assertEquals(correctList, ParserUtil.parseParametersAndLabels(input));
    }

    @Test
    public void parseParametersAndLabels_extraSpaces_returnEmptyList() {
        String input = "TEST PARAMETER 1  (L a b e l 1) TEST PARAMETER 2 (L a b e l 2)";
        assertEquals(new ArrayList<>(), ParserUtil.parseParametersAndLabels(input));
    }

    @Test
    public void parseParametersAndLabels_extraSpaces2_returnEmptyList() {
        String input = "TEST PARAMETER 1 (L a b e l 1)  TEST PARAMETER 2 (L a b e l 2)";
        assertEquals(new ArrayList<>(), ParserUtil.parseParametersAndLabels(input));
    }

    @Test
    public void parseParametersAndLabels_extraSpaces3_returnEmptyList() {
        String input = "TEST PARAMETER 1 (L a b e l 1) TEST PARAMETER 2 (L a b e l 2)  TEST PARAMETER 3";
        assertEquals(new ArrayList<>(), ParserUtil.parseParametersAndLabels(input));
    }

    @Test
    public void parseParametersAndLabels_missingSpaces_returnEmptyList() {
        String input = "TEST PARAMETER 1(L a b e l 1) TEST PARAMETER 2 (L a b e l 2)";
        assertEquals(new ArrayList<>(), ParserUtil.parseParametersAndLabels(input));
    }

    @Test
    public void parseParametersAndLabels_missingSpaces2_returnEmptyList() {
        String input = "T(L a b e l 1) TEST PARAMETER 2 (L a b e l 2)";
        assertEquals(new ArrayList<>(), ParserUtil.parseParametersAndLabels(input));
    }

    @Test
    public void parseParametersAndLabels_missingSpaces3_returnEmptyList() {
        String input = "TEST PARAMETER 1(L a b e l 1) TEST PARAMETER 2 (L a b e l 2)a";
        assertEquals(new ArrayList<>(), ParserUtil.parseParametersAndLabels(input));
    }

    @Test
    public void parseParametersAndLabels_missingSpaces4_returnEmptyList() {
        String input = "TEST PARAMETER 1(L a b e l 1)a TEST PARAMETER 2 (L a b e l 2)";
        assertEquals(new ArrayList<>(), ParserUtil.parseParametersAndLabels(input));
    }

    @Test
    public void parseParametersAndLabels_missingSpaces5_returnEmptyList() {
        String input = "TEST PARAMETER 1 (L a b e l 1) T(L a b e l 2)";
        assertEquals(new ArrayList<>(), ParserUtil.parseParametersAndLabels(input));
    }

    @Test
    public void parseParametersAndLabels_incorrectFormat_returnEmptyList() {
        String input = "TEST PARAMETER 1 (L a b e l 1) TEST PARAMETER 2";
        assertEquals(new ArrayList<>(), ParserUtil.parseParametersAndLabels(input));
    }

    @Test
    public void parseParametersAndLabels_incorrectFormat2_returnEmptyList() {
        String input = "TEST PARAMETER 1 (L a b e l 1) TEST PARAMETER 2";
        assertEquals(new ArrayList<>(), ParserUtil.parseParametersAndLabels(input));
    }

    @Test
    public void parseParametersAndLabels_incorrectFormat3_returnEmptyList() {
        String input = "TEST PARAMETER 1 (L a b e l 1)TEST PARAMETER 2";
        assertEquals(new ArrayList<>(), ParserUtil.parseParametersAndLabels(input));
    }
}
