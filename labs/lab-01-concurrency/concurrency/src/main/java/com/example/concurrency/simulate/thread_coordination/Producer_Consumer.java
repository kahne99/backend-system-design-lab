package com.example.concurrency.simulate.thread_coordination;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class Producer_Consumer {
    private static final BlockingQueue<Integer> queue = new ArrayBlockingQueue<>(10);
    private static final int POISON_PILL = -1;

    public static void main(String[] args) {

        ExecutorService executor = Executors.newFixedThreadPool(3);

        try {
            // Consumer
            Future<Integer> consumer = executor.submit(() -> {
                AtomicInteger completedTask = new AtomicInteger();
                try {
                    while (true) {
                        Integer data = queue.take();

                        if (data == POISON_PILL) {
                            System.out.println("Consumer stopped");
                            break;
                        }
                        completedTask.getAndIncrement();
                        System.out.println("Consumer processing :: " + data);
                        Thread.sleep(200);
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }

                return completedTask.get();
            });

            Future<?> producer1 = executor.submit(() -> {
                try {
                    for (int i = 1; i <= 10; i++) {
                        queue.put(i);
                        System.out.println("Producer-1 pushed :: " + i);
                        Thread.sleep(50);
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    throw new RuntimeException(e);
                }
            });

            Future<?> producer2 = executor.submit(() -> {
                try {
                    for (int i = 11; i <= 20; i++) {
                        queue.put(i);
                        System.out.println("Producer-2 pushed :: " + i);
                        Thread.sleep(500);
                    }
                } catch (InterruptedException ex) {
                    Thread.currentThread().interrupt();
                    throw new RuntimeException();
                }
            });

            // Wait for both producers
            producer1.get();
            producer2.get();

            queue.put(POISON_PILL);

            System.out.println("All producers completed");

            // Notify consumer to stop


            // Wait for consumer
            Integer completedTask =  consumer.get();
            System.out.println("Completed task :: " + completedTask);

        } catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            executor.shutdown();
            executor.close();
        }
    }
}
