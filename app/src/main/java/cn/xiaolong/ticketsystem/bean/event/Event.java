package cn.xiaolong.ticketsystem.bean.event;


import com.standards.library.model.BaseInfo;

/**
 * <请描述这个类是干什么的>
 *
 * @data: 2016/7/21 15:19
 * @version: V1.0
 */
public class Event<T> extends BaseInfo {


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
