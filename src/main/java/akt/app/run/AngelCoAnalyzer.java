package akt.app.run;

import akt.app.model.PostParameterMap;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

import static akt.app.utils.HeaderUtils.*;

public class AngelCoAnalyzer
{

    public static void main(String[] args) throws IOException, URISyntaxException, InterruptedException
    {
        String location = "United State";
        String sortBy = "raised";
        int processors = Runtime.getRuntime().availableProcessors();
        boolean mkdir = new File(location + "_" + sortBy).mkdir();
        CountDownLatch countDownLatch = new CountDownLatch(processors);
        AtomicInteger pageCounter = new AtomicInteger();
        if (mkdir)
        {
            PostParameterMap postParameterMap = PostParameterMap.build().addLocation(location).setSortBy(sortBy);
            for (int i = 0; i < processors; i++)
            {
                String filePath = location + "_" + sortBy+"/" + i + ".txt";
                WorkerRunnable workerRunnable = new WorkerRunnable(pageCounter,
                        buildHeaders(AngelCoAnalyzer.class.getResourceAsStream("/postHeaders.txt")),
                        new File(filePath),
                        postParameterMap,
                        countDownLatch);
                workerRunnable.start();
            }
            countDownLatch.await();
        }
    }

}
