package com.shortcutweb.controller;

import com.shortcutweb.config.resolvers.Resolve;
import com.shortcutweb.dto.ConvertRequestDto;
import com.shortcutweb.dto.RedirectUrlDto;
import com.shortcutweb.dto.validator.SameOriginValidator;
import com.shortcutweb.res.RedirectConvertFailResponse;
import com.shortcutweb.res.RedirectConvertSuccessResponse;
import com.shortcutweb.service.UrlConnectionCheckService;
import com.shortcutweb.service.UrlConvertService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
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


    @Tag(name = "ConvertController")
    @Operation(summary = "Url Convert",
            description = "Input URL To short-cut URL Convert",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(schema = @Schema(implementation = ConvertRequestDto.class,name = "convertUrl",pattern = "URL"),
                            mediaType = "application/json",examples = @ExampleObject(
                                    name = "exampleUrl", value = "{\"originUrl\":\"https://www.google.com\"}" )
                    ), required = true))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    content = {@Content(mediaType = "application/json",schema = @Schema(implementation = RedirectConvertSuccessResponse.class))}
            ),
            @ApiResponse(responseCode = "400",
                    content = {@Content(mediaType = "application/json",schema = @Schema(implementation = RedirectConvertFailResponse.class))}
            )
    })

    @PostMapping(value = "/convert", produces = "application/json")
    public ResponseEntity<RedirectConvertSuccessResponse> convert(@Resolve String ip, @Valid @RequestBody ConvertRequestDto requestDto) {

        validator.validate(requestDto);

        String title = connectionCheckService.connect(requestDto.getOriginUrl());
        RedirectUrlDto redirectUrlDto = new RedirectUrlDto();
        redirectUrlDto.setOriginUrl(requestDto.getOriginUrl());
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
