package com.standards.library.model;

import java.io.Serializable;

public abstract class BaseInfo implements Serializable{
    //实体类返回提示语
    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
