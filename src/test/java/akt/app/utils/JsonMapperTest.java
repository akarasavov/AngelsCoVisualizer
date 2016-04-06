package akt.app.utils;

import akt.app.model.Startup;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class JsonMapperTest
{

    @Test
    public void convertToString() throws Exception
    {
        Startup startup = new Startup();
        startup.setEmployees("a");
        System.out.println(JsonMapper.toString(startup));
    }

    @Test
    public void readRussia() throws IOException
    {
        BufferedReader bufferedReader = new BufferedReader(new FileReader("russia.txt"));
        String line = null;
        List<Startup>  startups  =new ArrayList<>();
        while ((line = bufferedReader.readLine()) != null)
        {
            System.out.println(line);
            Startup startup = JsonMapper.toModel(line);
            startups.add(startup);
        }
        System.out.println(startups.size());
    }
}