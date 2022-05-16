package platform.exception;


public class ValidateException extends Exception {
    private ValidateExceptionEnum exceptionEnum;

    public ValidateException(ValidateExceptionEnum validateExceptionEnum) {
        this.exceptionEnum = validateExceptionEnum;
    }
}
