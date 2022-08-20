package ru.job4j.thread;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;

public class Wget implements Runnable {

    private final static int SECOND = 1000;
    private final String url;
    private final int speed;
    private final String name;

    public Wget(String url, int speed, String name) {
        this.url = url;
        this.speed = speed;
        this.name = name;
    }

    @Override
    public void run() {
        try (BufferedInputStream in = new BufferedInputStream(new URL(url).openStream());
             FileOutputStream fileOutputStream = new FileOutputStream(name)) {
            byte[] dataBuffer = new byte[1024];
            int bytesRead;
            long before = System.currentTimeMillis();
            int downloadData = 0;
            while ((bytesRead = in.read(dataBuffer, 0, 1024)) != -1) {
                fileOutputStream.write(dataBuffer, 0, bytesRead);
                downloadData += dataBuffer.length;
                if (downloadData >= speed) {
                    long after = System.currentTimeMillis();
                    long time = after - before;
                    System.out.println(time);
                    if (time < SECOND) {
                        try {
                            Thread.sleep(SECOND - time);
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                        }
                    }
                    before = System.currentTimeMillis();
                    downloadData = 0;
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void validate(String[] args) {
        if (args.length < 3) {
            throw new IllegalArgumentException("Отсутствуют параметры запуска");
        }
        try {
            (new URL(args[0])).openStream().close();
        } catch (Exception e) {
            throw new IllegalArgumentException("Недействительный URL");
        }
        if (Integer.parseInt(args[1]) <= 0) {
            throw new IllegalArgumentException("Задайте значение скорости больше 0");
        }
        if (args[2].length() < 3 || !args[2].contains(".")) {
            throw new IllegalArgumentException("Недопустимое имя файла");
        }
    }

    public static void main(String[] args) throws InterruptedException {
        validate(args);
        String url = args[0];
        int speed = Integer.parseInt(args[1]);
        String name = args[2];
        Thread wget = new Thread(new Wget(url, speed, name));
        wget.start();
        wget.join();
    }
}