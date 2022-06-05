package com.shortcutweb.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)

public class RedirectInformation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "url_id",foreignKey = @ForeignKey(name = "redirect"))
    private RedirectUrl redirectUrl;

    private String referer;

    @Column(name = "client_os")
    private String clientOS;

    private String clientDevice;

    private String clientBrowser;

    private String clientCountry;

    private LocalDate redirectDate;

    @Builder
    public RedirectInformation(RedirectUrl redirectUrl, String referer, String clientOS, String clientDevice, String clientBrowser, String clientCountry, LocalDate redirectDate) {
        this.redirectUrl = redirectUrl;
        this.referer = referer;
        this.clientOS = clientOS;
        this.clientDevice = clientDevice;
        this.clientBrowser = clientBrowser;
        this.clientCountry = clientCountry;
        this.redirectDate = redirectDate;
    }

}
