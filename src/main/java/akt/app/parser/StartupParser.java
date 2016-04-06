package akt.app.parser;

import akt.app.model.Startup;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class StartupParser
{
    private Logger logger = LoggerFactory.getLogger(StartupParser.class);

    public List<Startup> parse(String html)
    {
        Document jsoup = Jsoup.parse(html);
        Elements parameters = jsoup.getElementsByClass("value");
        Elements ids = jsoup.getElementsByClass("name");
        int i = 0, j = 0;
        List<Startup> startups = new ArrayList<>();
        Startup startup = null;
        for (Element element : parameters)
        {
            if (i == 8)
            {
                i = 0;
                startup.setExternalId(buildExternalId(ids, j));
                startup.setName(buildName(ids, j));
                startups.add(startup);
                j++;
            }

            if (i == 0)
            {
                startup = new Startup();
                startup.setSignal(parseSignal(element));
            } else if (i == 1)
            {
                startup.setJoined(parseJoinedAt(element));
            } else if (i == 2)
            {
                startup.setLocation(parseLocation(element));
            } else if (i == 3)
            {
                startup.setMarket(parseMarket(element));
            } else if (i == 4)
            {
                startup.setWebSite(parseWebSite(element));
            } else if (i == 5)
            {
                startup.setEmployees(parseEmployees(element));
            } else if (i == 6)
            {
                startup.setStage(parseStage(element));
            } else if (i == 7)
            {
                startup.setTotalRaised(parseTotalRaised(element));
            }
            i++;
        }
        return startups;
    }

    private String buildExternalId(Elements ids, int j)
    {
        try
        {
            if (!ids.isEmpty() && j < ids.size())
            {
                return ids.get(j).getElementsByTag("a").get(0).attr("data-id").replaceAll("[^0-9]+", "");
            }

        } catch (Exception exception)
        {
            logger.error("externalId construction error:", exception);
        }
        throw new IllegalStateException("External id can`t be build");
    }

    private String buildName(Elements ids, int j)
    {
        try
        {
            if (!ids.isEmpty() && j < ids.size())
            {
                return ids.get(j).getElementsByTag("a").get(0).text();
            }

        } catch (Exception exception)
        {
            logger.error("externalId construction error:", exception);
        }
        throw new IllegalStateException("External id can`t be build");
    }

    private String parseTotalRaised(Element element)
    {
        try
        {
            return element.getElementsByClass("value").text().replaceAll("\\\\n|\\$", "");
        } catch (Exception exception)
        {
            logger.info("TotalRaised is unparsible string:" + element.text());
        }
        return null;
    }

    private String parseStage(Element element)
    {
        try
        {
            return element.getElementsByClass("value").text().replaceAll("\\\\n", "");
        } catch (Exception exception)
        {
            logger.info("Stage is unparsible string:" + element.text());
        }
        return null;
    }

    private String parseEmployees(Element element)
    {
        try
        {
            return element.getElementsByClass("value").text().replaceAll("\\\\n", "");
        } catch (Exception exception)
        {
            logger.info("Employees unparsible string:" + element.text());
        }
        return null;
    }

    private String parseWebSite(Element element)
    {
        try
        {
            return element.getElementsByTag("a").text();
        } catch (Exception exception)
        {
            logger.info("WebSite unparsible string:" + element.text());
        }
        return null;
    }

    private String parseMarket(Element element)
    {
        try
        {
            return element.getElementsByTag("a").text();
        } catch (Exception exception)
        {
            logger.info("Market unparsible string:" + element.text());
        }
        return null;
    }

    private String parseLocation(Element element)
    {
        try
        {
            return element.getElementsByTag("a").text();
        } catch (Exception exception)
        {
            logger.info("Location unparsible string:" + element.text());
        }
        return null;
    }

    private String parseJoinedAt(Element element)
    {
        try
        {
            return element.text().replaceAll("\\\\n", "");
        } catch (Exception exception)
        {
            logger.info("JoinedAt unparsible string:" + element.text());
        }
        return null;
    }

    private String parseSignal(Element element)
    {
        try
        {
            return element.getElementsByTag("img").get(0).attr("alt").replaceAll("[^a-zA-Z1-9]+", "");
        } catch (Exception exception)
        {
            logger.info("Signal unparsible string:" + element.text());
        }
        return null;
    }

}
