package com.standards.library.model;

import java.io.Serializable;

/**
 * <基础类>
 *
 * @author fuxj
 * @data: 15/11/10 下午7:32
 * @version: V1.0
 */
public abstract class BaseInfo implements Serializable {
    //实体类返回提示语
    private String successHintMsg;

    public void setSussceHintMsg(String msg) {
        this.successHintMsg = msg;
    }

    public String getSuccessHintMsg() {
        return successHintMsg;
    }
}
