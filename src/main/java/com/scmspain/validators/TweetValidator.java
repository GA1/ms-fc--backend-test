package com.scmspain.validators;

import com.scmspain.entities.Tweet;
import org.springframework.stereotype.Component;

@Component
public class TweetValidator {

    private UrlRemover urlRemover;

    public TweetValidator(UrlRemover urlRemover) {
        this.urlRemover = urlRemover;
    }

    public boolean isValid(Tweet t) {
        String tweetText = t.getTweet();
        if (tweetText == null  || tweetText.length() == 0)
            throw new IllegalArgumentException("Tweet should not be empty.");
        String textWithoutUrls = urlRemover.getTextWithoutUrls(tweetText);
        if (140 < textWithoutUrls.length())
            throw new IllegalArgumentException("Tweet should have at most 140 characters.");
        else if (t.getPublisher() == null || t.getPublisher().length() == 0)
            throw new IllegalArgumentException("Publisher should not be empty.");
        else
            return true;
    }
}
