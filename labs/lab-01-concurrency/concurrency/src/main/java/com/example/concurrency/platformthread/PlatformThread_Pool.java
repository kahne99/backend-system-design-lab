package com.example.concurrency.platformthread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class PlatformThread_Pool {
    private static int NUMBER_OF_THREADS = 2;
    private static int NUMBER_OF_TASKS = 10;

    public static void main(String[] args) {
        System.out.println("START...");
        ExecutorService executor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

        // Unprocessed tasks will be stored in the Thread Pool's `Work Queue`.
        for (int i = 1; i <= NUMBER_OF_TASKS; i++ ) {
            int taskId = i;

            executor.submit(() -> {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                System.out.println("Thread name :: " + Thread.currentThread().getName() + ". Task :: " + taskId);
            });
        }

        // It will be printed before the list of tasks.
        System.out.println("The line of code is placed after the list of task.");

        executor.close();
        System.out.println("END.");
    }
}
