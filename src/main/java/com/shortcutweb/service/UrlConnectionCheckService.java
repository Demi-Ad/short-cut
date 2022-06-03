package com.shortcutweb.service;

import com.shortcutweb.exception.UrlConvertException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
@Slf4j
public class UrlConnectionCheckService {

    private final RestTemplate restTemplate;

    public void connect(String url) {
        try {
            ResponseEntity<String> res = restTemplate.getForEntity(url, String.class);
            log.info("TEST = {}",res.getStatusCodeValue());
            if (res.getStatusCode().is2xxSuccessful()) {
                return;
            }
            throw new UrlConvertException("URL StatusCode Exception");
        } catch (RestClientException e) {
            throw new UrlConvertException("RestTemplate Exception",e);
        }
    }
}
