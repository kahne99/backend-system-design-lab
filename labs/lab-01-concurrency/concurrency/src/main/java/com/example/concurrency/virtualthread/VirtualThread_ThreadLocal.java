package com.example.concurrency.virtualthread;

import java.util.concurrent.Executors;

/*
* Typically, thread can access a share variable declared at a class level.
* If multiple threads modify the same variable, the data may be overwritten.
*
* `Thread local` addresses this problem by creating separate a copy of the data for each thread.
* */
public class VirtualThread_ThreadLocal {
    private static final ThreadLocal<Integer> COUNTER = ThreadLocal.withInitial(() -> 0);

    public static void main(String[] args) throws Exception {

        try (var executor = Executors.newVirtualThreadPerTaskExecutor()) {

            for (int i = 0; i < 3; i++) {
                executor.submit(() -> {
                    COUNTER.set(COUNTER.get() + 1);

                    System.out.println(Thread.currentThread() + " -> " + COUNTER.get());
                });
            }
        }

    }
}

/*
* Each thread has its own value.
* Therefore, should be carefully considered when use of thread-local storage with virtual threads.
* Because the number of stored thread-local values will correspond to the number of such virtual threads.
*
* Consider using `ScopedValue` instead of `ThreadLocal` if the data has read-only and has a well-defined execution scope.
* * */

/*
VirtualThread[#23]/runnable@ForkJoinPool-1-worker-2 -> 1
VirtualThread[#24]/runnable@ForkJoinPool-1-worker-3 -> 1
VirtualThread[#21]/runnable@ForkJoinPool-1-worker-1 -> 1
* */