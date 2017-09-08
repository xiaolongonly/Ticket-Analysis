package cn.xiaolong.ticketsystem.group;

import java.util.HashMap;

public class LoadEntity {
    public HashMap<Integer, LoadResource> codeEntity = new HashMap<>();

    public LoadEntity put(int code, LoadResource msg) {
        codeEntity.put(code, msg);
        return this;
    }

    public LoadResource getMessage(int code) {
        return codeEntity.get(code);
    }

}