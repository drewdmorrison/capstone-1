package com.techelevator;

import java.io.*;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class SalesReport {
    private File file;
    //private File timeStampedFile;
    private BigDecimal sessionSaleAmount = new BigDecimal("0.00");

    public SalesReport(String nameOfFile) {
        this.file = new File(nameOfFile);
    }


    // Creates a sales report for the current session and logs what was sold and the total amount earned.
    public void generateReport(List<Item> itemList) {
        PrintWriter writer = null;
        if (this.file.exists()) {
            try {
                FileOutputStream fileOutputStream = new FileOutputStream(file, false);
                writer = new PrintWriter(fileOutputStream);
                for (Item item : itemList) {
                    writer.println(item.getName() + "|" + item.getSaleCounter());
                    if (item.getSaleCounter() > 0) {
                        setSessionSaleAmount(item.getPrice().multiply(BigDecimal.valueOf(item.getSaleCounter())));
                    }
                }

               writer.println("Total Sales: $" + getSessionSaleAmount());
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        } else {
            try {
                writer = new PrintWriter(this.file);
                for (Item item : itemList) {
                    writer.println(item.getName() + "|" + item.getSaleCounter());
                    if (item.getSaleCounter() > 0) {
                        setSessionSaleAmount(item.getPrice().multiply(BigDecimal.valueOf(item.getSaleCounter())));
                    }
                }

                writer.println("Total Sales: $" + getSessionSaleAmount());
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

        writer.flush();
        writer.close();
    }

    public BigDecimal getSessionSaleAmount() {
        return sessionSaleAmount;
    }

    public void setSessionSaleAmount(BigDecimal amount) {
        this.sessionSaleAmount = sessionSaleAmount.add(amount);
    }


    // Trying to create a sales report that has the timestamp as the name of the file, so it creates a new report each time the app
    // is ran. Keeps making multiple files per session that logs each item purchase separately.
    public void timeStampedSalesReport(String oldFileName, String timeStampedFileName) {
        // take salesreport.txt
        // make a timestamped copy
        //String fileName = new SimpleDateFormat("yyyy-MM-dd hh-mm-ss'.txt'").format(new Date());
        File timeStampedFile = new File(timeStampedFileName);
        File oldFile = new File(oldFileName);
        PrintWriter writer = null;

        try {

            // Scan oldFile --> salesreport.txt
            Scanner scanner = new Scanner(oldFile);
           // while salesreport has another line
            while(scanner.hasNextLine()) {
                // see if timestamped file exists
                if (timeStampedFile.exists()) {
                    try {
                        // get the current line from sales report
                        String line = scanner.nextLine();
                        FileWriter fileWriter = new FileWriter(timeStampedFile);
                        writer = new PrintWriter(fileWriter);
                        writer.println(line);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    try {
                        writer = new PrintWriter(timeStampedFile);
                        String line = scanner.nextLine();
                        writer.println(line);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        writer.flush();
        writer.close();
    }
}
