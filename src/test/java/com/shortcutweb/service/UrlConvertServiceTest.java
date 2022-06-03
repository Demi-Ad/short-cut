package com.shortcutweb.service;

import com.shortcutweb.dto.RedirectUrlDto;
import com.shortcutweb.entity.RedirectUrl;
import com.shortcutweb.repository.RedirectUrlRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

@SpringBootTest
@Transactional
class UrlConvertServiceTest {

    @Autowired
    EntityManager em;

    @Autowired
    UrlConvertService service;

    @Autowired
    RedirectUrlRepository repository;

    @Test
    void saveTest() {
        RedirectUrlDto dto = new RedirectUrlDto();
        dto.setOriginUrl("http://127.0.0.1");
        Long save = service.save(dto,"127.0.0").getId();

        em.flush();
        em.clear();

        RedirectUrl redirectUrl = repository.findById(save).orElseThrow();

        Assertions.assertThat(redirectUrl.getConvertUrl()).isNotEmpty();
        Assertions.assertThat(redirectUrl.getConvertUrl().length()).isEqualTo(6);
        Assertions.assertThat(redirectUrl.getUrlMakeIpAddress()).isEqualTo("127.0.0.1");
        Assertions.assertThat(redirectUrl.getOriginUrl()).isEqualTo("http://127.0.0.1");

    }
}