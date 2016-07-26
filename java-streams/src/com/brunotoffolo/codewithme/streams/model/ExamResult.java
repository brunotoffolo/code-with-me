package com.brunotoffolo.codewithme.streams.model;

/**
 * Simple model class to represent an exam result.
 * Contains only the basic information needed to evaluate the
 * results using streams and lambda expressions on the code
 * examples.
 *
 * @author Bruno Toffolo
 */
public class ExamResult implements Comparable {

    /** The student ID */
    private int id;

    /** The grade obtained in the exam */
    private double grade;

    /**
     * Creates a new exam result.
     * @param id Student ID
     * @param grade Obtained grade
     */
    public ExamResult(int id, double grade) {
        this.id = id;
        this.grade = grade;
    }

    /**
     * Gets the student ID
     * @return Student ID
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the student ID
     * @param id Student ID
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Gets the student grade
     * @return Student grade
     */
    public double getGrade() {
        return grade;
    }

    /**
     * Sets the student grade
     * @param grade Student grade
     */
    public void setGrade(double grade) {
        this.grade = grade;
    }

    /**
     * Compares this result to another one. Generates the result needed
     * to create a list of exam results in <b>reversed</b> order, for
     * simplicity in this example.
     * @param o ExamResult object to compare to
     * @return -1 if current grade is bigger; 1 if smaller; 0 if equal
     */
    @Override
    public int compareTo(Object o) {
        if (o instanceof ExamResult) {
            return (getGrade() > ((ExamResult) o).getGrade()) ? -1 : 1;
        }
        return 0;
    }
}
