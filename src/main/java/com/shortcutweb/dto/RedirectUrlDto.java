package com.shortcutweb.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.URL;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RedirectUrlDto {
    private Long id;
    @URL(message = "URL 형식이 올바르지 않습니다")
    private String originUrl;
    private String convertUrl;

}
