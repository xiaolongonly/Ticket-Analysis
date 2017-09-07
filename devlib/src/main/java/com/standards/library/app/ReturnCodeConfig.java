package com.standards.library.app;


public class ReturnCodeConfig {
    /**
     * 空数据返回码
     */
    public static int CODE_EMPTY = -0x1000;
    public static int CODE_LOCAL_EMPTY = -0x1000;

    private static ReturnCodeConfig instance;

    public int successCode;

    private ReturnCodeConfig() {
        successCode = 1;
    }

    public static ReturnCodeConfig getInstance() {
        if (instance == null) {
            instance = new ReturnCodeConfig();
        }
        return instance;
    }

    /**
     * 设置成功的返回码, 用来判断
     *
     * @param successCode
     */
    public void initReturnCode(int successCode, int emptyCode) {
        this.successCode = successCode;
        ReturnCodeConfig.CODE_EMPTY = emptyCode;
    }

    /**
     * 是否数据位空
     *
     * @param code
     * @return
     */
    public boolean isEmptyCode(int code) {
        return code == CODE_EMPTY || code == CODE_LOCAL_EMPTY;
    }

}
