package com.example.concurrency.virtualthread;

import com.example.concurrency.utils.CustomLogUtils;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/*
 * Thread pinning occurs when a Carrier Thread is occupied by a Virtual Thread and cannot be released through the usual unmounting process.
 * It can happen when a Virtual Thread performs blocking operations inside a synchronized block (Java 21–23) or calls a native method (JNI).
 * */
public class VirtualThread_Pinning {
    private static int BLOCKING_TIME = 3000;
    private static final Object LOCK = new Object();

    public static void main(String[] args) throws InterruptedException {
        CountDownLatch vt1Started = new CountDownLatch(1);
        long startTime = System.currentTimeMillis();

        System.setProperty("jdk.virtualThreadScheduler.parallelism", "1");
        System.setProperty("jdk.virtualThreadScheduler.maxPoolSize", "1");
        System.setProperty("jdk.tracePinnedThreads", "full");

        try (ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor()) {

            // Virtual Thread 1
            executor.submit(() -> {
                CustomLogUtils.log(startTime, "VT-1", "START");

                synchronized (LOCK) {
                    vt1Started.countDown();

                    try {
                        // Simulate the I/O blocking
                        CustomLogUtils.log(startTime, "VT-1", "SLEEP 3 seconds");
                        Thread.sleep(BLOCKING_TIME);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }

                    CustomLogUtils.log(startTime, "VT-1", "END");
                }
            });

            // Use `CountDownLatch` to ensure that Virtual thread 2 runs after Virtual thread  1.
            vt1Started.await();

            // Virtual Thread 2
            executor.submit(() -> {
                CustomLogUtils.log(startTime, "VT-2", "START");
                CustomLogUtils.log(startTime, "VT-2", "END");
            });
        }

    }
}

/*
* Configure the Virtual Thread Schedule to only use 1 Carrier Thread.
* VT-1 acquires the `synchronize` monitor and become pinned to the Carrier thread.
* While VT-1 is blocked inside the `synchronize` block, it can't unmount from the Carrier Thread.
* VT-2 must wait until VT-1 finishes, release the monitor, and unmount from the Carrier Thread before it can be scheduled.
*
* Note: The Carrier Thread is not returned to the pool while VT-1 is pinned.
* It becomes available for other Virtual Threads only after VT-1 unmounts.
* */

/*
[  27 ms] [VT-1] [Carrier: VirtualThread[#21]/runnable@ForkJoinPool-1-worker-1] START
[  49 ms] [VT-1] [Carrier: VirtualThread[#21]/runnable@ForkJoinPool-1-worker-1] SLEEP 3 seconds
VirtualThread[#21]/runnable@ForkJoinPool-1-worker-1 reason:MONITOR
    java.base/java.lang.VirtualThread$VThreadContinuation.onPinned(VirtualThread.java:199)
    java.base/jdk.internal.vm.Continuation.onPinned0(Continuation.java:393)
    java.base/java.lang.VirtualThread.parkNanos(VirtualThread.java:635)
    java.base/java.lang.VirtualThread.sleepNanos(VirtualThread.java:807)
    java.base/java.lang.Thread.sleep(Thread.java:507)
    com.example.concurrency.virtualthread.VirtualThread_Pinning.lambda$main$0(VirtualThread_Pinning.java:36) <== monitors:1
    java.base/java.util.concurrent.Executors$RunnableAdapter.call(Executors.java:572)
    java.base/java.util.concurrent.FutureTask.run(FutureTask.java:317)
    java.base/java.lang.VirtualThread.run(VirtualThread.java:329)
[3052 ms] [VT-1] [Carrier: VirtualThread[#21]/runnable@ForkJoinPool-1-worker-1] END
[3053 ms] [VT-2] [Carrier: VirtualThread[#24]/runnable@ForkJoinPool-1-worker-1] START
[3053 ms] [VT-2] [Carrier: VirtualThread[#24]/runnable@ForkJoinPool-1-worker-1] END
* */