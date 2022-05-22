package common.bean.exception;

import lombok.Getter;

public class ExecuteException extends Exception {
    @Getter
    private ExecuteExceptionEnum exceptionEnum;

    public ExecuteException(ExecuteExceptionEnum testExceptionEnum) {
        this.exceptionEnum = testExceptionEnum;
    }
}
