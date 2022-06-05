package com.shortcutweb.component;

import com.maxmind.geoip2.exception.GeoIp2Exception;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ResourceLoader;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class GeoLiteComponentTest {

    @Autowired
    ResourceLoader resourceLoader;

    @Test
    void google_test() {
        GeoLiteComponent geoLiteComponent = new GeoLiteComponent(resourceLoader);
        geoLiteComponent.init();
        try {
            String country = geoLiteComponent.country("221.144.116.255");
            System.out.println(country);
            Assertions.assertThat(country).isNotEmpty();
        } catch (IOException e) {
            Assertions.fail("파일을 찾을 수 없다");
        } catch (GeoIp2Exception e) {
            Assertions.fail("GeoIp2Exception",e);
        }
    }

    @Test
    void loopBack_test() {
        GeoLiteComponent geoLiteComponent = new GeoLiteComponent(resourceLoader);
        geoLiteComponent.init();
        try {
            String country = geoLiteComponent.country("127.0.0.1");
            System.out.println(country);
            Assertions.assertThat(country).isNotEmpty();
        } catch (IOException e) {
            Assertions.fail("파일을 찾을 수 없다");
        } catch (GeoIp2Exception e) {
            Assertions.fail("GeoIp2Exception",e);
        }
    }

}