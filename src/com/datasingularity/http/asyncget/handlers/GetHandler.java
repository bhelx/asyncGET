package com.datasingularity.http.asyncget.handlers;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.http.HttpResponse;

/**
 * This is a simple GET Handler it just uses the http 
 * get to download a resource blindly. 
 *
 * @author bhelx
 */
public class GetHandler extends AsyncRequestHandler {

    public GetHandler(URI uri, String outputDirectory) {
    	super(uri, outputDirectory);
    }

    @Override
    public void finished(HttpResponse response) {
        try {

            URL url = uri.toURL();

            String[] fileNameSplit = url.getFile().split("/");
            final String contentName = fileNameSplit[fileNameSplit.length-1];

            final InputStream is = response.getEntity().getContent();

            File f = new File(outputDirectory + '/' + contentName);
            
            OutputStream fout = new FileOutputStream(f);

            byte[] buffer = new byte[1024];
            int len;
            while((len = is.read(buffer)) > 0) {
                fout.write(buffer, 0, len);
            }

            report.addBytes(response.getEntity().getContentLength());
            report.addToResourceCount();

            fout.close();
            is.close();

            System.out.println("Downloaded " + contentName);

        } catch (IOException ex) {
            Logger.getLogger(GetHandler.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
