package com.shortcutweb.controller;

import com.shortcutweb.config.resolvers.Resolve;
import com.shortcutweb.config.resolvers.statisticsresolver.StatisticsQuery;
import com.shortcutweb.res.UrlStatisticsResponse;
import com.shortcutweb.service.UrlStatisticsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RequiredArgsConstructor
@Slf4j
public class UrlStatisticsController {

    private final UrlStatisticsService statisticsService;

    @GetMapping(value = "/{param:^[a-zA-Z0-9]{6}-stat$}",produces = "text/html")
    public String getUrlStatistics(@PathVariable String param,
                                   @Resolve StatisticsQuery query, Model model) {
        String uri = param.replace("-stat", "");
        UrlStatisticsResponse statistics = statisticsService.Statistics(uri, query);
        model.addAttribute("statistics",statistics);
        model.addAttribute("uri",uri);
        return "page/dash";
    }
}
