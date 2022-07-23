package com.deep.event.event;

/**
 * 不传播
 *
 * @author Create by liuwenhao on 2022/7/21 17:09
 */
public class NoSpreadPattern implements SpreadPattern {


	@Override
	public <E, R> void spread(EventContext context, R r, E e) {
		// 不传播，什么都不做
	}
}
