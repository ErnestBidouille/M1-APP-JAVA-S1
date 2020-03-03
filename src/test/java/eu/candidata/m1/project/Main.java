package eu.candidata.m1.project;

import java.io.File;
import java.io.FileReader;
import java.io.Reader;
import java.util.NoSuchElementException;
import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import eu.candidata.m1.project.format.csv.CsvDataManager;
import eu.candidata.m1.project.format.json.JsonDataAnonymizer;
import eu.candidata.m1.project.format.json.JsonDataDescriptor;
import eu.candidata.m1.project.format.json.JsonDataValidator;

public class Main {

    private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) throws Throwable {
        if (args.length != 4) {
            throw new IllegalArgumentException("4 or 5 args needed");
        }
        
    }
}
