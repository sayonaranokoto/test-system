package common.bean.base;

import java.io.Serializable;

/**
 * @author void
 */
public class Result<T> implements Serializable {
    private static final long serialVersionUID = 8920558991997133230L;

    private static final long SUCCESS_CODE = 0L;

    /**
     * 是否成功
     */
    private Boolean success = true;

    /**
     * 错误码
     */
    private Long code;

    /**
     * 错误信息
     */
    private String msg;

    private T data;

    public Result<T> setSuccess(boolean success) {
        this.success = success;
        return this;
    }

    public boolean isSuccess() {
        return Boolean.TRUE.equals(success);
    }

    public Result<T> setCode(long code) {
        this.code = code;
        return this;
    }

    public long getCode() {
        return code;
    }

    public Result<T> setMsg(String msg) {
        this.msg = msg;
        return this;
    }

    public String getMsg() {
        return msg;
    }

    public Result<T> setData(T data) {
        this.data = data;
        return this;
    }

    public T getData() {
        return this.data;
    }

    public static <T> Result<T> of(T data) {
        Result<T> result = new Result<>();
        result.setCode(SUCCESS_CODE);
        result.setSuccess(true);
        result.setData(data);
        return result;
    }

    public static <T> Result<T> of() {
        Result<T> result = new Result<>();
        result.setCode(SUCCESS_CODE);
        result.setSuccess(true);
        return result;
    }

    public static <T> Result<T> fail(long code, String msg) {
        Result<T> result = new Result<>();
        result.setCode(code);
        result.setMsg(msg);
        result.setSuccess(false);
        return result;
    }

    public static <T> Result<T> fail(BaseStatusCode baseStatusCode) {
        return fail(baseStatusCode.getCode(), baseStatusCode.getMsg());
    }
}
