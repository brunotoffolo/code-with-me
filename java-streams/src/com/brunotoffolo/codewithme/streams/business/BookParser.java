package com.brunotoffolo.codewithme.streams.business;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.OptionalDouble;
import java.util.stream.Collectors;

/**
 * Simple class used to parse the contents of a book and demonstrate some
 * useful aspects of Java 8 streams.
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
        List<String> sequentialStreamIteration = sequentialStreamIteration(bookWordsList);
        List<String> parallelStreamIteration = parallelStreamIteration(bookWordsList);

        if (!manualListIteration.equals(manualArrayIteration) ||
                !manualListIteration.equals(sequentialStreamIteration) ||
                !manualListIteration.equals(parallelStreamIteration)) {
            System.err.println("Methods do not generate the same results");
        }

        countLinesThatStartWithUppercase(bookWordsList);
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

    /**
     * Searches for the number of lines that contain a specific word in the book.
     * Performs this operation through the new Java 8 approach, using a sequential
     * Stream.
     *
     * @param bookWordsList Book contents
     * @return List of lines that contain the word, converted to uppercase
     */
    private static List<String> sequentialStreamIteration(List<String> bookWordsList) {
        long startSequentialStream = System.currentTimeMillis();
        List<String> sequentialStream = bookWordsList.stream()
                .filter(line -> line.contains(DESIRED_WORD))
                .map(line -> line.toUpperCase())
                .collect(Collectors.toList());
        long endSequentialStream = System.currentTimeMillis();
        System.out.println("Java 8 sequential stream: " + (endSequentialStream - startSequentialStream));
        return sequentialStream;
    }

    /**
     * Searches for the number of lines that contain a specific word in the book.
     * Performs this operation through the new Java 8 approach, but using a parallel
     * Stream.
     *
     * @param bookWordsList Book contents
     * @return List of lines that contain the word, converted to uppercase
     */
    private static List<String> parallelStreamIteration(List<String> bookWordsList) {
        long startParallelStream = System.currentTimeMillis();
        List<String> parallelStream = bookWordsList.parallelStream()
                .filter(line -> line.contains(DESIRED_WORD))
                .map(line -> line.toUpperCase())
                .collect(Collectors.toList());
        long endParallelStream = System.currentTimeMillis();
        System.out.println("Java 8 parallel stream: " + (endParallelStream - startParallelStream));
        return parallelStream;
    }

    /**
     * Counts how many lines start with an uppercase letter and them calculates the
     * average character count in each of them.
     * @param book Book contents
     */
    private static void countLinesThatStartWithUppercase(List<String> book) {
        long matches = book
                .stream()
                .filter(r -> r.matches("[A-Z].*"))
                .count();

        // We can't reuse the same stream :(
        OptionalDouble averageCharacters = book
                .stream()
                .filter(r -> r.matches("[A-Z].*"))
                .mapToInt(r -> r.length())
                .average();

        System.out.println("There are " + matches + " lines starting with an uppercase letter.");

        averageCharacters.ifPresent(value ->
                System.out.println("They have, in average, " + value + " characters each."));
    }

}
