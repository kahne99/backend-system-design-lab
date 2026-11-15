package com.example.concurrency.executor.fundamentials;

import java.util.concurrent.Executor;

/*
 * Executor is an interface that defines how a task is executed.
 * */
public class Executor_Interface {
    public static void main(String[] args) {
        // Direct Executor
        Executor directlyExecutor = command -> {
            System.out.println("Task started.");
            command.run();
            System.out.println("Task completed.");
        };

        directlyExecutor.execute(() -> {
            System.out.println("Task executing ...");
        });
    }
}
