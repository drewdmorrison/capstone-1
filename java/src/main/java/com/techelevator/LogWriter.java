package com.techelevator;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

public class LogWriter {

    private File file;

    public LogWriter(String nameOfFile) {
        this.file = new File(nameOfFile);
    }

    public void writeToFile(String lineOfText) {
        PrintWriter writer = null;
        if (this.file.exists()) {
            try {
                FileOutputStream fileOutputStream = new FileOutputStream(file, true);
                writer = new PrintWriter((fileOutputStream));
                writer.println(getCurrentTime() + " " + lineOfText);

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        } else {
            try {
                writer = new PrintWriter(this.file);
                writer.println(getCurrentTime() + " " + lineOfText);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

        writer.flush();
        writer.close();
    }

    public String getCurrentTime() {
        String date = new SimpleDateFormat("MM/dd/yy hh:mm:ss a").format(new Date());
        return date;
    }
}
