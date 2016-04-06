package akt.app.utils;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

import static java.lang.String.valueOf;

public class JsonParameterUtil
{
    private static Logger logger = LoggerFactory.getLogger(JsonParameterUtil.class);

    public static List<NameValuePair> toNameValuePairList(String jsonText)
    {
        try
        {
            JSONObject coreObject = new JSONObject(jsonText);
            JSONArray ids = coreObject.getJSONArray("ids");
            List<NameValuePair> parameters = new ArrayList<>();
            for (int i = 0; i < ids.length(); i++)
            {
                parameters.add(new BasicNameValuePair("ids[]", valueOf(ids.getInt(i))));
            }
            parameters.add(new BasicNameValuePair("total", valueOf(coreObject.getInt("total"))));
            parameters.add(new BasicNameValuePair("page", valueOf(coreObject.getInt("page"))));
            parameters.add(new BasicNameValuePair("sort", coreObject.getString("sort")));
            parameters.add(new BasicNameValuePair("new", valueOf(coreObject.getBoolean("new"))));
            parameters.add(new BasicNameValuePair("hexdigest", coreObject.getString("hexdigest")));
            return parameters;
        } catch (JSONException exception)
        {
            logger.error("Unparsable json", exception);
        }
        throw new IllegalStateException("can`t convert this json to object \n json=" + jsonText);

    }

}
