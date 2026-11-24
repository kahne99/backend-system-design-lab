package com.example.concurrency.simulate.thread_coordination;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/*
* CyclicBarrier is a synchronization mechanism that allows multiple threads to wait for each others at synchronize point
* (barrier point). Once all threads have reached the barrier point, they are released and can proceed to the next phase.
*
* It's useful for problems where multiple threads need to wait for other threads to complete a part of task
* before moving to the next steps.
*
* In the example below, although the threads have different execution times,
* they must wait for each other to complete their tasks before proceeding to the next step.
* */
public class CyclicBarrier_Demo {
    private static int NUMBER_THREADS = 3;

    public static void main(String[] args) {

        CyclicBarrier barrier = new CyclicBarrier(
                NUMBER_THREADS,
                () -> {
                    System.out.println("All threads completed. Generating report ....");
                }
        );
        ExecutorService executor = Executors.newFixedThreadPool(NUMBER_THREADS);

        try {
            executor.submit(() -> processBalance(barrier));
            executor.submit(() -> processProfitLoss(barrier));
            executor.submit(() -> processMargin(barrier));

        } finally {
            executor.shutdown();
        }
    }

    private static void processBalance(CyclicBarrier barrier) {
        try {
            System.out.println("[Balance] - Start calculate account balances ....");
            Thread.sleep(2000);
            System.out.println("[Balance] - Calculation completed.");

            // Wait until all workers arrive.
            barrier.await();

            System.out.println("[Balance] - Generating report...");

        } catch (BrokenBarrierException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void processProfitLoss(CyclicBarrier barrier) {
        try {
            System.out.println("[ProfitLoss] - Start calculate account profit, loss ....");
            Thread.sleep(3000);
            System.out.println("[ProfitLoss] - Calculation completed.");

            // Wait until all workers arrive.
            barrier.await();

            System.out.println("[ProfitLoss] - Generating report...");

        } catch (BrokenBarrierException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void processMargin(CyclicBarrier barrier) {
        try {
            System.out.println("[Margin] - Start calculate margin ....");
            Thread.sleep(1000);
            System.out.println("[Margin] - Calculation completed.");

            // Wait until all workers arrive.
            barrier.await();

            System.out.println("[Margin] - Generating report...");

        } catch (BrokenBarrierException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}


/*
[Balance] - Start calculate account balances ....
[ProfitLoss] - Start calculate account profit, loss ....
[Margin] - Start calculate margin ....
[Margin] - Calculation completed.
[Balance] - Calculation completed.
[ProfitLoss] - Calculation completed.
All threads completed. Generating report ....        <- Wait until all threads done block before `barrier.await()`.
            ==================== start to the next phase ========================
[ProfitLoss] - Generating report...
[Margin] - Generating report...
[Balance] - Generating report...
* */