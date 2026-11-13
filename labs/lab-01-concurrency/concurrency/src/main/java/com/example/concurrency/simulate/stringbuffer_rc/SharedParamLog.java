package com.example.concurrency.simulate.stringbuffer_rc;

/**
 * Per-instance param-log buffer (like non-static PreparedStatementHelper).
 * Do NOT share one instance across threads.
 */
public class SharedParamLog {

    // `static` shared between two threads
    private static final StringBuffer buffer = new StringBuffer();

    public StringBuffer buffer() {
        return buffer;
    }

    public void appendBind(int i) {
        buffer.append("setLong(").append(i).append(",0);");
        if (buffer.length() > 200_000) {
            buffer.delete(0, 50_000);
        }
    }
}
