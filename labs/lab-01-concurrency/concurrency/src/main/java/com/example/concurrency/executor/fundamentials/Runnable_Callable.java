package com.example.concurrency.executor.fundamentials;

import java.util.concurrent.Callable;

/*
 * Runnable & Callable
 * Both represent for a task
 * */
public class Runnable_Callable {
    public static void main(String[] args) {
        Runnable runnable = () -> {
            System.out.println("Runnable -- Task is running ...");
        };

        // Runnable: just execute task, not return data
        runnable.run();

        Callable<String> callable = () -> {
            System.out.println("Callable -- Task is running ...");
            return "Data returned from Callable";
        };

        try {
            // Callable: can return data
            String callableReturned = callable.call();
            System.out.println(callableReturned);
        } catch (Exception e) {

            // Callable: can throw Checked exception.
            throw new RuntimeException(e);
        }
    }
}
