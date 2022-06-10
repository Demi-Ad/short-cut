package com.shortcutweb.advice;

import com.shortcutweb.exception.UrlConvertException;
import com.shortcutweb.res.RedirectConvertFailResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConversionException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class ExceptionController {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<RedirectConvertFailResponse> methodValidateFail(MethodArgumentNotValidException e) {
        BindingResult bindingResult = e.getBindingResult();
        String defaultMessage = bindingResult.getFieldError().getField();

        return ResponseEntity.badRequest().body(new RedirectConvertFailResponse("err-01", defaultMessage + " Validation Fail"));
    }

    @ExceptionHandler(UrlConvertException.class)
    public ResponseEntity<RedirectConvertFailResponse> urlConvertFail(UrlConvertException e) {
        String message = e.getMessage();

        return ResponseEntity.badRequest().body(new RedirectConvertFailResponse("err-02", message));
    }

    @ExceptionHandler(HttpMessageConversionException.class)
    public ResponseEntity<RedirectConvertFailResponse> conversionFail(HttpMessageConversionException e) {
        String message = e.getMessage();
        return ResponseEntity.badRequest().body(new RedirectConvertFailResponse("err-03", message));
    }
}
