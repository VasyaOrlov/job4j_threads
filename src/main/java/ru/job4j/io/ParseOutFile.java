package ru.job4j.io;

import java.io.*;

public final class ParseOutFile {

    private final File file;

    public ParseOutFile(File file) {
        this.file = file;
    }

    public void saveContent(String content) {
        try (BufferedOutputStream o = new BufferedOutputStream(new FileOutputStream(file))) {
            for (int i = 0; i < content.length(); i += 1) {
                o.write(content.charAt(i));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
