package com.techelevator;

import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

public class Purchasing {

    private BigDecimal balance = new BigDecimal("0.0");

    public BigDecimal feedMoney() {
        BigDecimal amount = new BigDecimal("0.00");
        Scanner scanner = new Scanner(System.in);
        boolean keepFeeding = true;

        while (keepFeeding) {
            try {
                System.out.print("\nPlease feed money (1.00, 2.00, 5.00, 10.00): ");
                String stringAmount = scanner.nextLine();
                amount = new BigDecimal(stringAmount);

                List<BigDecimal> acceptedBills = new ArrayList<>();
                acceptedBills.add(new BigDecimal("1.00"));
                acceptedBills.add(new BigDecimal("2.00"));
                acceptedBills.add(new BigDecimal("5.00"));
                acceptedBills.add(new BigDecimal("10.00"));

                if (acceptedBills.contains(amount)) {
                    addMoney(amount);
                    LogWriter writer = new LogWriter("log.txt");
                    writer.writeToFile("FEED MONEY: $");
                } else {
                    System.out.println("\nInvalid amount, returning to purchasing menu");
                    break;
                }

                System.out.print("\nWould you like to feed more money? (Y/N): ");
                String addMore = scanner.nextLine().toLowerCase();
                if (addMore.equals("n")) {
                    keepFeeding = false;
                } else if (!addMore.equals("y")) {
                    System.out.println("\nInvalid choice, returning to purchasing menu.");
                    break;
                }
            } catch (NumberFormatException e) {
                System.out.println("\nInvalid entry, returning back to purchasing menu.");
                break;
            }
        }
        return amount;
    }

    public String getChange(BigDecimal balance) {
        double amountInPennies = balance.doubleValue() * 100;
        int quarter;
        int dime;
        int nickel;

        quarter = (int) amountInPennies / 25;
        amountInPennies %= 25;
        dime = (int) amountInPennies / 10;
        amountInPennies %= 10;
        nickel = (int) amountInPennies / 5;

        this.setBalance(new BigDecimal("0.00"));
        LogWriter writer = new LogWriter("log.txt");
        writer.writeToFile("GIVE CHANGE: $" + balance + " $" + getBalance());

        return "Quarters: " + quarter + "\nDimes: " + dime + "\nNickels: " + nickel;

    }

    public void addMoney(BigDecimal amount) {
        this.balance = this.balance.add(amount);
    }

    public void removeMoney(BigDecimal amount) {
        this.balance = this.balance.subtract(amount);
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }
}
