package com.scmspain.entitities;

import com.scmspain.entities.Tweet;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;

public class TweetTest {

    private Tweet.TweetBuilder tweetBuilder;
    private Date discardDate = new Date();
    private Long id = 13l;
    private String publisher = "somePublisher";
    private String tweet = "someTweet";
    private Long pre2015MigrationStatus = 13l;
    private Tweet t;

    @Before
    public void setUp() throws Exception {

        t = new Tweet.TweetBuilder().
                setId(id).
                setPublisher(publisher).
                setTweet(tweet).
                setPre2015MigrationStatus(pre2015MigrationStatus).
                setDiscardDate(discardDate).build();
    }

    @Test
    public void invalidTweetIsNotPersisted() throws Exception {
        t = new Tweet.TweetBuilder().
                setId(id).
                setPublisher(publisher).
                setTweet(tweet).
                setPre2015MigrationStatus(pre2015MigrationStatus).
                setDiscardDate(discardDate).
                build();
        assertEquals(id, t.getId());
        assertEquals(publisher, t.getPublisher());
        assertEquals(tweet, t.getTweet());
        assertEquals(pre2015MigrationStatus, t.getPre2015MigrationStatus());
        assertEquals(discardDate, t.getDiscardDate());
    }


}
