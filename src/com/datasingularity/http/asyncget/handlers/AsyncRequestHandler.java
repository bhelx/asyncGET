package com.datasingularity.http.asyncget.handlers;

import com.datasingularity.http.asyncget.threading.Report;

import java.net.URI;
import org.apache.http.HttpResponse;

/**
 * This abstract represents an HTTP handler, 
 * basically a callback object. You can have
 * many different types. They can do different 
 * things depending on the uri, etc. I have implemented
 * a simple GET.
 *
 * @author bhelx
 */
public abstract class AsyncRequestHandler {
  
    protected URI uri;
    protected String outputDirectory;
    protected Report report;
	
    public AsyncRequestHandler(URI uri, String outputDirectory) {
        this.outputDirectory = outputDirectory;
        this.uri = uri;
    }

    public final void setReport(Report report) {
        this.report = report;
    }
    
    public final URI getURI() {
    	return uri;
    }

    public boolean started() {
        return true;
    }

    public void timeout() {
        if (uri != null) {
            System.out.println(uri.toString()+" timed out");
        } else {
            System.out.println("timeout");
        }
    }
    
    /* 
     * You MUST implement the finish().
     */
    public abstract void finished(HttpResponse response);
    
    
}

