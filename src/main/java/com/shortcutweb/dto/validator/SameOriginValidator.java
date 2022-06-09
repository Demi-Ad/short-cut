package com.shortcutweb.dto.validator;

import com.shortcutweb.dto.RedirectUrlDto;
import com.shortcutweb.exception.UrlConvertException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.net.URI;
import java.net.URISyntaxException;

@Component
public class SameOriginValidator {

    private final URI domain;

    @Autowired
    public SameOriginValidator(@Value("${domain.url}") String domain) {
        try {
            this.domain = new URI(domain);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    public void validate(RedirectUrlDto dto) {
        String originUrl = dto.getOriginUrl();
        try {
            URI uri = new URI(originUrl);
            if (uri.getHost().equals(domain.getHost())) {
                throw new UrlConvertException("Same Origin Domain");
            }
        } catch (URISyntaxException e) {
            throw new UrlConvertException(e.getMessage());
        }

    }
}
