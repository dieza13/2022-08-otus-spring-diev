package ru.otus.projs.hw02.exception;

import ru.otus.projs.hw02.model.TestResult;

public class HandleTestResultException extends RuntimeException{

    private final TestResult testResult;

    public HandleTestResultException(TestResult result) {
        super("Error on handling test result");
        this.testResult = result;
    }

    public HandleTestResultException(TestResult result, Throwable cause) {
        super("Error on handling test result", cause);
        this.testResult = result;
    }

    public TestResult getTestResult() {
        return testResult;
    }
}
