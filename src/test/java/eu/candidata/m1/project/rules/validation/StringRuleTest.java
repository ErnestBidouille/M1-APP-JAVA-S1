package eu.candidata.m1.project.rules.validation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class StringRuleTest {

    @Test
    void testMatch() {
        StringRule instance = new StringRule();
        assertTrue(instance.test("Test"));
        assertFalse(instance.test(null));
        assertFalse(instance.test(""));
    }

    @Test
    void testValueOf() {
        StringRule instance = new StringRule();
        assertEquals("Test", instance.valueOf("Test"));
        assertEquals(null, instance.valueOf(null));
        assertEquals("", instance.valueOf(""));
    }
}
