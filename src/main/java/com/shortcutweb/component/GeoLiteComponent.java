package com.shortcutweb.component;

import com.maxmind.geoip2.DatabaseReader;
import com.maxmind.geoip2.exception.GeoIp2Exception;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.UnknownHostException;

@Component
@Slf4j
@RequiredArgsConstructor
public class GeoLiteComponent {
    private DatabaseReader reader;
    private final ResourceLoader resourceLoader;

    @PostConstruct
    void init() {
        try {
            File file = resourceLoader.getResource("classpath:static/geo/GeoLite2-City.mmdb").getFile();
            reader = new DatabaseReader.Builder(file).build();
        } catch (IOException e) {
            log.error("static/geo/GeoLite2-City.mmdb Not Found");
            log.error("require GeoLite2 Database!");
            throw new RuntimeException(e);
        }
    }

    public String country(String IpAddress) throws IOException, GeoIp2Exception {
        InetAddress inet = InetAddress.getByName(IpAddress);
        return reader.country(inet).getCountry().getName();
    }
}
