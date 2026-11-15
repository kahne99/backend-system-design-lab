package com.example.concurrency.simulate.back_pressure.admission_control;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executors;

public class Bounded_BlockingQueue {
    private static int PRODUCERS = 10;
    private static int CONSUMERS = 2;

    public static void main(String[] args) throws Exception {

        BlockingQueue<Integer> queue = new ArrayBlockingQueue<>(3);

        try (var executor = Executors.newVirtualThreadPerTaskExecutor()) {

            // Consumers
            for (int i = 1; i <= CONSUMERS; i++) {
                int consumerId = i;

                executor.submit(() -> {
                    try {
                        while (true) {
                            int task = queue.take();

                            System.out.println( "Consumer " + consumerId + " processing task " + task);

                            Thread.sleep(2000);
                        }
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                });
            }

            // Producer
            for (int i = 1; i <= PRODUCERS; i++) {
                System.out.println("Producing: " + i);

                // wait until slot become available
//                queue.put(i);

                // return `false` when there is no space left.
                boolean accepted = queue.offer(i);
                if (!accepted) {
                    System.out.println("Queue full - Reject task");
                }

                System.out.println("Enqueued: " + i);
            }
        }
    }
}
