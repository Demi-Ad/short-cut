package com.shortcutweb.controller;

import com.shortcutweb.config.messageresolver.RequestInfo;
import com.shortcutweb.exception.UrlNotFoundException;
import com.shortcutweb.message.RedirectMessage;
import com.shortcutweb.service.UrlRedirectService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.NoSuchElementException;

@Controller
@RequiredArgsConstructor
public class UrlRedirectController {

    private final UrlRedirectService redirectService;

    @GetMapping("/{param:^[a-zA-Z0-9]{6}$}")
    public String redirect(@PathVariable("param") String param, @RequestInfo RedirectMessage redirectMessage) {
        try {
            String originUrl = redirectService.findOriginUrl(param);
            return "redirect:" + originUrl;
        } catch (NoSuchElementException e) {
            throw new UrlNotFoundException("URL Not Found");
        }
    }
}
