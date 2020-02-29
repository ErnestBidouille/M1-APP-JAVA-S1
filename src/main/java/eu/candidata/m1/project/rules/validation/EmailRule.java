package eu.candidata.m1.project.rules.validation;

import org.apache.commons.validator.routines.EmailValidator;

public class EmailRule implements TypeRule<String> {

    /**
     * Check if a given string follow email format, excluding local addresses and
     * TLD addresses
     * 
     * @param email
     * @return <code>true</code> if the given string follow email format, otherwise
     *         <code>false</code>
     */
    @Override
    public boolean test(String email) {
        return EmailValidator.getInstance().isValid(email);
    }

    public static final String[] DAUPHINE_EMAIL_DOMAINS = { "dauphine.eu",
            "dauphine.psl.eu", "lamsade.dauphine.fr", };

    /**
     * Check if a given string follow Paris Dauphine's email format
     * 
     * @param email The email to validate
     * @return <code>true</code> if given string follow Paris Dauphine's email
     *         format, otherwise <code>false</code>
     */
    public static boolean isDauphineEmail(String email) {
        if (!ValidationRules.IS_EMAIL.test(email)) {
            return false;
        }
        final String emailDomain = email.split("@")[1];
        for (String string : DAUPHINE_EMAIL_DOMAINS) {
            if (string.equals(emailDomain)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String valueOf(String string) {
        return string;
    }
}
