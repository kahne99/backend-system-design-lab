package com.example.concurrency.platformthread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class PlatformThread_Blocking {
    private static int NUMBER_OF_THREADS = 1;
    private static int NUMBER_OF_TASKS = 10;
    private static int BLOCKING_TIME = 3000;

    public static void main(String[] args) {
        ExecutorService executor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

        System.out.println("START...");
        long startTime = System.currentTimeMillis();

        for (int i = 1; i <= NUMBER_OF_TASKS; i++) {
            int taskId = i;

            String threadName = Thread.currentThread().getName();
            long taskStart = System.currentTimeMillis();

            System.out.printf(
                    "[START] Task-%d | Thread: %s | Time: %d%n",
                    taskId,
                    threadName,
                    taskStart - startTime
            );

            executor.submit(() -> {
                try {
                    // Simulate blocking I/O (Call API, query DB, read file,...)
                    Thread.sleep(BLOCKING_TIME);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

                System.out.println(
                        Thread.currentThread().getName()
                                + " - State: "
                                + Thread.currentThread().getState()
                );

                long taskEnd = System.currentTimeMillis();
                System.out.printf(
                        "[END]   Task-%d | Thread: %s | Time: %d%n",
                        taskId,
                        threadName,
                        taskEnd - startTime
                );
            });
        }

        executor.close();
        System.out.println("END.");
    }
}

/* 1 thread/ 10 task
=> A subsequence task must wait in a `Working Queue` until the preceding task has been successfully completed
before it can be processed.
While in a sleep state, platform thread cannot accept other tasks.

START...
        [START] Task-1 | Thread: main | Time: 0
        [START] Task-2 | Thread: main | Time: 28
        [START] Task-3 | Thread: main | Time: 29
        [START] Task-4 | Thread: main | Time: 29
        [START] Task-5 | Thread: main | Time: 29
        [START] Task-6 | Thread: main | Time: 29
        [START] Task-7 | Thread: main | Time: 29
        [START] Task-8 | Thread: main | Time: 29
        [START] Task-9 | Thread: main | Time: 30
        [START] Task-10 | Thread: main | Time: 30
        [END]   Task-1 | Thread: main | Time: 3031
        [END]   Task-2 | Thread: main | Time: 6044
        [END]   Task-3 | Thread: main | Time: 9053
        [END]   Task-4 | Thread: main | Time: 12066
        [END]   Task-5 | Thread: main | Time: 15080
        [END]   Task-6 | Thread: main | Time: 18085
        [END]   Task-7 | Thread: main | Time: 21100
        [END]   Task-8 | Thread: main | Time: 24101
        [END]   Task-9 | Thread: main | Time: 27106
        [END]   Task-10 | Thread: main | Time: 30121
END.
*/
