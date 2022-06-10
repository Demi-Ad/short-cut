package com.shortcutweb.config.resolvers.statisticsresolver;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@Builder
public class StatisticsQuery {
    private StatisticsType type;
    private LocalDate start;
    private LocalDate end;
}
