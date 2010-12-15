package com.datasingularity.http.asyncget;

import com.datasingularity.http.asyncget.handlers.GetHandler;
import com.datasingularity.http.asyncget.parsing.UrlFileParser;
import com.datasingularity.http.asyncget.parsing.UrlFileParserFactory;
import com.datasingularity.http.asyncget.threading.HttpThreadPool;
import com.datasingularity.http.asyncget.threading.Report;

import java.io.BufferedReader;
import java.io.EOFException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;


/**
 * Main Class
 *
 * @author bhelx
 */
public class AsyncGET {

    public static void main(String[] args) {

        try {

            if (args.length < 3) {
                usage();
                System.exit(0);
            }
            
            //verify output directory
            File outputDir = new File(args[1]);
            if (!outputDir.exists()) {
            	System.out.print(args[1] + " does not exist, do you want to create it? (y/n) ");
            	BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            	String result = br.readLine();
            	if (result.equals("y")  || result.equals("Y")) {
            		outputDir.mkdir();
            	} else {
            		System.out.println("Exiting..");
            		System.exit(0);
            	} 
            }
            
            File inputFile = new File(args[0]);
            UrlFileParser parser = UrlFileParserFactory.createURLFileParser(inputFile);
       
            HttpThreadPool pool = new HttpThreadPool();

            pool.setMaxConnections(Integer.valueOf(args[2]));

            URL url;

            final long startTime = System.currentTimeMillis();
            
            //TODO - OMG, this is messy
            /* It would be nice if the httpthreadpool could manage
             * the parser and ask for the nextURL when it's threads
             * fall below the MAX_CONNECTION limit. This will work
             * for now.
             */ 
            while (true) {
            	try {
            		url = parser.getNextURL();
            		if (url != null) {
            			GetHandler getHandler = new GetHandler(url.toURI(), args[1]);
            			pool.sumbitHandler(getHandler);
            		} else {
            			//this is some kind of invalid url
            		}
            	} catch (EOFException ex) {
            		break;
            	} 
            } 
            
            parser.close(); //close the parser

            //join all the threads, there are always some stragglers
            ArrayList<Thread> threads = pool.getThreads();
            for (Thread thread : threads) {
                thread.join();
            }

            //shutdown the connection manager
            pool.getClientConnectionManager().shutdown(); 

            System.out.println();
            System.out.println("Downloaded " + pool.getReport().getResourceCount() + " resources totalling "
                    + pool.getReport().getBytes()/1000000.0 + " Megabytes in "
                    + ((System.currentTimeMillis()-startTime)/1000.0)
                    + " seconds");
            System.out.println();

        } catch (FileNotFoundException ex) {
            System.out.println(ex);
            usage();
        } catch (InterruptedException ex) {
            System.out.println(ex);
        } catch (IOException ex) {
            System.out.println(ex);
        } catch (URISyntaxException ex) {
            System.out.println(ex);
        }
        
        System.exit(0);

    }

    public static void usage() {
        System.out.println("asyncGET <urls_file> <output_directory> <num_simultaneous_connections>");
    }

}
