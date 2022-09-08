package ru.job4j.io;

import java.io.*;
import java.util.function.Predicate;

public final class ParseInFile {

    private final File file;

    public ParseInFile(File file) {
        this.file = file;
    }

    private synchronized String getContent(Predicate<Character> filter) {
        StringBuilder output = new StringBuilder();
        try (BufferedInputStream i = new BufferedInputStream(new FileInputStream(file))) {
            int data;
            while ((data = i.read()) != -1) {
                if (filter.test((char) data)) {
                    output.append((char) data);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return output.toString();
    }

    public synchronized String getCharEqualsA() {
        return getContent(e -> e == 'A');
    }

    public synchronized String getCharEqualsB() {
        return getContent(e -> e == 'B');
    }
}