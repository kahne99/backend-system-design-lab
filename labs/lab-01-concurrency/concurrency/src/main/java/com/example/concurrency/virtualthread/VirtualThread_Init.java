package com.example.concurrency.virtualthread;

import java.util.concurrent.Executors;

/*
* Virtual Thread is a type of Java thread, managed by the JVM,
* rather than being mapped 1:1 to an OS thread like platform thread.
*
* Multiple Virtual thread can share Platform thread.
* Suitable for I/O-bound, waiting task.
*
* Virtual thread not run directly on the CPU. They are scheduled and executed by the JVM via the Platform thread,
* commonly refer to as a `Carrier Thread`.
* */
public class VirtualThread_Init {
    private static final int NUMBER_OF_TASKS = 10;

    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();

        // Create a thread for each task submitted.
        var executor = Executors.newVirtualThreadPerTaskExecutor();

        // threads are processed at the same time
        for (int i = 1; i <= NUMBER_OF_TASKS; i++) {
            int taskId = i;

            executor.submit(() -> {
                Thread currentThread = Thread.currentThread();

                System.out.printf(
                        "[START] Task-%d | Thread: %s | Virtual: %s%n",
                        taskId,
                        currentThread,
                        currentThread.isVirtual()
                );

                try {
                    // Simulate the I/O process
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    return;
                }

                System.out.printf("[END] Task-%d%n", taskId);
            });
        }

        executor.close();
        System.out.println(
                "Total time: " +
                        (System.currentTimeMillis() - startTime) + " ms"
        );
    }
}

/*
* [START] Task-1 | Thread: VirtualThread[#21]/runnable@ForkJoinPool-1-worker-1 | Virtual: true
[START] Task-3 | Thread: VirtualThread[#24]/runnable@ForkJoinPool-1-worker-7 | Virtual: true
[START] Task-2 | Thread: VirtualThread[#23]/runnable@ForkJoinPool-1-worker-5 | Virtual: true
[START] Task-4 | Thread: VirtualThread[#25]/runnable@ForkJoinPool-1-worker-2 | Virtual: true
[START] Task-5 | Thread: VirtualThread[#26]/runnable@ForkJoinPool-1-worker-3 | Virtual: true
[START] Task-7 | Thread: VirtualThread[#28]/runnable@ForkJoinPool-1-worker-7 | Virtual: true
[START] Task-8 | Thread: VirtualThread[#29]/runnable@ForkJoinPool-1-worker-7 | Virtual: true
[START] Task-10 | Thread: VirtualThread[#31]/runnable@ForkJoinPool-1-worker-4 | Virtual: true
[START] Task-6 | Thread: VirtualThread[#27]/runnable@ForkJoinPool-1-worker-7 | Virtual: true
[START] Task-9 | Thread: VirtualThread[#30]/runnable@ForkJoinPool-1-worker-1 | Virtual: true
[END] Task-1
[END] Task-2
[END] Task-7
[END] Task-3
[END] Task-4
[END] Task-5
[END] Task-8
[END] Task-10
[END] Task-6
[END] Task-9
Total time: 3109 ms
* */