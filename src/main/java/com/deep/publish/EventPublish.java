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
     * 接收事件时需要验证参数是否是自身事件所需的类型
     * 本类可以适用于不同事件类型的监听
     *
     * @author liuwenhao
     * @date 2022/6/28 17:35
     */
    void publish(Object event);

}