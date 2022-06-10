package com.shortcutweb.exception;


public class QueryParameterException extends RuntimeException{
    public QueryParameterException() {
    }

    public QueryParameterException(String message) {
        super(message);
    }

    public QueryParameterException(String message, Throwable cause) {
        super(message, cause);
    }

    public QueryParameterException(Throwable cause) {
        super(cause);
    }

    public QueryParameterException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
