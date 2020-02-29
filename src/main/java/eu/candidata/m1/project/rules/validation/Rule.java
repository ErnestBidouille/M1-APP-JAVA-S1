package eu.candidata.m1.project.rules.validation;

import java.util.function.Predicate;

@FunctionalInterface
public interface Rule extends Predicate<String> {

    default public Rule not() {
        return s -> !test(s);
    }

    default public Rule and(Rule rule) {
        return s -> test(s) && rule.test(s);
    }

    default public Rule or(Rule rule) {
        return s -> test(s) || rule.test(s);
    }
}
