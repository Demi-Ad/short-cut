package com.shortcutweb.res;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class RedirectConvertSuccessResponse {
    private String originUrl;
    private String convertUrl;
    private String documentTitle;
    private String createDate;
}
