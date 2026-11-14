package com.example.concurrency.virtualthread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;

public class VirtualThread_ShareState {
    private static int rcCounter = 0;
    private static int synchronizeCounter = 0;
    private static final AtomicInteger atomicCounter = new AtomicInteger(0);
    private static int reentrantLockCounter = 0;
    private static int violateCounter = 0;

    private static final ReentrantLock lock = new ReentrantLock();

    public static void main(String[] args) {

        simulateRateCondition();
        simulateShareState_synchronized();
        simulateShareState_atomic();
        simulateShareState_reentrantLock();
        simulateShareState_violate();

        System.out.println("rcCounter :: " + rcCounter);
        System.out.println("synchronizeCounter :: " + synchronizeCounter);
        System.out.println("atomicCounter :: " + atomicCounter);
        System.out.println("reentrantLockCounter :: " + reentrantLockCounter);
        System.out.println("violateCounter :: " + violateCounter);
    }

    /*
    * Simulating a race condition where more than two threads modify a `counter` without any synchronization mechanism.
    * Once the progress is completed, the `counter` will be less than the expected value.
    * */
    private static void simulateRateCondition() {
        try (ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor()) {
            for (int i = 0; i < 1_000; i++) {
                executor.submit(() -> rcCounter++);
            }
        }
    }

    /*
     * This method use `synchronized` to ensure the `synchronizeCounter` variable == expected value.
     * */
    private static void simulateShareState_synchronized() {
        try (ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor()) {
            for (int i = 0; i < 1_000; i++) {
                executor.submit(VirtualThread_ShareState::increment);
            }
        }
    }

    /*
    * This method use `atomic` to ensure the `atomicCounter` variable == expected value.
    * */
    private static void simulateShareState_atomic() {
        try (ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor()) {
            for (int i = 0; i < 1_000; i++) {
                executor.submit(atomicCounter::incrementAndGet);
            }
        }
    }

    /*
     * This method use `atomic` to ensure the `reentrantCounter` variable == expected value.
     * */
    private static void simulateShareState_reentrantLock() {
        try (ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor()) {
            for (int i = 0; i < 1_000; i++) {
                executor.submit(() -> {
                    lock.lock();

                    try {
                        reentrantLockCounter ++;
                    } finally {
                        lock.unlock();
                    }
                });
            }
        }
    }

    /*
     * `violate` does not guarantee resolution of the race conditions. It ensures visibility across the thread.
     * */
    private static void simulateShareState_violate() {
        try (ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor()) {
            for (int i = 0; i < 1_000; i++) {
                executor.submit(() -> {
                    violateCounter ++;
                });
            }
        }
    }

    private static synchronized void increment() {
        synchronizeCounter ++;
    }
}
