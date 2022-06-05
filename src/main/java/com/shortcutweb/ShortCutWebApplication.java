package com.shortcutweb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableCaching
@EnableAsync
@EnableJpaAuditing
public class ShortCutWebApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShortCutWebApplication.class, args);
    }

}
