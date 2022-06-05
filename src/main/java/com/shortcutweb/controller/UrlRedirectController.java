package com.shortcutweb.controller;

import com.shortcutweb.config.redirectresolver.RequestInfo;
import com.shortcutweb.exception.UrlNotFoundException;
import com.shortcutweb.event.RedirectEvent;
import com.shortcutweb.service.UrlRedirectService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.NoSuchElementException;

@Controller
@RequiredArgsConstructor
public class UrlRedirectController {

    private final UrlRedirectService redirectService;
    private final ApplicationEventPublisher eventPublisher;

    @GetMapping("/{param:^[a-zA-Z0-9]{6}$}")
    public String redirect(@PathVariable("param") String param, @RequestInfo RedirectEvent redirectEvent) {
        try {
            String originUrl = redirectService.findOriginUrl(param);
            eventPublisher.publishEvent(redirectEvent);

            return "redirect:" + originUrl;
        } catch (NoSuchElementException e) {
            throw new UrlNotFoundException("URL Not Found",e);
        }
    }
}
