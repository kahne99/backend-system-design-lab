package com.example.concurrency.simulate.multi_stage;

import java.util.concurrent.*;

/*
* Simulate a request processing system with three stages: Request, Validation, and Payment.
* - Request Stage: Produces requests and puts them into a BlockingQueue.
* - Validation Stage: Uses multiple consumers to validate requests concurrently and forwards validated data to the next queue.
* - Payment Stage: Uses a consumer to process validated requests sequentially.
* */
public class MultiStage_Demo {
    private static final BlockingQueue<Integer> validationQueue = new ArrayBlockingQueue<>(10);
    private static final BlockingQueue<Integer> paymentQueue = new ArrayBlockingQueue<>(10);

    private static final int NUMBER_VALIDATION_THREADS = 2;
    private static final int NUMBER_PAYMENT_THREADS = 1;

    private static final int POISON_PILL = -1;

    public static void main(String[] args) {
        ExecutorService requestExecutor = Executors.newFixedThreadPool(1);
        ExecutorService validationExecutor = Executors.newFixedThreadPool(NUMBER_VALIDATION_THREADS);
        ExecutorService paymentExecutor = Executors.newFixedThreadPool(NUMBER_PAYMENT_THREADS);

        // Stage 1: Request Producer
        Future<?> requestProducerFuture = requestExecutor.submit(() -> {
            try {
                for (int i = 1; i <= 20; i++) {
                    validationQueue.put(i);
                    System.out.println("[Request] - Put :: " + i);
                    Thread.sleep(1000);
                }

            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
                throw new RuntimeException(ex);
            }
        });


        // Stage 2: Validation Consumers
        for (int worker = 1; worker <= NUMBER_VALIDATION_THREADS; worker++) {
            validationExecutor.submit(() -> {
                try {
                    while (true) {
                        Integer requestData = validationQueue.take();

                        if (POISON_PILL == requestData) {
                            break;
                        }

                        System.out.println("[Validation] - Take :: " + requestData);

                        paymentQueue.put(requestData);
                        Thread.sleep(50);
                    }
                } catch (InterruptedException ex) {
                    Thread.currentThread().interrupt();
                    throw new RuntimeException(ex);
                }
            });
        }

        for (int worker = 1; worker <= NUMBER_PAYMENT_THREADS; worker++) {
            paymentExecutor.submit(() -> {
                try {
                    while (true) {
                        int validatedData = paymentQueue.take();

                        if (POISON_PILL == validatedData) {
                            break;
                        }

                        System.out.println("[Payment] - Take :: " + validatedData);
                        Thread.sleep(50);
                    }
                } catch (InterruptedException ex) {
                    Thread.currentThread().interrupt();
                    throw new RuntimeException(ex);
                }
            });
        }


        try {

            // 1. Wait for all Request Producers
            requestProducerFuture.get();
            requestExecutor.shutdown();

            // 2. Stop Validation Consumers
            for (int i = 0; i < NUMBER_VALIDATION_THREADS; i++) {
                validationQueue.put(POISON_PILL);
            }
            validationExecutor.shutdown();
            // Wait again for running tasks to stop
            if (!validationExecutor.awaitTermination(5, TimeUnit.SECONDS)) {
                System.out.println("ValidationExecutor did not terminate!");
            }

            // 3. Stop Payment Consumers
            for (int i = 0; i < NUMBER_PAYMENT_THREADS; i++) {
                paymentQueue.put(POISON_PILL);
            }
            paymentExecutor.shutdown();
            // Wait again for running tasks to stop
            if (!paymentExecutor.awaitTermination(5, TimeUnit.SECONDS)) {
                System.out.println("PaymentExecutor did not terminate!");
            }

            System.out.println("All stages completed!");

        } catch (InterruptedException ex) {
            requestExecutor.shutdownNow();
            validationExecutor.shutdownNow();
            paymentExecutor.shutdownNow();

            Thread.currentThread().interrupt();
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }
    }
}
