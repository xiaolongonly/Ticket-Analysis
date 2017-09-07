package com.standards.library.model;

import com.standards.library.app.ReturnCodeConfig;
import com.google.gson.annotations.SerializedName;

public class Response<T> {

    @SerializedName("status")
    public int rsCode;

    @SerializedName("message")
    public String rsMsg;

    @SerializedName("object")
    public T data;

    public Response(int rsCode) {
        this.rsCode = rsCode;
    }

    public Response(int rsCode, String rsMsg) {
        this.rsCode = rsCode;
        this.rsMsg = rsMsg;
    }

    public boolean isSuccess() {
        return rsCode == ReturnCodeConfig.getInstance().successCode;
    }

    public boolean isEmptyCode() {
        return ReturnCodeConfig.getInstance().isEmptyCode(rsCode);
    }

}
