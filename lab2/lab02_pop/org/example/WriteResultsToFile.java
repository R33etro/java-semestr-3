package org.example;

import java.io.*;
import java.util.ArrayList;

public class WriteResultsToFile {
    public static void writeResutlsToFile(ArrayList<Results> list_of_results, double w1, double w2, double result) {
        try (Writer writer = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream("filename.txt"), "utf-8"))) {
            writer.write("id, angle, height\n");
            writer.write(list_of_results.toString());
            writer.write("\n");
            writer.write("waga 1: " + w1 + ", waga 2: " + w2 + ", wynik: " + result);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
