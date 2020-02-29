package eu.candidata.m1.project.rules.anonymization;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;

import eu.candidata.m1.project.rules.anonymization.RandomLocalPartLetterEmailAnonymizer;

class RandomLocalPartLetterEmailAnonymizerTest {

    @Test
    void testRandomizeStringThrowsExeception() {
        String testString = "Ceci est une String de test";
        assertEquals("Not an email", new RandomLocalPartLetterEmailAnonymizer().apply(testString));
    }

    @Test
    void testRandomizeEmailString() {
        String testString = "email-to-randomize@gmail.com";
        String randomizedEmail = new RandomLocalPartLetterEmailAnonymizer().apply(testString);
        String[] splittedTest = testString.split("@");
        String[] splittedRandomized = randomizedEmail.split("@");
        assertEquals(randomizedEmail.length(), testString.length());
        assertEquals(splittedTest[1], splittedRandomized[1]);
        assertNotEquals(splittedTest[0], splittedRandomized[0]);
    }
}
