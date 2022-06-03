package com.shortcutweb.component;

import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class GenerateRandomUrl {

    public String generate() {
        Random random = new Random();
        int length = 6;

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int choice = random.nextInt(3);
            switch(choice) {
                case 0:
                    sb.append((char)(random.nextInt(25) +97));
                    break;
                case 1:
                    sb.append((char)(random.nextInt(25) +65));
                    break;
                case 2:
                    sb.append((char)(random.nextInt(10) +48));
                    break;
                default:
                    break;
            }
        }
        return sb.toString();
    }
}
