package com.shortcutweb.controller;

import com.shortcutweb.config.ipresolver.IpResolve;
import com.shortcutweb.dto.RedirectUrlDto;
import com.shortcutweb.res.RedirectConvertSuccessResponse;
import com.shortcutweb.service.UrlConnectionCheckService;
import com.shortcutweb.service.UrlConvertService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.server.Cookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class UrlConvertController {

    private final UrlConvertService urlConvertService;
    private final UrlConnectionCheckService connectionCheckService;


    @PostMapping(value = "/convert",produces = "application/json")
    public ResponseEntity<RedirectConvertSuccessResponse> convert(@IpResolve String ip, @Valid @RequestBody RedirectUrlDto redirectUrlDto, HttpServletResponse response) {

        String title = connectionCheckService.connect(redirectUrlDto.getOriginUrl());
        redirectUrlDto.setDocumentTitle(title);

        RedirectUrlDto save = urlConvertService.save(redirectUrlDto, ip);
        RedirectConvertSuccessResponse redirectConvertSuccessResponse = new RedirectConvertSuccessResponse(save.getOriginUrl(), save.getConvertUrl(),save.getDocumentTitle());
        return ResponseEntity.ok(redirectConvertSuccessResponse);
    }
}
