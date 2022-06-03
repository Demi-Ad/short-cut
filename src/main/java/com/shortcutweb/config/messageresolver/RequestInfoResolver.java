package com.shortcutweb.config.messageresolver;

import com.shortcutweb.component.UserAgentParser;
import com.shortcutweb.message.RedirectMessage;
import com.shortcutweb.message.UserAgent;
import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Stream;

@Component
@RequiredArgsConstructor
public class RequestInfoResolver implements HandlerMethodArgumentResolver {

    private final UserAgentParser userAgentParser;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterAnnotation(RequestInfo.class) != null &&
                parameter.getParameterType().equals(RedirectMessage.class);
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

            Map<UserAgent, String> userAgentParseMap = userAgentParser.parse(ua);

            return RedirectMessage.builder()
                    .referer(referer)
                    .clientIP(ipAddress)
                    .clientOS(userAgentParseMap.get(UserAgent.OS))
                    .clientDevice(userAgentParseMap.get(UserAgent.DEVICE))
                    .clientBrowser(userAgentParseMap.get(UserAgent.BROWSER))
                    .redirectDateTime(LocalDateTime.now())
                    .build();
        }
        return RedirectMessage.empty();
    }
}
