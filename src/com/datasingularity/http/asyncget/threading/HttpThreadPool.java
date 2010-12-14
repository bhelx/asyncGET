package com.datasingularity.http.asyncget.threading;

import com.datasingularity.http.asyncget.ssl.EasySSLSocketFactory;
import com.datasingularity.http.asyncget.handlers.AsyncRequestHandler;
import com.datasingularity.http.asyncget.handlers.GetHandler;
import java.util.ArrayList;
import org.apache.http.HttpVersion;
import org.apache.http.client.HttpClient;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.params.ConnManagerPNames;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;


/**
 * This class manages the HttpThreads. 
 * 
 * @author bhelx
 */
public class HttpThreadPool {

    private HttpClient httpClient;
    private IdleConnectionMonitorThread monitorThread;
    private HttpParams params;
    private ArrayList<Thread> threads;
    private Report report;

    public HttpThreadPool() {

        this.report = new Report();

        params = new BasicHttpParams();

        //params.setParameter(ConnManagerPNames.MAX_CONNECTIONS_PER_ROUTE, new ConnPerRouteBean(30));
        //params.setParameter(HttpProtocolParams.USE_EXPECT_CONTINUE, false);

        SchemeRegistry schemeRegistry = new SchemeRegistry();
        schemeRegistry.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));
        schemeRegistry.register(new Scheme("https", new EasySSLSocketFactory(), 443));

        HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);

        ClientConnectionManager cm = new ThreadSafeClientConnManager(params, schemeRegistry);
        httpClient = new DefaultHttpClient(cm, params);        

        threads = new ArrayList<Thread>();

        monitorThread = new IdleConnectionMonitorThread(cm);
        monitorThread.start();

    }
    	
    public Report getReport() {
    	return report;
    }

    public ArrayList<Thread> getThreads() {
        return threads;
    }

    public ClientConnectionManager getClientConnectionManager() {
        return this.httpClient.getConnectionManager();
    }

    public void sumbitHandler(AsyncRequestHandler handler) {
        handler.setReport(report);
        Thread thread = new HttpThread(httpClient, handler);
        threads.add(thread);
        thread.start();
    }

    public void setMaxConnections(int maxConnections) {
        params.setParameter(ConnManagerPNames.MAX_TOTAL_CONNECTIONS, maxConnections);
    }
  

}