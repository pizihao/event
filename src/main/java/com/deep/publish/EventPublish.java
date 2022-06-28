package com.deep.publish;

/**
 * <h2>事件发布中心</h2>
 *
 * @author Create by liuwenhao on 2022/6/28 17:21
 */
@FunctionalInterface
public interface EventPublish {

    /**
     * 发布事件
     *
     * @author liuwenhao
     * @date 2022/6/28 17:35
     */
    void publish(Object event);

}