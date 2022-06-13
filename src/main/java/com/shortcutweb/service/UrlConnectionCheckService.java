package com.shortcutweb.service;

import com.shortcutweb.exception.UrlConvertException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class UrlConnectionCheckService {

    private final RestTemplate restTemplate;
    private HttpEntity<String> defaultRequestEntity;
    @PostConstruct
    public void init() {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(List.of(MediaType.TEXT_HTML));
        headers.setContentType(MediaType.TEXT_HTML);
        headers.add("user-agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/102.0.0.0 Safari/537.36");
        defaultRequestEntity = new HttpEntity<>("",headers);
    }

    public String connect(String url) {
        try {
            ResponseEntity<String> res = restTemplate.exchange(url, HttpMethod.GET , defaultRequestEntity,String.class);
            if (res.getStatusCode().is2xxSuccessful()) {
                if (res.getHeaders().getContentType().includes(MimeTypeUtils.TEXT_HTML)) {
                    Document document = Jsoup.parse(res.getBody());
                    return document.title();
                } else {
                    throw new UrlConvertException("This URL Is Not text/html");
                }
            }
            throw new UrlConvertException("URL StatusCode Not Successfully");
        } catch (RestClientException e) {
            throw new UrlConvertException("RestTemplate Exception",e);
        } catch (NullPointerException e) {
            throw new UrlConvertException("URL Parsing Error");
        }
    }
}
