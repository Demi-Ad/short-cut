package com.shortcutweb.config;

import com.shortcutweb.config.ipresolver.IpResolver;
import com.shortcutweb.config.messageresolver.RequestInfoResolver;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class MvcConfig implements WebMvcConfigurer {

    private final RequestInfoResolver requestInfoResolver;

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new IpResolver());
        resolvers.add(requestInfoResolver);
    }
}
