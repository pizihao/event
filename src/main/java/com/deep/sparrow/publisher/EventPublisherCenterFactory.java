package com.deep.sparrow.publisher;

import com.deep.sparrow.context.EventContext;

import java.util.Map;

/**
 * <h2>EventPublisherCenter工厂</h2>
 * 保证在不通过反射的情况下，有且只有一个EventPublisherCenter
 * 如果需要多个EventPublisherCenter则可以通过反射进行,如果生成后需要进行更改，也可使用反射进行
 *
 * @author Create by liuwenhao on 2022/7/1 17:10
 */
public class EventPublisherCenterFactory {

    EventPublisherCenter eventPublisherCenter;

    private synchronized boolean isExists() {
        return eventPublisherCenter == null;
    }

    private synchronized void assignment(EventPublisherCenter eventPublisherCenter) {
        if (isExists()) {
            this.eventPublisherCenter = eventPublisherCenter;
        }
    }

    public EventPublisherCenter builder() {
        while (isExists()) {
            assignment(new EventPublisherCenter());
        }
        return eventPublisherCenter;
    }

    public EventPublisherCenter builder(Map<String, EventContext> map) {
        while (isExists()) {
            assignment(new EventPublisherCenter(map));
        }
        return eventPublisherCenter;
    }

    public EventPublisherCenterFactory() {
        // non
    }
}