package eu.candidata.m1.project.rules.validation;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class IntegerRuleTest {

    @Test
    void testInteger() {
        Rule rule = new IntegerRule();
        assertTrue(rule.test("1"));
        assertTrue(rule.test("-1"));
        assertFalse(rule.test("e"));
        assertFalse(rule.test(null));
    }

    @Test
    void testAge() {
        Rule rule = new IntegerRule().then(IntegerRule::isAge);
        assertTrue(rule.test("0"));
        assertTrue(rule.test("60"));
        assertTrue(rule.test("120"));
        assertFalse(rule.test("-1"));
        assertFalse(rule.test("121"));
    }
}
