package com.shortcutweb.res;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RedirectConvertFailResponse {
    private String errorCode;
    private String message;
}
