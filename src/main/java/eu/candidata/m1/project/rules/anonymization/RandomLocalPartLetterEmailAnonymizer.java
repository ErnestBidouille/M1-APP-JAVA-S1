package eu.candidata.m1.project.rules.anonymization;

import eu.candidata.m1.project.rules.validation.ValidationRules;

public class RandomLocalPartLetterEmailAnonymizer implements Anonymizer {

    /**
     * Randomize first part of a given email string
     * 
     * @param email
     * @return first part randomized email string
     * @throws IllegalArgumentException if given string isn't email format
     */
    @Override
    public String apply(String email) {
        if (ValidationRules.IS_EMAIL.not().test(email)) {
            // throw new IllegalArgumentException("Given String isn't an
            // email.");
            return "Not an email";
        }
        String[] emailParts = email.split("@");
        return new RandomLetterAnonymizer().apply(emailParts[0]) + '@' + emailParts[1];
    }
}
