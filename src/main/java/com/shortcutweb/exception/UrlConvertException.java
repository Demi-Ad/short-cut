package com.shortcutweb.exception;

public class UrlConvertException extends RuntimeException {
    public UrlConvertException() {
    }

    public UrlConvertException(String message) {
        super(message);
    }

    public UrlConvertException(String message, Throwable cause) {
        super(message, cause);
    }

    public UrlConvertException(Throwable cause) {
        super(cause);
    }

    public UrlConvertException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
