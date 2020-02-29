package eu.candidata.m1.project.rules.validation;

import java.util.function.Predicate;

/**
 * A TypeRule over T is a Rule that is used to check if a value conforms to a
 * given type T. It gives the useful capability to chain validation and use the
 * resulting value to use with a more precise validation rule (a predicate over
 * T).
 * 
 * @author theophile
 *
 * @param <T> The type
 */
public interface TypeRule<T> extends Rule {

    public T valueOf(String string);

    public default Rule then(Predicate<T> predicate) {
        return s -> test(s) && predicate.test(valueOf(s));
    }
}
