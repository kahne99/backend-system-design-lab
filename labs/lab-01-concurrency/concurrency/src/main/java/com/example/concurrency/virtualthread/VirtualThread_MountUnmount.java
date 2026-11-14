package com.example.concurrency.virtualthread;

import com.example.concurrency.utils.CustomLogUtils;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/*
* When a Virtual Thread encounters a supported blocking I/O operation, the JVM can **unmount** it from its Carrier Thread,
* allowing that Carrier Thread to execute another Virtual Thread.

* Once the blocking operation completes (e.g., the waiting period expires or the OS signals that I/O is ready),
* the Virtual Thread becomes runnable and can be **mounted** onto an available Carrier Thread to resume execution.
* */
public class VirtualThread_MountUnmount {
    private static int BLOCKING_TIME = 3000;

    public static void main(String[] args) throws InterruptedException {
        CountDownLatch vt1Started = new CountDownLatch(1);
        long startTime = System.currentTimeMillis();

        try (ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor()) {

            executor.submit(() -> {
                CustomLogUtils.log(startTime, "VT-1", "START");

                vt1Started.countDown();

                try {
                    // Simulate the I/O blocking
                    Thread.sleep(BLOCKING_TIME);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

                CustomLogUtils.log(startTime, "VT-1", "END");

            });

            // Use `CountDownLatch` to ensure that Virtual thread 2 runs after Virtual thread  1.
            vt1Started.await();

            executor.submit(() -> {
                CustomLogUtils.log(startTime, "VT-2", "START");
                CustomLogUtils.log(startTime, "VT-2", "END");
            });
        }

    }
}

/*
[  26 ms] [VT-1] [Carrier: VirtualThread[#21]/runnable@ForkJoinPool-1-worker-1] START
[  53 ms] [VT-2] [Carrier: VirtualThread[#25]/runnable@ForkJoinPool-1-worker-1] START
[  54 ms] [VT-2] [Carrier: VirtualThread[#25]/runnable@ForkJoinPool-1-worker-1] END
[3053 ms] [VT-1] [Carrier: VirtualThread[#21]/runnable@ForkJoinPool-1-worker-1] END
* */