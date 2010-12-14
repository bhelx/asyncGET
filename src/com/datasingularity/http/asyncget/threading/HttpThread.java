package com.datasingularity.http.asyncget.threading;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;

import com.datasingularity.http.asyncget.handlers.AsyncRequestHandler;


/**
 * This is a generic class to represent a thread of an 
 * http request. That request can be a GET request, POST
 * request, etc. Doesn't matter.
 * 
 * @author bhelx
 *
 */
public class HttpThread extends Thread {


    private final HttpClient httpClient;
    private final HttpContext context;
    private final HttpGet httpget;
    private AsyncRequestHandler handler;

    public HttpThread(HttpClient httpClient, AsyncRequestHandler handler) {
        this.httpClient = httpClient;
        this.context = new BasicHttpContext();
        this.httpget = new HttpGet(handler.getURI().toString());
        this.handler = handler;
    }

    @Override
    public void run() {
        try {
            if (handler.started()) {
                HttpResponse response = this.httpClient.execute(this.httpget, this.context);
                handler.finished(response);
            } else {
                this.httpget.abort();
            }
        } catch (IOException ex) {
            System.out.println(ex);
        } 
    }
	
}
