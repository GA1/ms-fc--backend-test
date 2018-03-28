package com.scmspain.validators;

import org.springframework.stereotype.Component;

@Component
public class TweetValidator {

    public boolean isValid(String publisher, String tweetText) {
        if (tweetText == null  || tweetText.length() == 0) {
            throw new IllegalArgumentException("Tweet should not be empty.");
        } else if (140 < tweetText.length()) {
            throw new IllegalArgumentException("Tweet should have at most 140 characters.");
        } else if (publisher == null || publisher.length() == 0) {
            throw new IllegalArgumentException("Publisher should not be empty.");
        } else
            return true;
    }
}
