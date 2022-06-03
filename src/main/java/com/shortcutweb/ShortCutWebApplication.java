package com.shortcutweb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class ShortCutWebApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShortCutWebApplication.class, args);
    }

}
