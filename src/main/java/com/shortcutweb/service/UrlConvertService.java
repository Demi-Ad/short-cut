package com.shortcutweb.service;

import com.shortcutweb.component.GenerateRandomUrl;
import com.shortcutweb.dto.RedirectUrlDto;
import com.shortcutweb.entity.RedirectUrl;
import com.shortcutweb.exception.UrlConvertException;
import com.shortcutweb.repository.RedirectUrlRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class UrlConvertService {
    private final RedirectUrlRepository repository;
    private final GenerateRandomUrl generateRandomUrl;

    public RedirectUrlDto save(RedirectUrlDto dto, String clientIp) {
        String convertUrl = makeConvertUrl();

        RedirectUrl redirectUrl = RedirectUrl.builder()
                .documentTitle(dto.getDocumentTitle())
                .originUrl(dto.getOriginUrl())
                .urlMakeIpAddress(clientIp)
                .convertUrl(convertUrl)
                .build();

        repository.save(redirectUrl);

        return redirectUrl.toDto();
    }


    private String makeConvertUrl() {
        while (true) {
            String generate = generateRandomUrl.generate();
            if (repository.existsByConvertUrlEquals(generate)) {
                continue;
            }
            return generate;
        }
    }
}
