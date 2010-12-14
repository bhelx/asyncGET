package com.datasingularity.http.asyncget.threading;

/**
 * This class is used to represent a Report
 * associated with a thread pool. It is passed to
 * all of the AsyncRequestHandlers and they have 
 * synchronized access to the object. Don't go overboard 
 * on these properties as accessing them locks other threads.
 * 
 * @author bhelx
 */
public final class Report {

    private long bytes;
    private long resourceCount;

    public Report() {
        bytes = 0L;
    }

    public long getBytes() {
        return bytes;
    }

    public long getResourceCount() {
        return resourceCount;
    }

    public synchronized void addBytes(long bytes) {
        this.bytes += bytes;
    }

    public synchronized void addToResourceCount() {
        this.resourceCount++;
    }
    

}
