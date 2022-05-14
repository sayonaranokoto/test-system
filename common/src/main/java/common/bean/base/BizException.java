package common.bean.base;

import lombok.Getter;

public class BizException extends RuntimeException {
    @Getter
    private long code;

    @Getter
    private String msg;

    public BizException(long code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public BizException(StatusCode statusCode) {
        code = statusCode.getCode();
        msg = statusCode.getMsg();
    }
}
