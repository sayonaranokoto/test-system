package platform.exception;

import lombok.Getter;

public enum ValidateExceptionEnum {
    UNKNOWN(-1L, "未知问题"),
    EXPRESSION_PARAM_ERROR(11001L, "表达式错误"),
    EXPRESSION_NOT_MATCH(11002L, "结果不匹配"),
    ;

    @Getter
    private long code;

    @Getter
    private String desc;

    ValidateExceptionEnum(long code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
