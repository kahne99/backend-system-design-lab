package com.example.concurrency.virtualthread;


//import java.lang.ScopedValue;

/*
* `ScopedValue` is a preview feature in Java 21 - skip it.
* */
public class VirtualThread_ScopedValue {
//    private static final ScopedValue<String> USER_ID = ScopedValue.newInstance();
//
//    static void main() {
//        ScopedValue.where(USER_ID, "USER-1").run(() -> {
//            Thread thread = Thread.ofVirtual().start(() -> {
//                System.out.println("USER_ID :: " + USER_ID);
//            });
//
//            try {
//                thread.join();
//            } catch (InterruptedException e) {
//                throw new RuntimeException(e);
//            }
//
//        });
//    }
}
