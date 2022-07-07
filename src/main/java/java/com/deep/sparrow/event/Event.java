package java.com.deep.sparrow.event;


import java.util.EventObject;

/**
 * <h2>事件监听的顶级类</h>
 *
 * @author Create by liuwenhao on 2022/6/28 11:28
 */
public abstract class Event extends EventObject {

    /**
     * Constructs a prototypical Event.
     *
     * @param source The object on which the Event initially occurred.
     * @throws IllegalArgumentException if source is null.
     */
    protected Event(Object source) {
        super(source);
    }
}