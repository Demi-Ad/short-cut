package com.shortcutweb.controller;

import com.shortcutweb.config.resolvers.Resolve;
import com.shortcutweb.dto.RedirectUrlDto;
import com.shortcutweb.dto.validator.SameOriginValidator;
import com.shortcutweb.res.RedirectConvertSuccessResponse;
import com.shortcutweb.service.UrlConnectionCheckService;
import com.shortcutweb.service.UrlConvertService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.time.LocalDate;

@RestController
@Transactional
@RequiredArgsConstructor
public class UrlConvertController {

    private final UrlConvertService urlConvertService;
    private final UrlConnectionCheckService connectionCheckService;
    private final Environment env;

    private final SameOriginValidator validator;


    @PostMapping(value = "/convert",produces = "application/json")
    public ResponseEntity<RedirectConvertSuccessResponse> convert(@Resolve String ip, @Valid @RequestBody RedirectUrlDto redirectUrlDto) {

        validator.validate(redirectUrlDto);

        String title = connectionCheckService.connect(redirectUrlDto.getOriginUrl());
        redirectUrlDto.setDocumentTitle(title);

        RedirectUrlDto save = urlConvertService.save(redirectUrlDto, ip);
        String domain = env.getProperty("domain.url");
        RedirectConvertSuccessResponse redirectConvertSuccessResponse = RedirectConvertSuccessResponse.builder()
                .convertUrl(domain + "/" + save.getConvertUrl())
                .documentTitle(save.getDocumentTitle())
                .originUrl(save.getOriginUrl())
                .createDate(LocalDate.now().toString())
                .build();
        return ResponseEntity.ok(redirectConvertSuccessResponse);
    }
}
