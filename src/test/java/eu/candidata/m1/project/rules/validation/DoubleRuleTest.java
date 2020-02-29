package eu.candidata.m1.project.rules.validation;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class DoubleRuleTest {

    @Test
    void testDoubleRule() {
        DoubleRule instance = new DoubleRule();
        assertTrue(instance.test("2"));
        assertTrue(instance.test("2.2"));
        assertTrue(instance.test("2.2f"));
        assertTrue(instance.test("-2"));
        assertTrue(instance.test("-2.2"));
        assertTrue(instance.test("-2.2f"));
        assertFalse(instance.test("texte"));
        assertFalse(instance.test(null));
    }

    @Test
    void testAge() {
        Rule rule = new DoubleRule().then(DoubleRule::isAge);
        assertTrue(rule.test("0.00"));
        assertTrue(rule.test("60.1"));
        assertTrue(rule.test("12e+1"));
        assertFalse(rule.test("-1.0d"));
        assertFalse(rule.test("121"));
    }
}
