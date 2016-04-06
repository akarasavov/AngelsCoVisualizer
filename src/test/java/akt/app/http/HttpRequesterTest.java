package akt.app.http;

import akt.app.model.PostParameterMap;
import akt.app.model.Startup;
import akt.app.parser.StartupParser;
import akt.app.utils.JsonParameterUtil;
import org.apache.http.Header;
import org.apache.http.client.utils.URIBuilder;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import static akt.app.utils.HeaderUtils.buildHeaders;

public class HttpRequesterTest
{
    private HttpRequester httpRequester;
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private URI postURI;
    private Header[] postHeaders;
    private String host;
    private int port;


    @Before
    public void setUp() throws Exception
    {
        httpRequester = new HttpRequester();
        host = "angel.co";
        port = 443;
        postURI = new URI("https", null, host, port, "/company_filters/search_data", null, null);

        postHeaders = buildHeaders(HttpRequesterTest.class.getResourceAsStream("/postHeaders.txt"));
    }

    @Test
    public void getRequest() throws IOException, URISyntaxException
    {
        String jsonContent = "{\"ids\":[647327,652436,658877,691130,697812,708999,741507,778235,780650,789748,838575,847679,916883,443932,60,63,112,119,130,167],\"total\":821457,\"page\":3,\"sort\":\"signal\",\"new\":false,\"hexdigest\":\"e9a17f0888b443cd02a1edaaa540057e0976cd71\"}";
        URI uri = new URIBuilder()
                .setScheme("https")
                .setHost(host)
                .setPort(port)
                .setPath("/companies/startups")
                .addParameters(JsonParameterUtil.toNameValuePairList(jsonContent)).
                        build();
        logger.info(uri.toString());
        logger.info(httpRequester.getRequest(uri));
    }

    @Test
    public void postRequest() throws IOException, URISyntaxException
    {
        String content = httpRequester.postRequest(postURI, postHeaders, PostParameterMap.build());
        logger.info(content);
    }

    @Test
    public void postRequestRussia() throws IOException
    {
        String content = httpRequester.postRequest(postURI, postHeaders, PostParameterMap.build().addLocation("Russia").setPageParameter(1000));
        logger.info(content);
    }

    @Test
    public void postGetRequest() throws IOException, URISyntaxException
    {

        String jsonContent = httpRequester.postRequest(postURI, postHeaders, PostParameterMap.build());
        URI uri = new URIBuilder()
                .setScheme("https")
                .setHost(host)
                .setPort(port)
                .setPath("/companies/startups")
                .addParameters(JsonParameterUtil.toNameValuePairList(jsonContent)).
                        build();
        logger.info(uri.toString());
        String htmlDocument = httpRequester.getRequest(uri);
        StartupParser startupParser = new StartupParser();
        List<Startup> startups = startupParser.parse(htmlDocument);
        startups.forEach((elem) -> logger.info(elem.getLocation()));

    }

    @Test
    public void twoPostRequest() throws IOException
    {
        String firstContent = httpRequester.postRequest(postURI, postHeaders, PostParameterMap.build());
        String secondContent = httpRequester.postRequest(postURI, postHeaders, PostParameterMap.build().setPageParameter(2));
        String thirdContent = httpRequester.postRequest(postURI, postHeaders, PostParameterMap.build().setPageParameter(3));
        logger.info(firstContent);
        logger.info(secondContent);
        logger.info(thirdContent);
    }
}