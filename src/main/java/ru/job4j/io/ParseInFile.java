package ru.job4j.io;

import java.io.*;
import java.util.function.Predicate;

public final class ParseInFile {

    private final File file;

    public ParseInFile(File file) {
        this.file = file;
    }

    public String getContent(Predicate<Character> filter) {
        String output = "";
        try (BufferedInputStream i = new BufferedInputStream(new FileInputStream(file))) {
            int data;
            while ((data = i.read()) > 0) {
                if (filter.test((char) data)) {
                    output += (char) data;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return output;
    }
}