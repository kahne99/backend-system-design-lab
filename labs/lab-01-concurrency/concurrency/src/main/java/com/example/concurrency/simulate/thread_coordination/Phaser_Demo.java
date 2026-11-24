package com.example.concurrency.simulate.thread_coordination;

import java.util.concurrent.Phaser;

/*
* A Phaser is a synchronization mechanism similar to a CyclicBarrier, but more flexible and powerful.
* It allows multiple threads to synchronize across various phases,
* while also supporting the registration and deregistration of threads during execution.
 */

public class Phaser_Demo {
    private static int NUMBER_THREADS = 3;

    public static void main(String[] args) {
        Phaser phaser = new Phaser(NUMBER_THREADS);

        Runnable workerA = () -> {
            try {
                // Phase 0
                System.out.println("A: Processing balance...");
                Thread.sleep(1000);

                phaser.arriveAndAwaitAdvance();

                // Phase 1
                System.out.println("A: Aggregating data...");
                Thread.sleep(1000);

                phaser.arriveAndAwaitAdvance();

                // Phase 2
                System.out.println("A: Updating database...");

                phaser.arriveAndDeregister();

            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                phaser.forceTermination();
            }
        };

        Runnable workerB = () -> {
            try {
                // Phase 0
                System.out.println("B: Processing transactions...");
                Thread.sleep(1500);

                phaser.arriveAndAwaitAdvance();

                // Phase 1
                System.out.println("B: Validating data...");
                Thread.sleep(800);

                phaser.arriveAndDeregister();  // <- deregister - complete phase 1 and leave.

            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                phaser.forceTermination();
            }
        };

        Runnable workerC = () -> {
            try {
                // Phase 0
                System.out.println("C: Processing margin...");
                Thread.sleep(2000);

                // Complete Phase 0 and leave
                phaser.arriveAndDeregister();

            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                phaser.forceTermination();
            }
        };

        new Thread(workerA).start();
        new Thread(workerB).start();
        new Thread(workerC).start();
    }
}

/*
A: Processing balance...
B: Processing transactions...
C: Processing margin...
B: Validating data...
A: Aggregating data...
A: Updating database...
* */