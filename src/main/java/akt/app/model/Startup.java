package akt.app.model;

public class Startup
{
    private String signal;
    private String joined;
    private String location;
    private String market;
    private String webSite;
    private String employees;
    private String stage;
    private String totalRaised;
    private String externalId;
    private String name;

    public Startup()
    {
    }

    public String getExternalId()
    {
        return externalId;
    }

    public void setExternalId(String externalId)
    {
        this.externalId = externalId;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getSignal()
    {
        return signal;
    }

    public String getJoined()
    {
        return joined;
    }

    public String getLocation()
    {
        return location;
    }

    public String getMarket()
    {
        return market;
    }

    public String getEmployees()
    {
        return employees;
    }

    public String getStage()
    {
        return stage;
    }

    public String getTotalRaised()
    {
        return totalRaised;
    }

    public void setSignal(String signal)
    {
        this.signal = signal;
    }

    public void setJoined(String joined)
    {
        this.joined = joined;
    }

    public void setLocation(String location)
    {
        this.location = location;
    }

    public void setMarket(String market)
    {
        this.market = market;
    }

    public void setEmployees(String employees)
    {
        this.employees = employees;
    }

    public void setStage(String stage)
    {
        this.stage = stage;
    }

    public void setTotalRaised(String totalRaised)
    {
        this.totalRaised = totalRaised;
    }

    public String getWebSite()
    {

        return webSite;
    }

    public void setWebSite(String webSite)
    {
        this.webSite = webSite;
    }
}
