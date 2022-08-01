package com.deep.event;

/**
 * <h2></h2>
 *
 * @author Create by liuwenhao on 2022/7/27 14:55
 */
public class UpListenerTest {

	public String UpListener1(EventUtilTest.UpEvent upEvent) {
		upEvent.setName("同步执行，普通监听器");
		return upEvent.getName();
	}

	public String UpListener2(EventUtilTest.UpEvent upEvent) {
		upEvent.setName("异步执行监听器");
		return upEvent.getName();
	}

	public String UpListener3(EventUtilTest.UpEvent upEvent) {
		upEvent.setName("优先级低的监听器");
		return upEvent.getName();
	}

	public String UpListener4(EventUtilTest.UpEvent upEvent) {
		upEvent.setName("有异常的监听器");
		throw new RuntimeException(upEvent.getName());
	}

	public String throwHandler4(Throwable throwable) {
		return throwable.getMessage();
	}
}
