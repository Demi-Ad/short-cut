package com.shortcutweb.event;

import com.maxmind.geoip2.exception.GeoIp2Exception;
import com.shortcutweb.component.GeoLiteComponent;
import com.shortcutweb.entity.RedirectInformation;
import com.shortcutweb.entity.RedirectUrl;
import com.shortcutweb.repository.RedirectInformationRepository;
import com.shortcutweb.repository.RedirectUrlRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
@Slf4j
public class RedirectEventListener {

    private final RedirectUrlRepository urlRepository;
    private final RedirectInformationRepository informationRepository;
    private final GeoLiteComponent geoLiteComponent;

    @Async
    @EventListener(classes = {RedirectEvent.class})
    public void subscription(RedirectEvent event) {
        RedirectUrl redirectUrl = urlRepository.findByConvertUrlEquals(event.getRequestURI()).orElseThrow();
        String country;
        try {
            country = geoLiteComponent.country(event.getClientIP());
        } catch (IOException | GeoIp2Exception e) {
            country = "UNKNOWN";
        }

        RedirectInformation redirectInformation = RedirectInformation.builder()
                .redirectUrl(redirectUrl)
                .referer(event.getReferer())
                .clientOS(event.getClientOS())
                .clientBrowser(event.getClientBrowser())
                .clientDevice(event.getClientDevice())
                .clientCountry(country)
                .redirectDate(event.getRedirectDate())
                .build();

        informationRepository.save(redirectInformation);
    }
}
