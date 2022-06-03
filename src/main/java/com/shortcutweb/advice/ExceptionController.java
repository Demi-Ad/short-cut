package com.shortcutweb.advice;

import com.shortcutweb.exception.UrlConvertException;
import com.shortcutweb.res.RedirectConvertFailResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
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
        String code = bindingResult.getFieldError().getCode();
        String defaultMessage = bindingResult.getFieldError().getDefaultMessage();

        return ResponseEntity.badRequest().body(new RedirectConvertFailResponse(code,defaultMessage));
    }

    @ExceptionHandler(UrlConvertException.class)
    public ResponseEntity<RedirectConvertFailResponse> urlConvertFail(UrlConvertException e) {
        String message = e.getMessage();

        return ResponseEntity.badRequest().body(new RedirectConvertFailResponse("convert fail",message));
    }
}
