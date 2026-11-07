package com.example.concurrency.platformthread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/*
* A platform thread is mapped by the JVM directory to an OS thread.
* Use the OS resources to manage execution.
* */
public class PlatformThread_Init {
    public static void main(String[] args) {

// Method 1: Using Thread
        Thread thread1 = new Thread(() -> {
            System.out.println("A platform thread created using `new Thread`.");
        });

        thread1.start();

// Method 2: Using Thread.ofPlatform() -- Support from Java 21
        Thread thread2 = Thread.ofPlatform()
                .name("platform-thread")
                .start(() -> {
                    System.out.println("A platform thread created using `Thread.ofPlatform()`");
                });

// Method 3: Using `ExecutorService`
        ExecutorService executor = Executors.newFixedThreadPool(1);

        executor.submit(() -> {
            System.out.println("A platform thread created using `ExecutorService`");
        });

        executor.close();
    }
}
