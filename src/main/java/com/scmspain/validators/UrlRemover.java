package com.scmspain.validators;

import java.util.List;

public class UrlRemover {

    public String getTextWithoutUrls(String text) {
        String urlRegex = "((https?)://+[\\w\\d:#@%/;$()~_?\\+-=\\\\\\.&]*)";
        String result = text.replaceAll(urlRegex, "");
        return result;
    }

    String removeUrls(String s, List<String> urls) {
        String result = s;
        for (String url: urls)
            result = result.replace(url, "");
        return result;
    }
}
