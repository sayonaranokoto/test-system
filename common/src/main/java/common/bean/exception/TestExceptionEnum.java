package common.bean.exception;

import common.bean.base.BaseStatusCode;

public enum TestExceptionEnum implements BaseStatusCode {
    UNKNOWN(-1L, "未知问题"),

    //配置异常
    MISSING_CONFIG(101001L, "缺少配置参数"),

    //执行异常

    EXECUTE_ERROR(102001, "执行错误"),

    //校验异常
    EXPRESSION_PARAM_ERROR(11001L, "表达式错误"),
    EXPRESSION_NOT_MATCH(11002L, "结果不匹配"),

    ;

    private long code;

    private String msg;

    TestExceptionEnum(long code, String msg) {
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
