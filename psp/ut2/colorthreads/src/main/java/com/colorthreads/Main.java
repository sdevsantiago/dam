package com.colorthreads;

public class Main {
    public static void main(String[] args) {
        Colors colors[];

        try {
            colors = Colors.createColors(args);
        }
        catch (IllegalArgumentException e) {
            // exit early if colors can't be created
            e.printStackTrace();
            System.exit(1);
            return;
        }

        Thread threads[] = new Thread[colors.length];

        // create all threads, one for each color
        for (int i = 0; i < threads.length; i++) {
            threads[i] = new Thread(colors[i], colors[i].getColorName());
        }

        // run all threads
        for (Thread thread : threads) {
            thread.start();
        }

        // wait for all threads to stop
        boolean successfulExecution = true;
        try {
            for (Thread thread : threads) {
                thread.join();
            }
        } catch (InterruptedException e) {
            successfulExecution = false;
            e.printStackTrace();
        } finally {
            System.out.println(successfulExecution
                ? "Execution ended successfully"
                : "Errors have occurred");
        }
    }
}
