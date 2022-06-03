package com.shortcutweb.message;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@ToString
public class RedirectMessage {

    private String referer;
    private String clientOS;
    private String clientDevice;
    private String clientBrowser;
    private String clientIP;
    private LocalDateTime redirectDateTime;

    public static RedirectMessage empty() {
        return new RedirectMessage();
    }
}
