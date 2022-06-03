package com.shortcutweb.service;

import com.shortcutweb.exception.UrlNotFoundException;
import com.shortcutweb.repository.RedirectUrlRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class UrlRedirectService {

    private final RedirectUrlRepository repository;

    @Cacheable(value = "findUrl",key = "#param", condition = "#param != null")
    public String findOriginUrl(String param) {
        return repository.findByConvertUrlEquals(param)
                .orElseThrow(() -> new UrlNotFoundException("URL NOT FOUND"))
                .getOriginUrl();
    }
}
