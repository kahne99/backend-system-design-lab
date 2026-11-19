package com.example.concurrency.executor.fundamentials;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/*
 * Manages the lifecycle and submission of tasks. Responsibilities include:
 * + Manage tasks.
 * + Manage a Thread Pool.
 * + Return data via the `Future` class.
 * ...
 * */
public class ExecutorService_Fundamentals {

    public static void main(String[] args) throws Exception {
//        execute_submit();
//        invokeAll_invokeAny();
        threadpool_executor();
    }

    public static void execute_submit() throws Exception {
        ExecutorService executor = Executors.newFixedThreadPool(2);

        // Execute directly, not return data
        executor.execute(() -> {
            System.out.println("Run `ExecutorService.executor()`");
        });

        // Execute directly, can return data
        Future<String> submit = executor.submit(() -> {
            System.out.println("Run `ExecutorService.submit()`");
            return "Return from `ExecutorService.submit()`";
        });

        System.out.println(submit.get());

        executor.close();
    }

    /*
     * With `invokeAll`:
     * Executes all submitted tasks and waits until all tasks complete.
     * Returns a list of `Future` objects in the same order as the submitted tasks.
     * If a task fails, its exception is captured in the corresponding `Future`.

     * With `invokeAny`:
     * Executes multiple submitted tasks concurrently.
     * Returns the result of the first task that completes successfully.
     * Cancels the remaining unfinished tasks once a successful result is obtained.
     * If a task fails, it continues waiting for other tasks to succeed.
     *
     * */
    public static void invokeAll_invokeAny() throws Exception {
        ExecutorService executor = Executors.newFixedThreadPool(1);

        List<Callable<String>> callables = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            int finalI = i;

            Callable<String> callable = () -> {
                System.out.println("Execute `Callable` -- " + finalI);

                // use when test `invokeAny`
                Thread.sleep(finalI == 1 ? 1000 : 5000);

                return "Data return from `Callable` -- " + finalI;
            };
            callables.add(callable);
        }

//        List<Future<String>> futures = executor.invokeAll(callables);

        // Other tasks are canceled, but tasks that are already in-progress may continue if they don't handle interruption properly.
        String firstSuccessTask = executor.invokeAny(callables);
        System.out.println("First success task :: " + firstSuccessTask);

        executor.close();
    }


    /*
     * ThreadPoolExecutor is used to manage worker threads and execute concurrent tasks.
     * * Core Pool Size: Number of basic worker threads maintained by the executor.
     * * Maximum Pool Size: Maximum number of worker threads that can be created.
     * * Keep Alive Time: Time that excess idle workers are kept alive.
     * * Work Queue: Stores tasks waiting for available workers.
     *
     * ThreadPoolExecutor task submission:
     * 1. If the number of workers < Core Pool Size: -> Create a new worker to execute the task.
     * 2. If Core Pool Size is reached:  -> Add the task to Work Queue.
     * 3. If Work Queue is full: -> Create additional workers up to Maximum Pool Size.
     * 4. If Maximum Pool Size is reached and Work Queue is full: -> Reject the new task based on Rejection Policy.
     *
     * Maximum concurrent task capacity: -> Maximum Pool Size + Work Queue Capacity (assuming no previously submitted tasks).
     */
    public static void threadpool_executor() throws Exception {

        ExecutorService executor = new ThreadPoolExecutor(
                2,                           // Core Pool Size
                10,                                      // Maximum Pool Size
                60,                                      // Keep Alive Time
                TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(9)    // Work Queue
        );

        try {
            // Submit 20 concurrent tasks
            for (int i = 0; i < 20; i++) {
                int taskId = i;

                executor.submit(() -> {
                    String threadName = Thread.currentThread().getName();

                    System.out.println("Task " + taskId + " started by " + threadName);

                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        return;
                    }

                    System.out.println("Task " + taskId + " completed by " + threadName);
                });
            }

        } finally {
            // Stop accepting new tasks
            executor.shutdown();

            // Wait for all submitted tasks to complete
            if (!executor.awaitTermination(30, TimeUnit.SECONDS)) {
                // Cancel remaining tasks if timeout
                executor.shutdownNow();
            }
        }
    }

}
