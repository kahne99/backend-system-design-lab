package com.example.concurrency.simulate.stringbuffer_rc;

/**
 * Fix: each thread / helper owns its own {@link SharedParamLog}.
 * No static shared buffer → no AIOOBE on local.append(shared).
 */
public class SharedLogBufferRace {

    public static void main(String[] args) throws Exception {
        SharedParamLog writerLog = new SharedParamLog();
        SharedParamLog readerLog = new SharedParamLog();

        Thread writer = new Thread(() -> {
            int i = 0;
            while (true) {
                writerLog.appendBind(i++);
            }
        }, "writer");

        Thread reader = new Thread(() -> {
            int i = 0;
            while (true) {
                readerLog.appendBind(i++);
                StringBuffer local = new StringBuffer();
                local.append("parameters={").append(readerLog.buffer()).append("}");
            }
        }, "reader");

        writer.setDaemon(true);
        writer.start();
        reader.start();
        Thread.sleep(2000);
        System.out.println("OK - no AIOOBE in 2s (separate buffers)");
        System.exit(0);
    }
}
