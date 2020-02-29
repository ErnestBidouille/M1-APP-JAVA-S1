package eu.candidata.m1.project.rules.anonymization;

import java.util.function.Function;

@FunctionalInterface
public interface Anonymizer extends Function<String, String> {
}
