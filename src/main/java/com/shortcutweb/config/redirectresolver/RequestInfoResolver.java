package com.shortcutweb.config.redirectresolver;

import com.shortcutweb.component.UserAgentParser;
import com.shortcutweb.event.RedirectEvent;
import com.shortcutweb.event.UserAgent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Stream;

@Component
@RequiredArgsConstructor
@Slf4j
public class RequestInfoResolver implements HandlerMethodArgumentResolver {

    private final UserAgentParser userAgentParser;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterAnnotation(RequestInfo.class) != null &&
                parameter.getParameterType().equals(RedirectEvent.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        HttpServletRequest req = webRequest.getNativeRequest(HttpServletRequest.class);
        if (req != null) {
            String referer = req.getHeader("referer");

            String ipAddress = Stream.of(req.getRemoteAddr(), req.getHeader("X-FORWARDED-FOR"))
                    .filter(Objects::nonNull)
                    .findFirst()
                    .orElse("");

            String ua = req.getHeader("user-agent");
            String requestURI = req.getRequestURI().replace("/","");
            log.info("URI = {}",requestURI);
            Map<UserAgent, String> userAgentParseMap = userAgentParser.parse(ua);

            return RedirectEvent.builder()
                    .requestURI(requestURI)
                    .referer(referer)
                    .clientIP(ipAddress)
                    .clientOS(userAgentParseMap.get(UserAgent.OS))
                    .clientDevice(userAgentParseMap.get(UserAgent.DEVICE))
                    .clientBrowser(userAgentParseMap.get(UserAgent.BROWSER))
                    .redirectDate(LocalDate.now())
                    .build();
        }
        return RedirectEvent.empty();
    }
}
