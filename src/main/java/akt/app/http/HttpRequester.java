package akt.app.http;

import akt.app.model.PostParameterMap;
import org.apache.commons.io.IOUtils;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;

public class HttpRequester
{
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    public HttpRequester()
    {
    }

    public String getRequest(URI url) throws IOException
    {
        HttpClient httpClient = HttpClientBuilder.create().build();
        HttpGet get = new HttpGet(url);
        HttpResponse response = httpClient.execute(get);
        return IOUtils.toString(response.getEntity().getContent());
    }

    public String postRequest(URI uri, Header[] httpHeaders, PostParameterMap postParameterMap) throws IOException
    {
        HttpClient httpClient = HttpClientBuilder.create().build();
        HttpPost postRequest = new HttpPost(uri);
        postRequest.setHeaders(httpHeaders);
        postRequest.setEntity(buildHttpEntity(postParameterMap));
        HttpResponse execute = httpClient.execute(postRequest);
        if (execute.getStatusLine().getStatusCode() == HttpStatus.SC_OK)
        {
            return IOUtils.toString(execute.getEntity().getContent());
        }
        logger.warn("Request is not executed. Status is " + execute.getStatusLine());
        throw new IllegalStateException("Request status:" + execute.getStatusLine());
    }


    private HttpEntity buildHttpEntity(PostParameterMap postParameterMap) throws UnsupportedEncodingException
    {
        return new UrlEncodedFormEntity(postParameterMap.toNameValuePair());

    }

}
