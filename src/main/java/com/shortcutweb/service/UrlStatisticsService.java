package com.shortcutweb.service;

import com.shortcutweb.config.resolvers.statisticsresolver.StatisticsQuery;
import com.shortcutweb.entity.RedirectInformation;
import com.shortcutweb.entity.RedirectUrl;
import com.shortcutweb.exception.UrlNotFoundException;
import com.shortcutweb.repository.RedirectInformationRepository;
import com.shortcutweb.repository.RedirectUrlRepository;
import com.shortcutweb.res.UrlStatisticsData;
import com.shortcutweb.res.UrlStatisticsResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class UrlStatisticsService {
    private final RedirectInformationRepository informationRepository;
    private final RedirectUrlRepository urlRepository;



    public UrlStatisticsResponse Statistics(String param, StatisticsQuery query) {
        RedirectUrl redirectUrl = urlRepository.findByConvertUrlEquals(param).orElseThrow(UrlNotFoundException::new);
        LocalDate startDate;
        LocalDate endDate;
        List<RedirectInformation> list = new ArrayList<>();
        switch (query.getType()) {
            case BETWEEN:
                startDate = query.getStart();
                endDate = query.getEnd();
                list.addAll(informationRepository.searchByConvertUrl(redirectUrl.getId(),startDate,endDate));
                break;
            case CURRENT:
                startDate = LocalDate.now().minusMonths(1L);
                endDate = LocalDate.now();
                list.addAll(informationRepository.searchByConvertUrl(redirectUrl.getId(),startDate ,endDate));
                break;
            default:
                throw new RuntimeException();
        }


        UrlStatisticsData statisticsData = UrlStatisticsData.builder()
                .browser(group(list, RedirectInformation::getClientBrowser))
                .device(group(list, RedirectInformation::getClientDevice))
                .country(group(list, RedirectInformation::getClientCountry))
                .referer(refererToHost(list))
                .os(group(list, RedirectInformation::getClientOS))
                .date(group(list, redirectInformation -> redirectInformation.getRedirectDate().toString()))
                .build();


        return UrlStatisticsResponse.builder()
                .hitCount(((long) list.size()))
                .documentTitle(redirectUrl.getDocumentTitle())
                .originUrl(redirectUrl.getOriginUrl())
                .data(statisticsData)
                .startDate(startDate)
                .endDate(endDate)
                .build();

    }

    private Map<String,Long> group(List<RedirectInformation> list , Function<RedirectInformation,String> f) {
        return list.stream().collect(Collectors.groupingBy(f,Collectors.counting()));
    }

    private Map<String,Long> refererToHost(List<RedirectInformation> list) {
        return list.stream()
                .map(this::mappingHost)
                .filter(Objects::nonNull)
                .collect(Collectors.groupingBy(Object::toString, Collectors.counting()));
    }

    private String mappingHost(RedirectInformation redirectInformation) {
        try {
            log.info(redirectInformation.getReferer());
            String host = new URI(redirectInformation.getReferer()).getHost();
            if (host != null) {
                return host;
            } else {
                return redirectInformation.getReferer();
            }
        } catch (URISyntaxException | NullPointerException e) {
            return redirectInformation.getReferer();
        }
    }
}
