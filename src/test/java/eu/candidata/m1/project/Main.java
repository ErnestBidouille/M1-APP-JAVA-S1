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
        System.out.println("What do you want to do ? 1 - Anonymize, 2 - Validate");
        Integer inputInteger = 0;
        try (Scanner s = new Scanner(System.in)) {
            while (true) {
                try {
                    inputInteger = s.nextInt();
                } catch (NoSuchElementException e) {
                    System.out.println("You must provide integer value");
                    s.nextLine();
                }
                if (inputInteger == 1 || inputInteger == 2) {
                    break;
                }
                System.out.println("You must provide value between 1 or 2");
            }
            JsonDataDescriptor jsonDataDescriptor = new JsonDataDescriptor(new File(args[1]));
            System.out.println("Does your Csv have Headers ? yes - no");
            String input = "";
            while (true) {
                input = s.next();
                if (input.equals("yes") || input.equals("no")) {
                    break;
                }
                System.out.println("You must provide value between yes or no");
            }
            Reader reader = new FileReader(args[0]);
            try (
                    CsvDataManager csvDataManager = (input.equals("yes"))
                            ? CsvDataManager.getCsvReaderWithCsvHeaders(reader, jsonDataDescriptor)
                            : CsvDataManager.getCsvReaderWithOutCsvHeaders(reader, jsonDataDescriptor);) {
                if (inputInteger == 1) {
                    try {
                        JsonDataAnonymizer jsonDataAnonymizer = new JsonDataAnonymizer(new File(args[2]));
                        csvDataManager.anonymizeAndWrite(jsonDataAnonymizer, args[3]);
                    } catch (Exception e) {
                        LOGGER.error("An error has occured");
                        throw e;
                    }
                } else {
                    try {
                        JsonDataValidator jsonDataValidator = new JsonDataValidator(new File(args[2]));
                        csvDataManager.validateAndWrite(jsonDataValidator, args[3]);
                    } catch (Exception e) {
                        LOGGER.error("An error has occured");
                        throw e;
                    }
                }
            }
            System.out.println("Done");
        }
    }
}
