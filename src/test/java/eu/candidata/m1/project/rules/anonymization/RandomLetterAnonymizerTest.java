package eu.candidata.m1.project.rules.anonymization;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;

import eu.candidata.m1.project.rules.anonymization.RandomLetterAnonymizer;

class RandomLetterAnonymizerTest {

    @Test
    void testRandomize() {
        String testString = "Ceci est une String de test";
        String randomizedString = new RandomLetterAnonymizer().apply(testString);
        assertEquals(testString.length(), randomizedString.length());
        assertNotEquals(testString, randomizedString);
    }
}
