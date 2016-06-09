package com.brunotoffolo.codewithme.exceptions.model;

import com.brunotoffolo.codewithme.exceptions.exception.InsufficientFundsException;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * Represents a Credit Card. To keep the example short and simple, we only store basic
 * information needed for our desired operations. It could be much more complex in a
 * more detailed example or use case.
 *
 * @author Bruno Toffolo
 */
public class CreditCard {

    private final long number;
    private final String brand;
    private double limit;
    private double balance;
    private final Calendar expirationDate;
    private int pin;
    private List<Purchase> purchases;

    public CreditCard(int pin, Calendar expirationDate, double limit, String brand, long number) {
        if (expirationDate.before(new GregorianCalendar())) {
            throw new IllegalArgumentException("Credit card expiration date should not be in the past");
        }

        this.balance = 0.0;
        this.brand = brand;
        this.expirationDate = expirationDate;
        this.number = number;
        setLimit(limit);
        setPin(pin);
        this.purchases = new ArrayList<>();
    }

    /**
     * Gets the card number.
     *
     * @return Card number
     */
    public long getNumber() {
        return number;
    }

    /**
     * Gets the card limit.
     *
     * @return Card limit
     */
    public double getLimit() {
        return limit;
    }

    /**
     * Sets the card limit.
     *
     * @param limit Limit to be set
     */
    public void setLimit(double limit) {
        if (limit < 0) {
            throw new IllegalArgumentException("Credit card limit should be a positive value");
        }

        this.limit = limit;
    }

    /**
     * Gets the card balance.
     *
     * @return Card balance
     */
    public double getBalance() {
        return balance;
    }

    /**
     * Gets the card PIN code.
     *
     * @return PIN code
     */
    public int getPin() {
        return pin;
    }

    /**
     * Sets the card PIN code.
     *
     * @param pin Code to be set
     */
    public void setPin(int pin) {
        if (pin < 100000 || pin > 999999) {
            throw new IllegalArgumentException("PIN code must have exactly six digits");
        }

        this.pin = pin;
    }

    /**
     * Gets the card brand.
     *
     * @return Card brand
     */
    public String getBrand() {
        return brand;
    }

    /**
     * Gets the card expiration date.
     *
     * @return Expiration date
     */
    public Calendar getExpirationDate() {
        return expirationDate;
    }

    /**
     * Adds a new purchase in the credit card invoice.
     *
     * @param amount Amount of the purchase.
     * @param description Description of the purchase.
     * @returns Partial balance including the added purchase
     */
    public double addPurchase(double amount, String description) throws InsufficientFundsException {
        if (balance + amount > limit) {
            throw new InsufficientFundsException("Purchase amount is higher than the available limit");
        }

        balance += amount;

        Purchase purchase = new Purchase(amount, description);
        purchases.add(purchase);

        System.out.println("CC " + number + " | New purchase: USD " + amount +
                " | Current balance: USD " + balance);

        return balance;
    }

    /**
     * Generates a simple invoice file for the credit card and saves it into a file. The
     * invoice is a plain text file containing information of the card, the purchases and
     * total balance at the present time.
     *
     * @param filename Name of the file in which the invoice should be stored.
     */
    public void createInvoice(String filename) {
        File invoiceFile = new File(filename);
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat dateTimeFormat = new SimpleDateFormat("dd/MM/yyyy, HH:MM");
        String currentTime = dateTimeFormat.format(Calendar.getInstance().getTime());

        BufferedWriter bufferedWriter = null;
        try {
            bufferedWriter = new BufferedWriter(new FileWriter(invoiceFile));
            bufferedWriter.write("INVOICE FOR: " + brand + " " + number + "\r\n");
            bufferedWriter.write("--------------------------------------------\r\n");
            bufferedWriter.write("PURCHASES\r\n");
            bufferedWriter.write("Date\t\tAmount\tDescription\r\n");
            for (Purchase purchase : purchases) {
                bufferedWriter.write(
                        dateFormat.format(purchase.getDate().getTime()) + "\t" +
                        purchase.getAmount() + "\t" +
                        purchase.getDescription() + "\r\n");
            }
            bufferedWriter.write("--------------------------------------------\r\n");
            bufferedWriter.write("TOTAL AMOUNT: USD " + balance + "\n");
            bufferedWriter.write("Remaining limit: USD " + (limit - balance) + "\n");
            bufferedWriter.write("--------------------------------------------\r\n");
            bufferedWriter.write("Invoice generated at " + currentTime);

            System.out.println("CC " + number + " | Invoice generated in " + filename);
        } catch (IOException e) {
            System.err.println("Error while exporting credit card invoice: " + e.getMessage());
        } finally {
            // In the previous commit, we used two different 'catch' blocks to do exactly the
            // same thing: log a message informing that an exception happening when closing the
            // BufferedWriter resource. We implemented two different catch blocks because each
            // of them would handle a specific type of exception.

            // In such cases where the same handling operations should be performed for more than
            // one type of exception, Java 7 has implemented a mechanism that makes it easier for
            // developers to do that. It is now possible to pipe different exception types in a
            // single block by using the pipe '|' operator when declaring the catch arguments.

            // An example can be seen below. We used a single catch block to handle both the
            // IOException and the NullPointerException.

            try {
                bufferedWriter.close();
            } catch (IOException | NullPointerException e) {
                System.err.println("Error while closing invoice BufferedWriter: " + e.getMessage());
            }
        }
    }

}
