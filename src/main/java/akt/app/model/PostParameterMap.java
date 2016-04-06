package akt.app.model;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.lang.String.valueOf;

public class PostParameterMap
{
    private Map<String, String> parameterMap;

    private PostParameterMap()
    {
        this.parameterMap = new HashMap<>();
        init();
    }

    public static PostParameterMap build()
    {
        return new PostParameterMap();
    }

    private void init()
    {
        parameterMap.put("sort", "signal");
    }

    public List<? extends NameValuePair> toNameValuePair()
    {
        List<NameValuePair> nameValuePairs = new ArrayList<>();
        parameterMap.forEach((key, value) -> nameValuePairs.add(new BasicNameValuePair(key, value)));
        return nameValuePairs;
    }

    public PostParameterMap setPageParameter(int value)
    {
        parameterMap.put("page", valueOf(value));
        return this;
    }

    public PostParameterMap setSortBy(String value)
    {
        parameterMap.put("sort", value);
        return this;
    }

    public PostParameterMap addLocation(String location)
    {
        parameterMap.put("filter_data[locations][]", location);
        return this;
    }

}
