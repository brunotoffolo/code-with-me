package com.brunotoffolo.codewithme.streams.business;

import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * Sample class to demonstrate some different usage examples for infinite
 * streams.
 *
 * @author Bruno Toffolo
 */
public class InfiniteStream {

    public static void main(String[] args) {
        System.out.println("Maximum random number = " + maxRandomNumber());
        System.out.println("Gaussian Sum = " + gaussianSum());
    }

    /**
     * Generates a stream of random numbers and calculates the maximum
     * between the generated values.
     *
     * @return Maximum generated value
     */
    private static double maxRandomNumber() {
        Stream<Double> randomNumbers = Stream.generate(Math::random);

        return randomNumbers
                .skip(10000000L)
                .distinct()
                .limit(10000000L)
                .reduce(Double.MIN_VALUE, Double::max);
    }

    /**
     * Calculates the sum of all integer numbers from 1 to 100, as done
     * by Gauss in the late 1700's.
     *
     * @return Value of the sum
     */
    private static int gaussianSum() {
        IntStream sequentialStream = IntStream.iterate(1, r -> r + 1);

        return sequentialStream
                .limit(100)
                .reduce(0, (a, b) -> a + b);
    }
}
