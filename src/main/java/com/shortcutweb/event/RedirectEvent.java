package com.shortcutweb.event;

import lombok.*;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@ToString
public class RedirectEvent {
    private String requestURI;
    private String referer;
    private String clientOS;
    private String clientDevice;
    private String clientBrowser;
    private String clientIP;
    private LocalDate redirectDate;

    public static RedirectEvent empty() {
        return new RedirectEvent();
    }
}
