package ru.job4j.thread;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;

public class Wget implements Runnable {
    private final String url;
    private final int speed;

    public Wget(String url, int speed) {
        this.url = url;
        this.speed = speed;
    }

    @Override
    public void run() {
        try (BufferedInputStream in = new BufferedInputStream(new URL(url).openStream());
             FileOutputStream fileOutputStream = new FileOutputStream("pom_tmp.xml")) {
            byte[] dataBuffer = new byte[1024];
            int bytesRead;
            long before = System.currentTimeMillis();
            while ((bytesRead = in.read(dataBuffer, 0, 1024)) != -1) {
                fileOutputStream.write(dataBuffer, 0, bytesRead);
                long after = System.currentTimeMillis();
                long time = after - before;
                if (time < speed) {
                    try {
                        Thread.sleep(speed - time);
                        before = after;
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void validate(String[] args) {
        if (args.length < 2) {
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
    }

    public static void main(String[] args) throws InterruptedException {
        validate(args);
        String url = args[0];
        int speed = Integer.parseInt(args[1]);
        Thread wget = new Thread(new Wget(url, speed));
        wget.start();
        wget.join();
    }
}