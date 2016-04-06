package akt.app.run;

import akt.app.http.HttpRequester;
import akt.app.model.PostParameterMap;
import akt.app.model.Startup;
import akt.app.parser.StartupParser;
import akt.app.utils.JsonMapper;
import akt.app.utils.JsonParameterUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.apache.commons.io.IOUtils;
import org.apache.http.Header;
import org.apache.http.client.utils.URIBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

public class WorkerRunnable extends Thread
{
    private final FileWriter fileWriter;
    private final AtomicInteger pageNumber;
    private final HttpRequester httpRequester;
    private final String host;
    private final int port;
    private final URI postURI;
    private final Header[] postHeaders;
    private final PostParameterMap postParameterMap;
    private final CountDownLatch countDownLatch;
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    public WorkerRunnable(AtomicInteger pageNumber,
                          Header[] postHeaders,
                          File file,
                          PostParameterMap postParameterMap,
                          CountDownLatch countDownLatch)
            throws IOException, URISyntaxException
    {
        this.fileWriter = new FileWriter(file, true);
        this.pageNumber = pageNumber;
        this.httpRequester = new HttpRequester();
        this.host = "angel.co";
        this.port = 443;
        this.postURI = new URI("https", null, host, port, "/company_filters/search_data", null, null);
        this.postHeaders = postHeaders;
        this.postParameterMap = postParameterMap;
        this.countDownLatch = countDownLatch;
    }

    @Override
    public void run()
    {
        while (true)
        {
            try
            {
                int page = pageNumber.getAndIncrement();
                logger.info("worker:" + getName() + " start process page=" + page);
                postParameterMap.setPageParameter(page);
                String jsonContent = httpRequester.postRequest(postURI, postHeaders, postParameterMap);
                URI uri = new URIBuilder()
                        .setScheme("https")
                        .setHost(host)
                        .setPort(port)
                        .setPath("/companies/startups")
                        .addParameters(JsonParameterUtil.toNameValuePairList(jsonContent)).
                                build();
                String htmlDocument = httpRequester.getRequest(uri);
                StartupParser startupParser = new StartupParser();

                writeToFile(startupParser.parse(htmlDocument));
                logger.info("worker:" + getName() + " write to file");
            } catch (URISyntaxException | IOException exception)
            {
                logger.error("Worker:" + getName() + " error \n", exception);
                break;
            }
            finally
            {
                IOUtils.closeQuietly(fileWriter);
                countDownLatch.countDown();
            }
        }
    }


    private void writeToFile(List<Startup> startups) throws IOException
    {
        StringBuilder stringBuilder = new StringBuilder();
        startups.forEach((elem) -> {
            try
            {
                stringBuilder.append(JsonMapper.toString(elem)).append("\n");
            } catch (JsonProcessingException exception)
            {
                logger.error("jsonConverting error", exception);
            }
        });
        fileWriter.write(stringBuilder.toString());
    }


}
