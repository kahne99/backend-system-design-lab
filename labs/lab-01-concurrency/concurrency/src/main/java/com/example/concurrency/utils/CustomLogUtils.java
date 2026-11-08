package com.example.concurrency.utils;

public class CustomLogUtils {

    public static void log(long startTime, String virtualThread, String message) {
        long elapsed = System.currentTimeMillis() - startTime;

        System.out.printf(
                "[%4d ms] [%s] [Carrier: %s] %s%n",
                elapsed,
                virtualThread,
                Thread.currentThread(),
                message
        );
    }
}
