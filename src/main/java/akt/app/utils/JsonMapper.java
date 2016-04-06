package akt.app.utils;

import akt.app.model.Startup;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class JsonMapper
{
    public static String toString(Startup startup) throws JsonProcessingException
    {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(startup);
    }

    public static Startup toModel(String line) throws IOException
    {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(line, Startup.class);
    }
}
