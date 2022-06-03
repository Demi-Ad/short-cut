package com.shortcutweb.component;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GenerateRandomUrlTest {

    @Test
    void generate() {
        GenerateRandomUrl generateRandomUrl = new GenerateRandomUrl();

        String generate = generateRandomUrl.generate();

        Assertions.assertThat(generate.length()).isEqualTo(6);
    }
}