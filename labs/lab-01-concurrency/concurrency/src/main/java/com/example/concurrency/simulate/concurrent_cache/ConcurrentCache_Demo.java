package com.example.concurrency.simulate.concurrent_cache;

import java.util.Map;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/*
* Scenario:
* * Multiple threads request the same customer data at the same time.
* * If the data is not in the cache, they may all query the database simultaneously.

* Purpose:
* * Demonstrate how ConcurrentHashMap and computeIfAbsent() prevent duplicate database queries and reduce cache stampede.
* */
public class ConcurrentCache_Demo {
    private static final Map<Long, String> cache = new ConcurrentHashMap<>();

    private static final AtomicInteger dbQueryCount = new AtomicInteger();

    public static void main(String[] args) throws InterruptedException {
        ExecutorService executor = Executors.newFixedThreadPool(10);
        CountDownLatch startLatch = new CountDownLatch(1);

        for (int i = 0; i < 10; i++) {
            executor.submit(() -> {
                try {
                    // to ensure that all tasks run simultaneously
                    startLatch.await();

                    // get customer
                    String customer = getCustomer(1001L);

                    System.out.println(Thread.currentThread().getName()+ " - Result: " + customer);

                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }

            });
        }

        startLatch.countDown();
        executor.shutdown();

        boolean awaited = executor.awaitTermination(10, TimeUnit.SECONDS);
        if (!awaited) {
            executor.shutdownNow();
        }

        System.out.println("Total DB queries: " + dbQueryCount.get());
    }


    private static String loadFromDatabase(Long id) {
        dbQueryCount.incrementAndGet();
        System.out.println(Thread.currentThread().getName() + " - Query DB for customer " + id);

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException(e);
        }

        return "Customer-" + id;
    }

    public static String getCustomer(Long id) {
        return cache.computeIfAbsent(id, ConcurrentCache_Demo::loadFromDatabase);
    }
}
