package com.deep.event.event;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.ListUtil;

import java.lang.reflect.Type;
import java.util.Collection;
import java.util.Collections;

/**
 * 独占模式<br>
 * 仅将事件传递给首个监听器
 *
 * @author Create by liuwenhao on 2022/7/21 14:23
 */
public class ExclusiveListenerPattern implements ListenerPattern {

	@Override
	public <E> Collection<Listener<E, Object>> getListener(Type type, Collection<Listener<E, Object>> listeners) {
		if (listeners == null || listeners.isEmpty()) {
			return Collections.emptyList();
		}
		return ListUtil.of(CollUtil.getFirst(listeners));
	}
}
