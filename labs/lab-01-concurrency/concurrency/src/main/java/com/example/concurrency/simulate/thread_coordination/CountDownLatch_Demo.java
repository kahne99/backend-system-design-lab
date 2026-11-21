package com.example.concurrency.simulate.thread_coordination;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/*
* `CountDownLatch` is a synchronization mechanism used until a set of tasks is completed.
* */
public class CountDownLatch_Demo {

    public static void main(String[] args) {
        ExecutorService executor = Executors.newFixedThreadPool(3);
        CountDownLatch latch = new CountDownLatch(3);

        /*
         * Simulated Scenario:
         * Service 4 can only start processing requests after the other three services have successfully started.
         * In other words, Service 4 must wait until all three services are ready before proceeding with its processing.
         * */
        try {

            executor.submit(() -> {
                try {
                    System.out.println("Service 1 initializing...");
                    Thread.sleep(2000);
                    System.out.println("Service 1 is Ready!");

                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                } finally {
                    latch.countDown();
                }
            });

            executor.submit(() -> {
                try {
                    System.out.println("Service 2 initializing...");
                    Thread.sleep(2000);
                    System.out.println("Service 2 is Ready!");

                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                } finally {
                    latch.countDown();
                }
            });

            executor.submit(() -> {
                try {
                    System.out.println("Service 3 initializing...");
                    Thread.sleep(2000);
                    System.out.println("Service 3 is Ready!");

                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                } finally {
                    latch.countDown();
                }
            });

            System.out.println("Main: Waiting for services...");

            latch.await();

            System.out.println("Main: All services are ready!");
            System.out.println("Application started.");

        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        finally {
            executor.shutdown();
            executor.close();
        }

    }
}
