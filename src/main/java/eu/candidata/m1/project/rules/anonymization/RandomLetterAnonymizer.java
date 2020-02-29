package eu.candidata.m1.project.rules.anonymization;

import org.apache.commons.lang3.RandomStringUtils;

public class RandomLetterAnonymizer implements Anonymizer {

    /**
     * Randomize given string with alphabetic chars.
     * 
     * @param string
     * @return randomized string
     */
    @Override
    public String apply(String string) {
        return RandomStringUtils.randomAlphabetic(string.length());
    }
}
