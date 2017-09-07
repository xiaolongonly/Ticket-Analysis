package com.standards.library.model;

import com.google.gson.annotations.SerializedName;
import com.standards.library.app.ReturnCodeConfig;

/**
 * <网络请求返回实体类>
 *
 * @author chenml@cncn.com
 * @data: 2015/11/16 20:31
 * @version: V1.0
 */
public class Response<T> {

    @SerializedName("showapi_res_code")
    public int rsCode;

    @SerializedName("showapi_res_error")
    public String rsMsg;

    @SerializedName("showapi_res_body")
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
