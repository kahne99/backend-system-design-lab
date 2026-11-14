package com.example.concurrency.virtualthread;

import com.example.concurrency.utils.CustomLogUtils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/*
 * With Virtual Threads, we can create a large number of threads at a low cost.
 * However, they can't handle a massive number of database connections or HTTP connections.
 *
 * A `Semaphore` allows only a certain number of threads to operate at the same time.
 * */
public class VirtualThread_Semaphore_ResourceLimiting {
    private static final int TASK_COUNT = 10;
    private static final int MAX_CONCURRENT_TASKS = 3;
    private static final Semaphore semaphore = new Semaphore(MAX_CONCURRENT_TASKS);

    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();

        try (ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor()) {
            for (int i = 1; i <= TASK_COUNT; i++) {
                final int taskId = i;


                executor.submit(() -> {
                    CustomLogUtils.log(startTime, "VT-" + taskId, "START");

                    try {
                        semaphore.acquire();

                        CustomLogUtils.log(startTime, "VT-" + taskId, "START -- Available thread: " + semaphore.availablePermits());

                        // Simulate resource usage
                        Thread.sleep(2000);
                        CustomLogUtils.log(startTime, "VT-" + taskId, "END");
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    } finally {
                        semaphore.release();
//                        CustomLogUtils.log(startTime, "VT-" + taskId, "RELEASE PERMITS");
                    }
                });
            }
        }
    }
}

/*
* Since the `Semaphore` has only 3 permits, a maximum of 3 tasks can be executed within the resource-usage zone at the given time.
* */

/*
[  38 ms] [VT-1] [Carrier: VirtualThread[#21]/runnable@ForkJoinPool-1-worker-1] START
[  38 ms] [VT-10] [Carrier: VirtualThread[#31]/runnable@ForkJoinPool-1-worker-2] START
[  60 ms] [VT-10] [Carrier: VirtualThread[#31]/runnable@ForkJoinPool-1-worker-2] START -- Available thread: 1
[  38 ms] [VT-9] [Carrier: VirtualThread[#30]/runnable@ForkJoinPool-1-worker-1] START
[  38 ms] [VT-6] [Carrier: VirtualThread[#27]/runnable@ForkJoinPool-1-worker-9] START
[  38 ms] [VT-2] [Carrier: VirtualThread[#23]/runnable@ForkJoinPool-1-worker-1] START
[  38 ms] [VT-3] [Carrier: VirtualThread[#24]/runnable@ForkJoinPool-1-worker-9] START
[  38 ms] [VT-8] [Carrier: VirtualThread[#29]/runnable@ForkJoinPool-1-worker-1] START
[  38 ms] [VT-4] [Carrier: VirtualThread[#25]/runnable@ForkJoinPool-1-worker-2] START
[  38 ms] [VT-7] [Carrier: VirtualThread[#28]/runnable@ForkJoinPool-1-worker-1] START
[  38 ms] [VT-5] [Carrier: VirtualThread[#26]/runnable@ForkJoinPool-1-worker-9] START
[  60 ms] [VT-1] [Carrier: VirtualThread[#21]/runnable@ForkJoinPool-1-worker-1] START -- Available thread: 2
[  62 ms] [VT-9] [Carrier: VirtualThread[#30]/runnable@ForkJoinPool-1-worker-2] START -- Available thread: 0
[2070 ms] [VT-10] [Carrier: VirtualThread[#31]/runnable@ForkJoinPool-1-worker-2] END
[2070 ms] [VT-1] [Carrier: VirtualThread[#21]/runnable@ForkJoinPool-1-worker-9] END
[2070 ms] [VT-9] [Carrier: VirtualThread[#30]/runnable@ForkJoinPool-1-worker-7] END
[2072 ms] [VT-6] [Carrier: VirtualThread[#27]/runnable@ForkJoinPool-1-worker-8] START -- Available thread: 0
[2073 ms] [VT-2] [Carrier: VirtualThread[#23]/runnable@ForkJoinPool-1-worker-6] START -- Available thread: 0
[2073 ms] [VT-3] [Carrier: VirtualThread[#24]/runnable@ForkJoinPool-1-worker-4] START -- Available thread: 0
[4077 ms] [VT-2] [Carrier: VirtualThread[#23]/runnable@ForkJoinPool-1-worker-6] END
[4077 ms] [VT-3] [Carrier: VirtualThread[#24]/runnable@ForkJoinPool-1-worker-6] END
[4077 ms] [VT-6] [Carrier: VirtualThread[#27]/runnable@ForkJoinPool-1-worker-2] END
[4077 ms] [VT-8] [Carrier: VirtualThread[#29]/runnable@ForkJoinPool-1-worker-2] START -- Available thread: 0
[4080 ms] [VT-7] [Carrier: VirtualThread[#28]/runnable@ForkJoinPool-1-worker-2] START -- Available thread: 0
[4079 ms] [VT-4] [Carrier: VirtualThread[#25]/runnable@ForkJoinPool-1-worker-2] START -- Available thread: 0
[6094 ms] [VT-7] [Carrier: VirtualThread[#28]/runnable@ForkJoinPool-1-worker-3] END
[6094 ms] [VT-8] [Carrier: VirtualThread[#29]/runnable@ForkJoinPool-1-worker-5] END
[6094 ms] [VT-4] [Carrier: VirtualThread[#25]/runnable@ForkJoinPool-1-worker-11] END
[6095 ms] [VT-5] [Carrier: VirtualThread[#26]/runnable@ForkJoinPool-1-worker-5] START -- Available thread: 0
[8097 ms] [VT-5] [Carrier: VirtualThread[#26]/runnable@ForkJoinPool-1-worker-5] END
* */
