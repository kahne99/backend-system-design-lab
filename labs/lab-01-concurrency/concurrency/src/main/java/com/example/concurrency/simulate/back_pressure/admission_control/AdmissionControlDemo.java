package com.example.concurrency.simulate.back_pressure.admission_control;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

public class AdmissionControlDemo {

    private static final Semaphore capacity = new Semaphore(2);

    public static void main(String[] args) {

        try (ExecutorService executor =
                     Executors.newVirtualThreadPerTaskExecutor()) {

            for (int i = 1; i <= 10; i++) {
                submitTask(executor, i);
            }
        }
    }

    private static void submitTask(ExecutorService executor, int taskId) {

        // admission control
        if (!capacity.tryAcquire()) {
            System.out.println("Task :: " + taskId + "REJECTED.");
            return;
        }

        executor.submit(() -> {

            try {
                System.out.println("Task :: " + taskId + "STARTED.");

                Thread.sleep(1000);

                System.out.println("Task :: " + taskId + "COMPLETED.");

            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.out.println("Task :: " + taskId + "INTERRUPTED.");

            } finally {
                // Release capacity
                capacity.release();
            }

        });

    }
}
