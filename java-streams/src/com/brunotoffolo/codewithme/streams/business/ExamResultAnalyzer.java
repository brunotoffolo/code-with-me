package com.brunotoffolo.codewithme.streams.business;

import com.brunotoffolo.codewithme.streams.model.ExamResult;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Simple program to retrieve a list of registry numbers of the students
 * who got the higher 100 grades in an imaginary exam. The list of grades
 * shall be traversed to find the highest numbers and the registry number
 * of their respective students shall be retrieved after this filtering
 * occurs.
 * <p>
 * The code below shows how to do such an operation using classic Java 7
 * for-each loops to perform this computation and, also, a new strategy
 * to perform the same thing making use of the new Java 8 concept of
 * streams. Additional examples were also introduced to demonstrate what
 * can be achieved when leveraging the power of Java streams.
 * <p>
 * The time measurements realized in this class should not be seen as a
 * real benchmark as they do not perform any kind of warm-up and do not
 * handle cache or other things that may impact the execution time for the
 * operations. They are just for simple demonstration purposes.
 *
 * @author Bruno Toffolo
 */
public class ExamResultAnalyzer {

    /**
     * Main method for the example. Invokes the operations that are being
     * analyzed.
     * @param args
     */
    public static void main(String[] args) {
        List<ExamResult> examResults = prepareExamResults(10000000L);

        // Simple stream expression to check if there is any very high grade
        boolean match = examResults.stream().anyMatch(r -> r.getGrade() > 9.998);
        if (match) {
            System.out.println("At least one student got a grade higher than 9.998");
        }

        List<Integer> manualResults = manualIteration(examResults);
        List<Integer> streamResults = streamOperations(examResults);
        System.out.println("Results match = " + manualResults.equals(streamResults));

        // Just another operation to demonstrate the power of streams
        getAverageGrade(examResults);
    }

    /**
     * Creates a list of random exam results, consisting of a registry number and
     * a grade for the exam.
     * @param size Number of exam results that should be generated
     * @return List of random exam results
     */
    private static List<ExamResult> prepareExamResults(long size) {
        List<ExamResult> examResults = new ArrayList<>();
        Random random = new Random();
        for (int i = 0; i < size; i++) {
            ExamResult result = new ExamResult(Math.abs(random.nextInt()), random.nextDouble() * 10);
            examResults.add(result);
        }
        return examResults;
    }

    /**
     * Performs a manual iteration over all exam results to retrieve the top 100
     * students.
     * @param examResults List of exam results
     * @return List of top 100 students
     */
    private static List<Integer> manualIteration(List<ExamResult> examResults) {
        long startManual = System.currentTimeMillis();
        List<ExamResult> sortedResultList = examResults.subList(0, examResults.size());
        Collections.sort(sortedResultList);

        List<ExamResult> topHundredResults = sortedResultList.subList(0, 100);

        List<Integer> registries = new ArrayList<>();
        for (ExamResult result : topHundredResults) {
            registries.add(result.getId());
        }

        // This for-each loop could also be done in a "stream-like" way with
        // the following line of code:
        // topHundredResults.forEach(result -> registries.add(result.getId()));

        Collections.sort(registries);
        long endManual = System.currentTimeMillis();

        System.out.println("Time consumed for manual iteration = " + (endManual - startManual));

        return registries;
    }

    /**
     * Uses stream operations to retrieve the top 100 students from the exam results list.
     * @param examResults List of exam results
     * @return List of top 100 students
     */
    private static List<Integer> streamOperations(List<ExamResult> examResults) {
        long startStreams = System.currentTimeMillis();
        List<Integer> streamRegistries = examResults.stream()
                .sorted(Comparator.comparing(ExamResult::getGrade).reversed())
                .limit(100)
                .sorted(Comparator.comparing(ExamResult::getId))
                .map(ExamResult::getId)
                .collect(Collectors.toList());
        long endStreams = System.currentTimeMillis();

        System.out.println("Time consumed for stream operations = " + (endStreams - startStreams));

        return streamRegistries;
    }

    /**
     * Gets the average grade for all the students that took the exam.
     * @param examResults List of exam results
     */
    private static void getAverageGrade(List<ExamResult> examResults) {
        OptionalDouble average = examResults
                .stream()
                .mapToDouble(r -> r.getGrade())
                .average();
        average.ifPresent(System.out::println);
    }

}
