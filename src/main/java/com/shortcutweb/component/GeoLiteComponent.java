package com.shortcutweb.component;

import com.maxmind.geoip2.DatabaseReader;
import com.maxmind.geoip2.exception.GeoIp2Exception;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

@Component
@Slf4j
@RequiredArgsConstructor
public class GeoLiteComponent {
    private DatabaseReader reader;
    private final ResourceLoader resourceLoader;

    @PostConstruct
    void init() {
        try {
            ClassPathResource classPathResource = new ClassPathResource("static/geo/GeoLite2-City.mmdb");
            InputStream inputStream = classPathResource.getInputStream();
            File file = File.createTempFile("GeoLite2-City",".mmdb");
            Files.copy(inputStream, file.toPath(), StandardCopyOption.REPLACE_EXISTING);
            file.deleteOnExit();
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
