package eu.candidata.m1.project.rules.validation;

/**
 * An IntegerRule is a basic Rule that checks a value conforms to a valid
 * java.lang.Integer value.
 * 
 * @author theophile
 *
 */
public class IntegerRule implements TypeRule<Integer> {

    /**
     * Check if given string is an integer or not.
     * 
     * @param string The value to check
     * @return <code>true</code> if given string is an integer or <code>false</code>
     */
    @Override
    public boolean test(String string) {
        try {
            Integer.valueOf(string);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * Returns the value parsed from the given String.
     */
    @Override
    public Integer valueOf(String string) {
        return Integer.valueOf(string);
    }

    /**
     * Helper function to determine whether an Integer represents a valid age.
     * 
     * @param value The value to check
     * @return <code>true</code> if the given integer is a valid age or
     *         <code>false</code>
     */
    public static boolean isAge(int value) {
        return value >= 0 && value <= 120;
    }
}
