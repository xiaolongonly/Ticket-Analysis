package com.standards.library.app;

public interface ReturnCode {

    //未知错误
    int LOCAL_UNKNOWN_ERROR = 0x10100;

    //无网络状态
    int LOCAL_NO_NETWORK = 0x10101;

    //超时
    int LOCAL_TIMEOUT_ERROR = 0x10104;

    //失败
    int LOCAL_ERROR_TYPE_ERROR = 0x10108;


    //成功
    int CODE_SUCCESS = 0;

    //失败
    int CODE_ERROR = -1;



    //数据为空
    int CODE_EMPTY = 1004;

    int CODE_TOKEN_INVALID = -106;
    /**
     * 登录过期
     */
    int CODE_LOGIN_INVALID = -1;

}
