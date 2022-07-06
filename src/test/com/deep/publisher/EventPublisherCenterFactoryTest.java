package com.deep.sparrow.publisher;

import com.deep.context.DefaultEventContext;
import com.deep.context.EventContext;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class EventPublisherCenterFactoryTest {

    ThreadPoolExecutor poolExecutor = new ThreadPoolExecutor(
        521,
        1024,
        500,
        TimeUnit.SECONDS,
        new LinkedBlockingQueue<>(),
        new ThreadPoolExecutor.AbortPolicy()
    );

    @Test
    public void testBuilder() {
        EventPublisherCenterFactory centerFactory = new EventPublisherCenterFactory();
        for (int i = 0; i < 1024; i++) {
            EventContext context = new DefaultEventContext(i + "");
            Map<String, EventContext> map = new HashMap<>();
            map.put(context.name(), context);
            EventPublisherCenter builder = centerFactory.builder(map);
            EventContext eventContext = builder.map.get("0");
            Assert.assertNotNull(eventContext);
        }

    }
}