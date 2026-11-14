package com.example.concurrency.virtualthread;

import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/*
 * Action to stop a task, without continuing to wait for it.
 * */
public class VirtualThread_Cancellation {

    public static void main(String[] args) throws Exception {
        try (var executer = Executors.newVirtualThreadPerTaskExecutor()) {

            Future<?> future = executer.submit(() -> {

                try {
                    while (true) {
                        System.out.println("Thread is running ....");

                        Thread.sleep(100);
                    }

                } catch (InterruptedException ex) {
                    System.out.println("Task canceled.");
                    Thread.currentThread().interrupt();
                }

            });

            Thread.sleep(200);

            // Interrupt running thread.
            future.cancel(true);

            // The task will not be interrupted and will continue to execute normally.
//            future.cancel(false);

            System.out.println("Task is canceled :: " + future.isCancelled());


        }
    }
}
