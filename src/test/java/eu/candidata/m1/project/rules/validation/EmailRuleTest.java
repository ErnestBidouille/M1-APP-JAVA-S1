package eu.candidata.m1.project.rules.validation;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class EmailRuleTest {

    @Test
    void testEmail() {
        Rule rule = new EmailRule();
        assertTrue(rule.test("email-to-test@example.com"));
        assertFalse(rule.test("email-to-test@@example.com"));
        assertFalse(rule.test(null));
        assertFalse(rule.test("email-to-test@localhost"));
        assertFalse(rule.test("email-to-test@.mail"));
        assertFalse(rule.test("@hotmail.fr"));
        assertFalse(rule.test("@hotmail.fr."));
    }

    @Test
    void testEmailDauphine() {
        Rule rule = new EmailRule().then(EmailRule::isDauphineEmail);
        for (String domain : EmailRule.DAUPHINE_EMAIL_DOMAINS) {
            assertTrue(rule.test("tested-email@" + domain));
        }
        for (String domain : EmailRule.DAUPHINE_EMAIL_DOMAINS) {
            assertFalse(rule.test("tested-email@@" + domain));
        }
        for (String domain : EmailRule.DAUPHINE_EMAIL_DOMAINS) {
            assertFalse(rule.test("tested-email@" + domain + ".eu"));
        }
        assertFalse(rule.test("email-to-test@example.com"));
        assertFalse(rule.test("email-to-test@localhost"));
        assertFalse(rule.test("email-to-test@.mail"));
        Rule dauphineRule = EmailRule::isDauphineEmail;
        assertFalse(dauphineRule.test("email@.email"));
    }
}
