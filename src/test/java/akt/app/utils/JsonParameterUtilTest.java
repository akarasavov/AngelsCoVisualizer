package akt.app.utils;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class JsonParameterUtilTest
{
    @Test
    public void toNameValueKey()
    {
        String idContent = "{\"ids\":[258348,356970,361073,363126,367081,369072,373322,374248,377953,378495,380855,383766,387370,388079,388535,395014,401389,401500,401921,412337],\"total\":821372,\"page\":1,\"sort\":\"signal\",\"new\":false,\"hexdigest\":\"2230f4ff3c0ed3b1acb339e45fcaf90f103cdfcf\"}";
        JsonParameterUtil.toNameValuePairList(idContent);
    }

}