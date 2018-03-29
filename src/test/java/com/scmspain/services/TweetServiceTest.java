package com.scmspain.services;

import com.scmspain.entities.Tweet;
import com.scmspain.testutils.Utils;
import com.scmspain.validators.TweetValidator;
import javassist.NotFoundException;
import org.junit.Before;
import org.junit.Test;
import org.springframework.boot.actuate.metrics.writer.MetricWriter;

import javax.persistence.*;

import java.util.*;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

public class TweetServiceTest {
    private EntityManager entityManager;
    private MetricWriter metricWriter;
    private TweetValidator tweetValidator;
    private TweetService tweetService;

    private Tweet tweet;
    private long id;

    @Before
    public void setUp() throws Exception {
        this.entityManager = mock(EntityManager.class);
        this.metricWriter = mock(MetricWriter.class);
        this.tweetValidator = mock(TweetValidator.class);

        this.tweetService = new TweetService(entityManager, metricWriter, tweetValidator);
        this.id = 13l;
        this.tweet = new Tweet("some Publisher", "some tweet text");

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

    @Test (expected = NotFoundException.class)
    public void nonExistingTweetCannotBeDiscarded() throws Exception {
        when(entityManager.createQuery(anyString(), any(Class.class))).thenReturn(Utils.createDummyTypedQuery(Arrays.asList()));

        tweetService.discardTweet(13l);
    }

    @Test
    public void existingTweetCanBeDiscarded() throws Exception {
        when(entityManager.createQuery(anyString(), any(Class.class))).thenReturn(Utils.createDummyTypedQuery(Arrays.asList(id)));
        when(entityManager.find(Tweet.class, id)).thenReturn(tweet);

        tweetService.discardTweet(13l);

        verify(entityManager).persist(any(Tweet.class));
    }


}
