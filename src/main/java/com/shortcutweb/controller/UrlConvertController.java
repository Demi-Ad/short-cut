package com.shortcutweb.controller;

import com.shortcutweb.config.ipresolver.IpResolve;
import com.shortcutweb.dto.RedirectUrlDto;
import com.shortcutweb.res.RedirectConvertSuccessResponse;
import com.shortcutweb.service.UrlConvertService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.support.MethodArgumentNotValidException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class UrlConvertController {

    private final UrlConvertService service;


    @PostMapping(value = "/convert",produces = "application/json")
    public ResponseEntity<RedirectConvertSuccessResponse> convert(@IpResolve String ip, @Valid @RequestBody RedirectUrlDto dto) {
        RedirectUrlDto save = service.save(dto, ip);
        RedirectConvertSuccessResponse redirectConvertSuccessResponse = new RedirectConvertSuccessResponse(save.getOriginUrl(), save.getConvertUrl());
        return ResponseEntity.ok(redirectConvertSuccessResponse);
    }
}
