package com.shortcutweb.dto;

import lombok.Data;
import org.hibernate.validator.constraints.URL;

@Data
public class ConvertRequestDto {
    @URL
    private String originUrl;
}
