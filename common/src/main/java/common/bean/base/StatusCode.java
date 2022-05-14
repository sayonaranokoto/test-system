package common.bean.base;

public enum StatusCode {
    OK(0L, "OK"),
    UNKNOWN(-1L, "UNKNOWN"),
    ;

    private long code;
    private String msg;

    StatusCode(long code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public long getCode() {
        return code;
    }

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
