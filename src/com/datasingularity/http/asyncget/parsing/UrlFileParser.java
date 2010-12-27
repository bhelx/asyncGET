package com.datasingularity.http.asyncget.parsing;

import java.io.EOFException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;

/**
 * This interface is used to parse URLs from files. 
 * Each impl will be associated with a particular file type.
 * I have already implemented TextUrlFileParser which takes 
 * a pure txt file with newline separated URLs. Another example 
 * may be an HtmlUrlFileParser or an XmlUrlFileParser. 
 *
 * @author bhelx
 */
public interface UrlFileParser {

    public URL getNextURL() throws IOException;
    public void setFile(File file) throws FileNotFoundException;
    public void close();

}
