package com.scmspain.services;

import com.scmspain.entities.Tweet;
import com.scmspain.validators.TweetValidator;
import org.junit.Before;
import org.junit.Test;
import org.springframework.boot.actuate.metrics.writer.MetricWriter;

import javax.persistence.EntityManager;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

public class TweetServiceTest {
    private EntityManager entityManager;
    private MetricWriter metricWriter;
    private TweetValidator tweetValidator;
    private TweetService tweetService;

    @Before
    public void setUp() throws Exception {
        this.entityManager = mock(EntityManager.class);
        this.metricWriter = mock(MetricWriter.class);
        this.tweetValidator = mock(TweetValidator.class);

        this.tweetService = new TweetService(entityManager, metricWriter, tweetValidator);
    }

    @Test (expected = IllegalArgumentException.class)
    public void invalidTweetIsNotPersisted() throws Exception {
        when(tweetValidator.isValid(any(Tweet.class))).thenThrow(new IllegalArgumentException());
        tweetService.publishTweet(new Tweet(null, null));
        verify(entityManager, never()).persist(any(Tweet.class));
    }

    @Test
    public void validTweetIsPersisted() throws Exception {
        when(tweetValidator.isValid(any(Tweet.class))).thenReturn(true);
        tweetService.publishTweet(new Tweet("correct publisher", "Correct tweeet text"));
        verify(entityManager).persist(any(Tweet.class));
    }

}
