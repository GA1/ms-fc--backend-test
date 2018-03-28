package com.scmspain.validators;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UrlRemover {

    public String getTextWithoutUrls(String text) {
        String urlRegex = "((https?)://+[\\w\\d:#@%/;$()~_?\\+-=\\\\\\.&]*)";
        String result = text.replaceAll(urlRegex, "");
        return result;
    }


    // Todo make sure hackers don't send messages with hundreds of short links
    String removeUrls(String s, List<String> urls) {
        String result = s;
        for (String url: urls)
            result = result.replace(url, "");
        return result;
    }
}
