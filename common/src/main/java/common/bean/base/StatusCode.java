package common.bean.base;

public enum StatusCode implements BaseStatusCode {
    OK(0L, "OK"),
    UNKNOWN(-1L, "UNKNOWN"),
    ;

    private long code;
    private String msg;

    StatusCode(long code, String msg) {
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

    public StatusCode getByCode(long code) {
        for (StatusCode statusCode: StatusCode.values()) {
            if (statusCode.getCode() == code) {
                return statusCode;
            }
        }
        return UNKNOWN;
    }
}
