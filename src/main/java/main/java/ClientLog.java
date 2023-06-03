package main.java;

import au.com.bytecode.opencsv.CSVWriter;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class ClientLog {
    public String log = "";

    public void log(int productNum, int amount) {
        log += String.format("%d,%d%n", productNum, amount);
    }

    public void exportAsCSV(File csvFile) throws IOException {
        if (!csvFile.exists())
            log = "productNum,amount\n" + log;
        try (FileWriter writer = new FileWriter(csvFile, true)) {
            writer.write(log);
            System.out.println("File exported");
        }
    }
}