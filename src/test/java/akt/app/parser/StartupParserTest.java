package akt.app.parser;

import akt.app.model.Startup;
import org.apache.commons.io.IOUtils;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;

public class StartupParserTest
{
    private StartupParser startupParser;
    private Logger logger = LoggerFactory.getLogger(this.getClass());


    @Before
    public void setUp() throws Exception
    {
        startupParser = new StartupParser();

    }

    @Test
    public void parseStartup() throws IOException
    {
        String html = IOUtils.toString(StartupParserTest.class.getResource("/postTestData.html"));
        List<Startup> startups = startupParser.parse(html);
        startups.forEach((elem) -> {
            logger.info("Name:" + elem.getName());
            logger.info("Id:" + elem.getExternalId());
            logger.info("Signal:" + elem.getSignal());
            logger.info("employee:" +  elem.getEmployees());
            logger.info("raised:"  +elem.getTotalRaised());
        });
    }

}