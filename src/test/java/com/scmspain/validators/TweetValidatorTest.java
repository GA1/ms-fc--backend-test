package com.scmspain.validators;

import com.scmspain.entities.Tweet;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class TweetValidatorTest {

    private TweetValidator tweetValidator;
    private UrlRemover urlRemover;
    private Tweet.TweetBuilder tb;
    private Tweet tweet;

    private boolean result;

    @Before
    public void setUp() throws Exception {
        this.tweetValidator = new TweetValidator(new UrlRemover());
        this.tb = new Tweet.TweetBuilder();
        this.urlRemover = new UrlRemover();
    }

    @Test
    public void validTweetShouldBeValid() throws Exception {
        tweet = tb.setPublisher("Guybrush Threepwood")
                .setTweet("I am Guybrush Threepwood, mighty pirate.")
                .build();
        result = tweetValidator.isValid(tweet);
        assertTrue(result);
    }

    @Test
    public void shouldThrowAnExceptionWhenPublisherIsNull() throws Exception {
        tweet = tb.setPublisher(null)
                .setTweet("Some tweet text.")
                .build();
        assertTweetThrowsIllegalArgumentExceptionWithMessage(tweet, "Publisher should not be empty.");
    }

    @Test
    public void shouldThrowAnExceptionWhenPublisherIsEmpty() throws Exception {
        tweet = tb.setPublisher("")
                .setTweet("Some tweet text.")
                .build();
        assertTweetThrowsIllegalArgumentExceptionWithMessage(tweet, "Publisher should not be empty.");
    }

    @Test
    public void shouldConsiderTweetWith140characterMessageToBeValid() throws Exception {
        tweet = tb.setPublisher("Guybrush Threepwood")
                .setTweet(createTextWithNcharacters(140))
                .build();
        result = tweetValidator.isValid(tweet);
        assertTrue(result);
    }

    @Test
    public void shouldConsiderTweetWith140characterPlusUrlMessageToBeValid() throws Exception {
        String someUrl = "https://www.example.com";
        String tweetText = createTextWithNcharacters(140) + someUrl;
        tweet = tb.setPublisher("Guybrush Threepwood")
                .setTweet(tweetText)
                .build();
        result = tweetValidator.isValid(tweet);
        assertTrue(result);
    }


    @Test
    public void shouldThrowAnExceptionWhenTweenLengthIs141() throws Exception {
        tweet = tb.setPublisher("talkativePublisher")
                .setTweet(createTextWithNcharacters(141))
                .build();
        assertTweetThrowsIllegalArgumentExceptionWithMessage(tweet, "Tweet should have at most 140 characters.");
    }

    private String createTextWithNcharacters(int N) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < N; i++)
            sb.append('.');
        return sb.toString();
    }

    @Test
    public void shouldThrowAnExceptionWhenTweetIsEmpty() throws Exception {
        tweet = tb.setPublisher("some publisher")
                .setTweet("")
                .build();
        assertTweetThrowsIllegalArgumentExceptionWithMessage(tweet, "Tweet should not be empty.");
    }

    @Test
    public void shouldThrowAnExceptionWhenTweetIsNull() throws Exception {
        tweet = tb.setPublisher("some publisher")
                .setTweet(null)
                .build();
        assertTweetThrowsIllegalArgumentExceptionWithMessage(tweet, "Tweet should not be empty.");
    }

    private void assertTweetThrowsIllegalArgumentExceptionWithMessage(Tweet tweet, String expectedMessage) {
        try {
            tweetValidator.isValid(tweet);
            fail("");
        } catch(IllegalArgumentException e) {
            assertEquals(expectedMessage, e.getMessage());
        }
    }
}
