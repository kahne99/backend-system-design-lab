package com.example.concurrency.executor.fundamentials;

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
        execute_submit();
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
}
