package com.deep.event.event;

/**
 * 边缘传播<br>
 * 除非返回事件自身或同类型的实例，否则传播
 *
 * @author Create by liuwenhao on 2022/7/21 17:10
 */
public class EdgeSpreadPattern implements SpreadPattern {

	@Override
	public <E, R> void spread(EventContext context, R r, E e) throws Throwable {
		if (!r.equals(e) && !r.getClass().equals(e.getClass())) {
			publishEvents(context, r);
		}
	}
}
