package com.standards.library.model;

public class Event<T> extends BaseInfo {

    public static final int CODE_PAY_PWD_SUCCESS = 0x1;

    public int code;

    public T data;

    public Event(T data) {
        this(0, data);
    }

    public Event(int code, T data) {
        this.code = code;
        this.data = data;
    }

    @Override
    public String toString() {
        return "code=" + code + ", data=" + data;
    }
}
