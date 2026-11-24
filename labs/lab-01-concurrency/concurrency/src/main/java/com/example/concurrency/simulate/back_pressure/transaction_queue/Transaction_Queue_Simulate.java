package com.example.concurrency.simulate.back_pressure.transaction_queue;

import java.util.concurrent.*;

/*
* In this example, the Semaphore controls the number of tasks that can access the database concurrently.
* Although there are two consumers, the Semaphore is initialized with only one permit.
* Therefore, only one task can access the database at a time.
* */
public class Transaction_Queue_Simulate {
    private static final BlockingQueue<Integer> queue = new ArrayBlockingQueue<>(10);
    private static final Semaphore semaphore = new Semaphore(1);
    private static final int POISON_PILL = -1;
    private static final int TOTAL_TRANSACTIONS = 30;

    public static void main(String[] args) {
        ExecutorService executor = Executors.newFixedThreadPool(3);

        try {
            // Consumers
            Future<?> consumer1 = executor.submit(() -> {
                while (true) {
                    try {
                        Integer data = queue.take();
                        if (data == POISON_PILL) {
                            System.out.println("Consumer 1 stopped.");
                            break;
                        }

                        System.out.println("Data take from consumer 1 :: " + data);
                        semaphore.acquire();

                        // Simulate business logic outside database access
                        Thread.sleep(50);


                        // Simulate database write: 100ms
                        Thread.sleep(100);

                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        throw new RuntimeException(e);
                    } finally {
                        semaphore.release();
                    }
                }
            });

            Future<?> consumer2 = executor.submit(() -> {
                while (true) {
                    try {
                        Integer data = queue.take();
                        if (data == POISON_PILL) {
                            System.out.println("Consumer 2 stopped.");
                            break;
                        }

                        System.out.println("Data take from consumer 2:: " + data);
                        semaphore.acquire();

                        // Simulate business logic outside database access
                        Thread.sleep(50);


                        // Simulate database write: 100ms
                        Thread.sleep(100);

                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        throw new RuntimeException(e);
                    } finally {
                        semaphore.release();
                    }
                }
            });



            // Producer
            Future<?> producer = executor.submit(() -> {
                try {
                    for (int i = 1; i <= TOTAL_TRANSACTIONS; i++) {

                        // Block if Queue is full
                        queue.put(i);

                        // 100 transactions/second
                        Thread.sleep(10);
                    }

                    System.out.println("[Producer] All transactions submitted.");

                    // Send stop signals to 2 consumers
                    for (int i = 0; i < 2; i++) {
                        queue.put(POISON_PILL);
                    }

                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    throw new RuntimeException(e);
                }
            });

            // Wait for producer to finish
            producer.get();
            consumer1.get();
            consumer2.get();

        } catch (InterruptedException | ExecutionException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException(e);
        } finally {
            executor.shutdown();
        }
    }
}
