package com.example.concurrency.executor.fundamentials;

import java.util.concurrent.*;

/*
* `Future` is an interface that represents the result of a task executed asynchronously.
* */
public class Future_Fundamentals {

    public static void main(String[] args) throws Exception {
        ExecutorService executor = Executors.newFixedThreadPool(1);

        Future<String> future = executor.submit(() -> {
            System.out.println("Task is running ...");
            Thread.sleep(3000);
            return "Data returned from `submit()` 1";
        });

        // If task is not yet complete, the thread will be blocked until the result is available.
        String dataFromFuture = future.get();
        System.out.println("future.get() :: "+ dataFromFuture);


        Future<String> future2 = executor.submit(() -> {
            System.out.println("Task is running ...");
            Thread.sleep(3000);
            return "Data returned from `submit()` 2";
        });


        // Simply wait until the time limit expires; if it is exceeded, throw a `TimeoutException`.
        String dataFromFuture2 = future2.get(1, TimeUnit.SECONDS);
        System.out.println("future.get(time, TimeUnit) :: "+ dataFromFuture2);


        Future<String> future3 = executor.submit(() -> {
            try {
                while (true) {
                    System.out.println("Task running...");
                    Thread.sleep(500);
                }
            } catch (InterruptedException e) {
                System.out.println("Task interrupted!");
                Thread.currentThread().interrupt();
                return "Cancelled";
            }
        });

        Thread.sleep(2000);

        // cancel processing task
        future3.cancel(true);

        boolean isCanceled = future3.isCancelled();
        System.out.println("Is canceled :: " + isCanceled);

        boolean isDone = future3.isDone();
        System.out.println("Is done :: " + isDone);

        Future.State state = future3.state();
        System.out.println("State :: " + state);

        executor.close();
    }

}


