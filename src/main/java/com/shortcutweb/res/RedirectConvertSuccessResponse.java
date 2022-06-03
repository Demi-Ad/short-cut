package com.shortcutweb.res;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RedirectConvertSuccessResponse {
    private String originUrl;
    private String convertUrl;

}
