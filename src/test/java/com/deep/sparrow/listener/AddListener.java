package java.com.deep.sparrow.listener;


import java.com.deep.sparrow.event.RemoveEvent;

public class AddListener implements Listener {

    @Override
    public Object execEvent(Object t) {
        System.out.println(t);
        return new RemoveEvent(this, "顺延下");
    }
}