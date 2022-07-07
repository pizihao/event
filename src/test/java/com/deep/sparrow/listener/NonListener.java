package java.com.deep.sparrow.listener;


import java.com.deep.sparrow.event.AddEvent;
import java.com.deep.sparrow.event.RemoveEvent;

/**
 * <h2></h2>
 *
 * @author Create by liuwenhao on 2022/7/3 16:54
 */
public class NonListener {


    @EventListener(value = AddEvent.class, order = 10)
    public RemoveEvent toRemoveEventOrder10(AddEvent addEvent) {
        System.out.println(addEvent);
        System.out.println("最晚到达");
        return new RemoveEvent(this, "顺延到删除阶段");
    }

    @EventListener(AddEvent.class)
    public RemoveEvent toRemoveEvent(AddEvent addEvent) {
        System.out.println(addEvent);
        System.out.println("测试是否到达");
        return new RemoveEvent(this, "顺延到删除阶段");
    }

    @EventListener(value = AddEvent.class, order = 5)
    public RemoveEvent toRemoveEventOrder5(AddEvent addEvent) {
        System.out.println(addEvent);
        System.out.println("较晚到达");
        return new RemoveEvent(this, "顺延到删除阶段");
    }

}