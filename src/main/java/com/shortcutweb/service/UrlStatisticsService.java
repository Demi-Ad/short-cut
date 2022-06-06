package com.shortcutweb.service;

import com.shortcutweb.config.resolvers.statisticsresolver.StatisticsQuery;
import com.shortcutweb.entity.RedirectInformation;
import com.shortcutweb.repository.RedirectInformationRepository;
import com.shortcutweb.res.UrlStatisticsData;
import com.shortcutweb.res.UrlStatisticsResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentMap;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UrlStatisticsService {
    private final RedirectInformationRepository repository;

    public UrlStatisticsResponse Statistics(String param, StatisticsQuery query) {
        List<RedirectInformation> list = new ArrayList<>();
        switch (query.getType()) {
            case SINGLE:
                list.addAll(repository.searchByConvertUrl(param,query.getSingleDate()));
                break;
            case BETWEEN:
                list.addAll(repository.searchByConvertUrl(param,query.getStartDate(),query.getEndDate()));
                break;
            case CURRENT:
                list.addAll(repository.searchByConvertUrl(param,LocalDate.now()));
                break;
        }


        UrlStatisticsData statisticsData = UrlStatisticsData.builder()
                .browserCount(group(list, RedirectInformation::getClientBrowser))
                .deviceCount(group(list, RedirectInformation::getClientDevice))
                .countryCount(group(list, RedirectInformation::getClientCountry))
                .refererCount(group(list, RedirectInformation::getReferer))
                .osCount(group(list, RedirectInformation::getClientOS))
                .dateCount(group(list, redirectInformation -> redirectInformation.getRedirectDate().toString()))
                .build();

        return UrlStatisticsResponse.builder()
                .hitCount(((long) list.size()))
                .data(statisticsData)
                .build();

    }

    private Map<String,Long> group(List<RedirectInformation> list , Function<RedirectInformation,String> f) {
        return list.stream().collect(Collectors.groupingBy(f,Collectors.counting()));
    }

}
