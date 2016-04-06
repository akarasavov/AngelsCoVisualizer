package akt.app.report;

import akt.app.model.Startup;
import akt.app.utils.JsonMapper;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReportBuilder
{
    private final List<Startup> startups;

    public ReportBuilder(String file) throws IOException
    {
        this.startups = readStartups(file);
    }

    private List<Startup> readStartups(String file) throws IOException
    {
        BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
        String line = null;
        List<Startup> startups = new ArrayList<>();
        while ((line = bufferedReader.readLine()) != null)
        {
            System.out.println(line);
            Startup startup = JsonMapper.toModel(line);
            startups.add(startup);
        }
        return startups;
    }

    public Map<String, BigInteger> buildReportMarketAmountOfMoney()
    {
        Map<String, BigInteger> map = new HashMap<>();
        startups.stream()
                .filter((elem) -> elem.getTotalRaised().length() > 2)
                .forEach((elem) -> {
                    try
                    {
                        BigInteger totalRaised = new BigInteger(elem.getTotalRaised().replaceAll(",", ""));
                        if (map.get(elem.getMarket()) == null)
                        {
                            map.put(elem.getMarket(), totalRaised);
                        } else
                        {
                            map.put(elem.getMarket(), map.get(elem.getMarket()).add(totalRaised));
                        }
                    } catch (NumberFormatException exception)
                    {
                        System.out.println("Error in:" + elem.getTotalRaised() );
                    }

                });
        return map;
    }

}
