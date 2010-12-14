package com.datasingularity.http.asyncget.parsing;

import java.io.File;
import java.io.FileNotFoundException;

/**
 * Only supporting text format right now
 *
 * @author bhelx
 */
public class UrlFileParserFactory {

    public static UrlFileParser createURLFileParser(File file) throws FileNotFoundException {

        //TODO - check extension and find appropriate parser

        UrlFileParser parser =  new TextUrlFileParser();
        parser.setFile(file);
        return parser;
    }

}
