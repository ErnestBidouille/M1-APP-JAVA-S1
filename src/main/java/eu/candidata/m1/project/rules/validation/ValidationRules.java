package eu.candidata.m1.project.rules.validation;

/**
 * 
 * @author ernestbidouille
 *
 */
public class ValidationRules {

    public static final TypeRule<Integer> IS_INTEGER = new IntegerRule();
    private static final Rule IS_INTEGER_AGE = IS_INTEGER.then(IntegerRule::isAge);
    public static final Rule IS_AGE = IS_INTEGER_AGE;
    public static final TypeRule<String> IS_STRING = new StringRule();
    public static final TypeRule<String> IS_EMAIL = new EmailRule();
    public static final Rule IS_DAUPHINE_EMAIL = IS_EMAIL.then(EmailRule::isDauphineEmail);

    private ValidationRules() {
    }
}
