package com.example.concurrency.virtualthread;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class VirtualThread_Stack_Chunks_Continuation {
    public static void main(String[] args) throws InterruptedException {

        CountDownLatch vt1Started = new CountDownLatch(1);

        try (ExecutorService executor =
                     Executors.newVirtualThreadPerTaskExecutor()) {

            executor.submit(() -> {
                System.out.println("VT-1 START");

                vt1Started.countDown();

                try {
                    System.out.println("VT-1 SLEEP");
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }

                System.out.println("VT-1 RESUME");
                System.out.println("VT-1 END");
            });

            vt1Started.await();

            executor.submit(() -> {
                System.out.println("VT-2 START");
                System.out.println("VT-2 END");
            });
        }
    }
}

/*
VT-1 START
VT-1 SLEEP
VT-2 START
VT-2 END
VT-1 RESUME
VT-1 END
* */