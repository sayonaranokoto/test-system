package common.bean.exception;


public class TestException extends Exception {
    private TestExceptionEnum exceptionEnum;

    public TestException(TestExceptionEnum testExceptionEnum) {
        this.exceptionEnum = testExceptionEnum;
    }
}
