package akt.app.utils;

import org.apache.http.Header;
import org.apache.http.message.BasicHeader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class HeaderUtils
{
    private static Logger logger = LoggerFactory.getLogger(HeaderUtils.class);

    public static Header[] buildHeaders(InputStream inputStream) throws IOException
    {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        String line;
        List<Header> headerList = new ArrayList<>();
        int lineCounter = 0;
        while ((line = bufferedReader.readLine()) != null)
        {
            String[] split = line.split(":", 2);
            if (split.length == 2)
            {
                headerList.add(new BasicHeader(split[0], split[1]));
            } else
            {
                logger.error("Line " + lineCounter + " is not parsable");
                throw new IllegalStateException("Illegal state when parse file");
            }
            lineCounter++;
        }
        Header[] arrayOfHeaders = new Header[headerList.size()];
        return headerList.toArray(arrayOfHeaders);
    }
}
