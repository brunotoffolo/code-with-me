package com.brunotoffolo.codewithme.streams.business;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * Simple class used to parse the contents of a book.
 * <p>
 * The time measurements realized in this class should not be seen as a
 * real benchmark as they do not perform any kind of warm-up and do not
 * handle cache or other things that may impact the execution time for the
 * operations. They are just for simple demonstration purposes.
 * <p>
 * This example is based in a plain text version of the book "The Adventures
 * of Tom Sawyer", by Mark Twain, as available in Project Gutenberg's site
 * (https://www.gutenberg.org/ebooks/74).
 *
 * @author Bruno Toffolo
 */
public class BookParser {

    private static final String DESIRED_WORD = " even ";

    public static void main(String[] args) {
        List<String> bookWordsList;

        try {
            bookWordsList = Files.readAllLines(Paths.get("resources/pg74.txt"), StandardCharsets.UTF_8);
        } catch (IOException e) {
            bookWordsList = new ArrayList<>();
        }

        List<String> manualListIteration = manualListIteration(bookWordsList);
        List<String> manualArrayIteration = manualArrayIteration(
                bookWordsList.toArray(new String[bookWordsList.size()]));

        if (!manualListIteration.equals(manualArrayIteration)) {
            System.err.println("Methods do not generate the same results");
        }
    }

    /**
     * Searches for the number of lines that contain a specific word in the book.
     * Performs this operation through the usual Java 7 approach, iterating a list.
     *
     * @param bookWordsList Book contents
     * @return List of lines that contain the word, converted to uppercase
     */
    private static List<String> manualListIteration(List<String> bookWordsList) {
        long startManualList = System.currentTimeMillis();
        List<String> manuallyFilteredList = new ArrayList<>();
        for (String line : bookWordsList) {
            if (line.contains(DESIRED_WORD)) {
                manuallyFilteredList.add(line.toUpperCase());
            }
        }
        long endManualList = System.currentTimeMillis();
        System.out.println("Java 7 for-each in a list: " + (endManualList - startManualList));
        return manuallyFilteredList;
    }

    /**
     * Searches for the number of lines that contain a specific word in the book.
     * Performs this operation through the usual Java 7 approach, but iterating over an
     * array of Strings.
     *
     * @param bookWordsArray Book contents
     * @return List of lines that contain the word, converted to uppercase
     */
    private static List<String> manualArrayIteration(String[] bookWordsArray) {
        long startManualArray = System.currentTimeMillis();
        List<String> manuallyFilteredArray = new ArrayList<>();
        for (String line : bookWordsArray) {
            if (line.contains(DESIRED_WORD)) {
                manuallyFilteredArray.add(line.toUpperCase());
            }
        }
        long endManualArray = System.currentTimeMillis();
        System.out.println("Java 7 for-each in an array: " + (endManualArray - startManualArray));
        return manuallyFilteredArray;
    }

}
