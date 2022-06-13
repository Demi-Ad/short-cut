package com.shortcutweb.controller.api;

import com.shortcutweb.config.resolvers.Resolve;
import com.shortcutweb.config.resolvers.statisticsresolver.StatisticsQuery;
import com.shortcutweb.exception.QueryParameterException;
import com.shortcutweb.exception.UrlNotFoundException;
import com.shortcutweb.res.ApiFailResponse;
import com.shortcutweb.res.UrlStatisticsResponse;
import com.shortcutweb.service.UrlStatisticsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
@Tag(name = "StatisticsController")
public class ApiUrlStatisticsController {

    private final UrlStatisticsService statisticsService;

    @Operation(
            description = "type(current | between), startDate(type:between!), endDate(type:between!)",
            responses = {
            @ApiResponse(responseCode = "200",content = @Content(mediaType = "application/json",schema = @Schema(implementation = UrlStatisticsResponse.class))),
            @ApiResponse(responseCode = "404",description = "URL NOT FOUND",content = @Content(schema = @Schema(implementation = Void.class))),
            @ApiResponse(responseCode = "400",content = @Content(schema = @Schema(implementation = ApiFailResponse.class)), description = "Query Parameter Not Accepted")
    })
    @GetMapping(value = "/{param:^[a-zA-Z0-9]{6}-stat$}",produces = "application/json")
    public ResponseEntity<UrlStatisticsResponse> getUrlStatistics(@PathVariable String param,
                                                                  @Resolve StatisticsQuery query) {
        String uri = param.replace("-stat", "");
        UrlStatisticsResponse statistics = statisticsService.Statistics(uri, query);
        return ResponseEntity.ok(statistics);
    }

    @ExceptionHandler(UrlNotFoundException.class)
    public ResponseEntity<?> urlNotFound(UrlNotFoundException e) {
        ApiFailResponse apiFailResponse = new ApiFailResponse("err-10",e.getMessage());
        log.warn("URL NOT FOUND = {}",apiFailResponse);
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(QueryParameterException.class)
    public ResponseEntity<ApiFailResponse> exceptionHandle(QueryParameterException e) {
        ApiFailResponse apiFailResponse = new ApiFailResponse("err-11",e.getMessage());
        log.warn("URL NOT FOUND = {}",apiFailResponse);
        return ResponseEntity.badRequest().body(apiFailResponse);
    }
}
