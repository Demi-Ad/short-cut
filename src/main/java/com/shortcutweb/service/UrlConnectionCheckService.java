package com.shortcutweb.service;

import com.shortcutweb.exception.UrlConvertException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
@Slf4j
public class UrlConnectionCheckService {

    private final RestTemplate restTemplate;

    public String connect(String url) {
        try {
            ResponseEntity<String> res = restTemplate.getForEntity(url, String.class);
            if (res.getStatusCode().is2xxSuccessful() && res.getBody() != null) {
                Document document = Jsoup.parse(res.getBody());
                return document.title();
            }
            throw new UrlConvertException("URL StatusCode Not Successfully");
        } catch (RestClientException e) {
            throw new UrlConvertException("RestTemplate Exception",e);
        }
    }
}
