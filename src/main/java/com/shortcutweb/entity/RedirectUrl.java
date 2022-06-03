package com.shortcutweb.entity;

import com.shortcutweb.dto.RedirectUrlDto;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class RedirectUrl {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob
    private String originUrl;

    private String convertUrl;

    private String documentTitle;

    @CreatedDate
    private LocalDateTime createdDateTime;

    private String urlMakeIpAddress;

    @Builder
    public RedirectUrl(String originUrl, String convertUrl, String urlMakeIpAddress, String documentTitle) {
        this.originUrl = originUrl;
        this.convertUrl = convertUrl;
        this.urlMakeIpAddress = urlMakeIpAddress;
        this.documentTitle = documentTitle;
    }

    public RedirectUrlDto toDto() {
        return new RedirectUrlDto(id,originUrl,convertUrl,documentTitle);
    }
}
