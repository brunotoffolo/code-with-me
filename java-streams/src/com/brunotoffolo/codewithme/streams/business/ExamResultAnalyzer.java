package com.brunotoffolo.codewithme.streams.business;

import com.brunotoffolo.codewithme.streams.model.ExamResult;

import java.util.*;

/**
 * Simple program to retrieve a list of registry numbers of the students
 * who got the higher 100 grades in an imaginary exam. The list of grades
 * shall be traversed to find the highest numbers and the registry number
 * of their respective students shall be retrieved after this filtering
 * occurs.
 * <p>
 * The code below shows how to do such an operation using classic Java 7
 * for-each loops to perform this computation.
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
        List<ExamResult> examResults = prepareexamResults(10000000L);

        List<Integer> manualResults = manualIteration(examResults);
    }

    /**
     * Creates a list of random exam results, consisting of a registry number and
     * a grade for the exam.
     * @param size Number of exam results that should be generated
     * @return List of random exam results
     */
    private static List<ExamResult> prepareexamResults(long size) {
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

        Collections.sort(registries);
        long endManual = System.currentTimeMillis();

        System.out.println("Time consumed for manual iteration = " + (endManual - startManual));

        return registries;
    }

}
