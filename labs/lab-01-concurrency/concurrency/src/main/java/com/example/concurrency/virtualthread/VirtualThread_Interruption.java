package com.example.concurrency.virtualthread;


/*
* Interruption is a mechanism for sending a signal that request a thread to stop its current task.
* It does not directly kill the thread.
* */
public class VirtualThread_Interruption {


    public static void main(String[] args) throws InterruptedException {

        Thread thread = Thread.ofVirtual().start(() -> {

            while (!Thread.currentThread().isInterrupted()) {
                System.out.println("Thread is running ....");
            }

            System.out.println("Thread receive a signal to stop thread.");
        });

        Thread.sleep(1);

        // send a signal to stop virtual thread
        thread.interrupt();
    }
}
