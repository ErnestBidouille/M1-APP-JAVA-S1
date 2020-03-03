package eu.candidata.m1.project;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {

    private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) throws Throwable {
        if (args.length != 4) {
            throw new IllegalArgumentException("4 or 5 args needed");
        }
    }
}
