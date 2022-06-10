package com.shortcutweb.res;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@ToString
public class ApiFailResponse {
    private String errorCode;
    private String message;
}
