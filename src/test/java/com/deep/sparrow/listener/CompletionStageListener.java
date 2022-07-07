package java.com.deep.sparrow.listener;

import java.com.deep.sparrow.event.RemoveEvent;

import java.util.concurrent.CompletableFuture;

/**
 * <h2></h2>
 *
 * @author Create by liuwenhao on 2022/7/7 15:20
 */
public class CompletionStageListener implements Listener {

    @Override
    public Object execEvent(Object t) {
        System.out.println(t);
        return CompletableFuture.supplyAsync(() ->
            new RemoveEvent(this, "顺延到RemoveEvent"));
    }
}