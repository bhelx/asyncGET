package com.datasingularity.http.asyncget.parsing;

import java.io.BufferedReader;
import java.io.EOFException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;


/**
 * An implementation of a URLFileParser that just takes a 
 * list of URLs separated by newlines.
 *
 * @author bhelx
 */
public class TextUrlFileParser implements UrlFileParser {

    private BufferedReader bufferedReader;

    @Override
    public void setFile(File file) throws FileNotFoundException {
        final FileReader fileReader = new FileReader(file);
        bufferedReader = new BufferedReader(fileReader);
    }

    @Override
    public URL getNextURL() throws IOException {
    	String urlString = bufferedReader.readLine();
    	
    	if (urlString == null) {
    		return null;
    	}
    	
    	URL url = new URL(urlString);
    	
    	if (url == null) {
    		return getNextURL();
    	} 
    	return url;
    	
    	
    }

    @Override
    public void close() {
        try {
            bufferedReader.close();
        } catch (IOException ex) {
            System.out.println(ex);
        }
    }



}
