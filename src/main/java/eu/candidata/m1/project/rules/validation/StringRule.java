package eu.candidata.m1.project.rules.validation;

/**
 * An StringRule is a basic Rule that checks a value conforms to a valid
 * java.lang.String value.
 * 
 * @author ernestbidouille
 *
 */
public class StringRule implements TypeRule<String> {

    /**
     * Check if given string is not an empty String or <code>null</code>.
     * 
     * @param string The value to check
     * @return <code>true</code> if given string is not empty or <code>null</code>
     *         or <code>false</code>
     */
    @Override
    public boolean test(String string) {
        if (string == null) {
            return false;
        }
        return !string.isEmpty();
    }

    /**
     * Returns the given string.
     */
    @Override
    public String valueOf(String string) {
        return string;
    }
}
