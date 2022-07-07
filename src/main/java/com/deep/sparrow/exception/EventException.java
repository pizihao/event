package com.deep.exception;

/**
 * 事件异常
 *
 * @author Create by liuwenhao on 2022/7/3 12:16
 */
public class EventException extends RuntimeException {
    private static final long serialVersionUID = 4220666905005394823L;

    public EventException() {
        super();
    }

    public EventException(Throwable throwable) {
        super(throwable);
    }

    public EventException(String message) {
        super(message);
    }

}