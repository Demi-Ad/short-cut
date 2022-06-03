package com.shortcutweb.component;

import com.shortcutweb.message.RedirectMessage;
import com.shortcutweb.message.UserAgent;
import org.springframework.stereotype.Component;
import ua_parser.Client;
import ua_parser.Parser;

import java.util.HashMap;
import java.util.Map;

@Component
public class UserAgentParser {

    private final Parser parser = new Parser();

    public Map<UserAgent,String> parse(String userAgent) {
        Client client = parser.parse(userAgent);

        Map<UserAgent,String> parsedUserAgent = new HashMap<>();

        parsedUserAgent.put(UserAgent.BROWSER,client.userAgent.family);
        parsedUserAgent.put(UserAgent.OS,client.os.family);
        parsedUserAgent.put(UserAgent.DEVICE,client.device.family);

        return parsedUserAgent;
    }
}
