package eu.candidata.m1.project.rules.validation;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class RuleTest {

    private static Rule trueRule = s -> true;
    private static Rule falseRule = s -> false;

    @Test
    void testRule() {
        assertTrue(trueRule.test(null));
        assertTrue(trueRule.test("abcd"));
        assertFalse(falseRule.test(null));
        assertFalse(falseRule.test("abcd"));
    }

    @Test
    void testNotRule() {
        assertFalse(trueRule.not().test(null));
        assertFalse(trueRule.not().test("abcd"));
        assertTrue(falseRule.not().test(null));
        assertTrue(falseRule.not().test("abcd"));
    }

    @Test
    void testAndRule() {
        assertTrue(trueRule.and(trueRule).test(null));
        assertTrue(trueRule.and(trueRule).test("abcd"));
        assertFalse(trueRule.and(falseRule).test(null));
        assertFalse(trueRule.and(falseRule).test("abcd"));
        assertFalse(falseRule.and(trueRule).test(null));
        assertFalse(falseRule.and(trueRule).test("abcd"));
        assertFalse(falseRule.and(falseRule).test(null));
        assertFalse(falseRule.and(falseRule).test("abcd"));
    }

    @Test
    void testOrRule() {
        assertTrue(trueRule.or(trueRule).test(null));
        assertTrue(trueRule.or(trueRule).test("abcd"));
        assertTrue(trueRule.or(falseRule).test(null));
        assertTrue(trueRule.or(falseRule).test("abcd"));
        assertTrue(falseRule.or(trueRule).test(null));
        assertTrue(falseRule.or(trueRule).test("abcd"));
        assertFalse(falseRule.or(falseRule).test(null));
        assertFalse(falseRule.or(falseRule).test("abcd"));
    }
}
