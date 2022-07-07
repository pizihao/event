package java.com.deep.sparrow.publisher;

/**
 * <h2>事件发布</h2>
 *
 * @author Create by liuwenhao on 2022/6/28 17:21
 */
@FunctionalInterface
public interface EventPublisher {

    /**
     * 发布事件
     * 接收事件时需要验证参数是否是自身事件所需的类型
     * 本类可以适用于不同事件类型的监听
     *
     * @author liuwenhao
     * @date 2022/6/28 17:35
     */
    void publish(Object event);

}