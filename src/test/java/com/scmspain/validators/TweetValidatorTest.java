package com.scmspain.validators;

import com.scmspain.entities.Tweet;
import com.scmspain.validators.TweetValidator;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;

public class TweetValidatorTest {

    private TweetValidator tweetValidator;
    private UrlRemover urlRemover;

    private boolean result;

    @Before
    public void setUp() throws Exception {
        this.tweetValidator = new TweetValidator(new UrlRemover());
        this.urlRemover = new UrlRemover();
    }

    @Test
    public void validTweetShouldBeValid() throws Exception {
        result = tweetValidator.isValid(new Tweet("Guybrush Threepwood", "I am Guybrush Threepwood, mighty pirate."));
        assertTrue(result);
    }

    @Test
    public void shouldThrowAnExceptionWhenPublisherIsNull() throws Exception {
        assertTweetThrowsIllegalArgumentExceptionWithMessage(null, "Some tweet text.", "Publisher should not be empty.");
    }

    @Test
    public void shouldThrowAnExceptionWhenPublisherIsEmpty() throws Exception {
        assertTweetThrowsIllegalArgumentExceptionWithMessage("", "Some tweet text.", "Publisher should not be empty.");
    }

    @Test
    public void shouldConsiderTweetWith140characterMessageToBeValid() throws Exception {
        result = tweetValidator.isValid(new Tweet("Guybrush Threepwood", createTextWithNcharacters(140)));
        assertTrue(result);
    }

    @Test
    public void shouldConsiderTweetWith140characterPlusUrlMessageToBeValid() throws Exception {
        String someUrl = "https://www.example.com";
        String tweetText = createTextWithNcharacters(140) + someUrl;
        result = tweetValidator.isValid(new Tweet("Guybrush Threepwood", tweetText));
        assertTrue(result);
    }


    @Test
    public void shouldThrowAnExceptionWhenTweenLengthIs141() throws Exception {
        assertTweetThrowsIllegalArgumentExceptionWithMessage("talkativePublisher",
                createTextWithNcharacters(141), "Tweet should have at most 140 characters.");
    }

    private String createTextWithNcharacters(int N) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < N; i++)
            sb.append('.');
        return sb.toString();
    }

    @Test
    public void shouldThrowAnExceptionWhenTweetIsEmpty() throws Exception {
        assertTweetThrowsIllegalArgumentExceptionWithMessage("Pirate", "", "Tweet should not be empty.");
    }

    @Test
    public void shouldThrowAnExceptionWhenTweetIsNull() throws Exception {
        assertTweetThrowsIllegalArgumentExceptionWithMessage("Pirate", null, "Tweet should not be empty.");
    }

    private void assertTweetThrowsIllegalArgumentExceptionWithMessage(
            String publisher, String tweetText, String expectedMessage) {
        try {
            tweetValidator.isValid(new Tweet(publisher, tweetText));
            fail("");
        } catch(IllegalArgumentException e) {
            assertEquals(expectedMessage, e.getMessage());
        }
    }
}
