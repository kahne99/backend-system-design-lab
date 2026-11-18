package com.example.concurrency.executor.fundamentials;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

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
        invokeAll_invokeAny();
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
}
