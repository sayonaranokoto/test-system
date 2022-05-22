package common.bean.exception;

import common.bean.base.BaseStatusCode;

public enum ExecuteExceptionEnum implements BaseStatusCode {
    UNKNOWN(-1L, "未知异常"),

    EXECUTE_PARAM_ERROR(201001, "请求参数错误"),
    REQUEST_ERROR(201001, "接口调用错误"),
    ;

    private long code;

    private String msg;

    ExecuteExceptionEnum(long code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    @Override
    public long getCode() {
        return code;
    }

    @Override
    public String getMsg() {
        return msg;
    }
}
