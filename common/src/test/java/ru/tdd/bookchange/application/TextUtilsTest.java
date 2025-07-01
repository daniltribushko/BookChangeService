package ru.tdd.bookchange.application;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.tdd.bookchange.application.utils.TextUtils;

class TextUtilsTest {

    @Test
    void isEmptyTestSuccess() {
        Assertions.assertTrue(TextUtils.isEmpty(""));
        Assertions.assertTrue(TextUtils.isEmpty(null));
    }

    @Test
    void isEmptyTestFail() {
        Assertions.assertFalse(TextUtils.isEmpty("abcdef"));
        Assertions.assertFalse(TextUtils.isEmpty("test_string"));
    }

    @Test
    void isNotEmptyTestSuccess() {
        Assertions.assertTrue(TextUtils.isNotEmpty("string"));
        Assertions.assertTrue(TextUtils.isNotEmpty("not_empty_string"));
    }

    @Test
    void isNotEmptyTestFail() {
        Assertions.assertFalse(TextUtils.isNotEmpty(""));
        Assertions.assertFalse(TextUtils.isNotEmpty(null));
    }

    @Test
    void firstUpperCaseTestSuccess() {
        String expected1 = "Test_string";
        String expected2 = "First upper case";
        String actual1 = TextUtils.firstUpperCase("test_string");
        String actual2 = TextUtils.firstUpperCase("first upper case");

        Assertions.assertEquals(expected1, actual1);
        Assertions.assertEquals(expected2, actual2);
    }

    @Test
    void firstUpperCaseTestFail() {
        String expected1 = "tEst";
        String expected2 = "teSt";
        String expected3 = "tesT";

        String actual = TextUtils.firstUpperCase("test");

        Assertions.assertNotEquals(expected1, actual);
        Assertions.assertNotEquals(expected2, actual);
        Assertions.assertNotEquals(expected3, actual);
    }
}
