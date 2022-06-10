package com.shortcutweb.config.resolvers.statisticsresolver;

import com.shortcutweb.config.resolvers.Resolve;
import com.shortcutweb.exception.QueryParameterException;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class StatisticsQueryResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterAnnotation(Resolve.class) != null &&
                parameter.getParameterType().isAssignableFrom(StatisticsQuery.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        HttpServletRequest request = webRequest.getNativeRequest(HttpServletRequest.class);
        String typeParam = request.getParameter("type");

        StatisticsType type = StatisticsType.of(typeParam);
        StatisticsQuery.StatisticsQueryBuilder builder = StatisticsQuery.builder().type(type);
        try {
            if (type == StatisticsType.BETWEEN) {
                builder.start(LocalDate.parse(request.getParameter("start")))
                        .end(LocalDate.parse(request.getParameter("end")));
            }
            return builder.build();
        } catch (DateTimeParseException | NullPointerException e) {
            throw new QueryParameterException("Not Allowed Query Parameter",e);
        }

    }
}
