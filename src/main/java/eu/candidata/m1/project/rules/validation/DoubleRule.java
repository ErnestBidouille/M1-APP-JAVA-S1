package eu.candidata.m1.project.rules.validation;

/**
 * An DoubleRule is a basic Rule that checks a value conforms to a valid
 * java.lang.Double value.
 * 
 * @author ernestbidouille
 *
 */
public class DoubleRule implements TypeRule<Double> {

    /**
     * Check if given string is a double or not
     * 
     * @param string The value to check
     * @return <code>true</code> if given string is a double or <code>false</code>
     */
    @Override
    public boolean test(String string) {
        try {
            Double.valueOf(string);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Returns the value parsed from the given String.
     */
    @Override
    public Double valueOf(String string) {
        return Double.valueOf(string);
    }

    /**
     * Helper function to determine whether a Double represents a valid age.
     * 
     * @param value The value to check
     * @return <code>true</code> if the given double is a valid age or
     *         <code>false</code>
     */
    public static boolean isAge(double value) {
        return value >= 0 && value <= 120;
    }
}
