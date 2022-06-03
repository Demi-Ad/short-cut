package com.shortcutweb.repository;

import com.shortcutweb.entity.RedirectUrl;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RedirectUrlRepository extends JpaRepository<RedirectUrl, Long> {
    boolean existsByConvertUrlEquals(String convertUrl);

    Optional<RedirectUrl> findByConvertUrlEquals(String convertUrl);

}