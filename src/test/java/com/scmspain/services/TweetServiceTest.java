package com.scmspain.services;

import com.scmspain.entities.Tweet;
import org.junit.Before;
import org.junit.Test;
import org.springframework.boot.actuate.metrics.writer.MetricWriter;

import javax.persistence.EntityManager;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class TweetServiceTest {
    private EntityManager entityManager;
    private MetricWriter metricWriter;
    private TweetService tweetService;

    @Before
    public void setUp() throws Exception {
        this.entityManager = mock(EntityManager.class);
        this.metricWriter = mock(MetricWriter.class);

        this.tweetService = new TweetService(entityManager, metricWriter);
    }

    @Test
    public void shouldInsertANewTweet() throws Exception {
        tweetService.publishTweet("Guybrush Threepwood", "I am Guybrush Threepwood, mighty pirate.");

        verify(entityManager).persist(any(Tweet.class));
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowAnExceptionWhenTweetLengthIsInvalid() throws Exception {
        tweetService.publishTweet("Pirate", "LeChuck? He's the guy that went to the Governor's for dinner and never wanted to leave. He fell for her in a big way, but she told him to drop dead. So he did. Then things really got ugly.");
    }

    @Test
    public void shouldThrowAnExceptionWhenPublisherIsNull() throws Exception {
        assertTweetWithThrowsIllegalArgumentExceptionWithTheMessage(null, "Some tweet text.", "Publisher should not be empty.");
    }

    @Test
    public void shouldThrowAnExceptionWhenPublisherIsEmpty() throws Exception {
        assertTweetWithThrowsIllegalArgumentExceptionWithTheMessage("", "Some tweet text.", "Publisher should not be empty.");
    }

    @Test
    public void shouldThrowAnExceptionWhenTweenLengthIs141() throws Exception {
        assertTweetWithThrowsIllegalArgumentExceptionWithTheMessage("talkativePublisher",
                createMessageWithNcharacters(141), "Tweet should have at most 140 characters.");
    }

    private String createMessageWithNcharacters(int N) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < N; i++)
            sb.append('.');
        return sb.toString();
    }

    @Test
    public void shouldThrowAnExceptionWhenTweetIsEmpty() throws Exception {
        assertTweetWithThrowsIllegalArgumentExceptionWithTheMessage("Pirate", "", "Tweet should not be empty.");
    }

    @Test
    public void shouldThrowAnExceptionWhenTweetIsNull() throws Exception {
        assertTweetWithThrowsIllegalArgumentExceptionWithTheMessage("Pirate", null, "Tweet should not be empty.");
    }

    private void assertTweetWithThrowsIllegalArgumentExceptionWithTheMessage(
            String publisher, String tweetText, String expectedMessage) {
        try {
            tweetService.publishTweet(publisher, tweetText);
            fail("");
        } catch(IllegalArgumentException e) {
            assertEquals(expectedMessage, e.getMessage());
        }
    }
}
