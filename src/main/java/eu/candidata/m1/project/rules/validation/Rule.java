package eu.candidata.m1.project.rules.validation;

import java.util.function.Predicate;

/**
 * A Rule is a function that takes a String as input and verifies it matches the
 * rule it encodes. Rules can be combined to form more complex rules.
 * 
 * @author theophile
 *
 */
@FunctionalInterface
public interface Rule extends Predicate<String> {

    /**
     * Creates a new Rule that return the opposite of the base rule.
     * 
     * @return The negated Rule
     */
    default public Rule not() {
        return s -> !test(s);
    }

    /**
     * Combines two Rules into a single one. The new Rule checks that a value
     * matches both Rules.
     * 
     * @param rule The other Rule to check
     * @return The new Rule
     */
    default public Rule and(Rule rule) {
        return s -> test(s) && rule.test(s);
    }

    /**
     * Combines two Rules into a single one. The new Rule checks that a value
     * matches any of the two Rules.
     * 
     * @param rule The other Rule to check
     * @return The new Rule
     */
    default public Rule or(Rule rule) {
        return s -> test(s) || rule.test(s);
    }
}
