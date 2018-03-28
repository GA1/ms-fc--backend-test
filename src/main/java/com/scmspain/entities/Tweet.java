package com.scmspain.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;

@Entity
public final class Tweet {
    @Id
    @GeneratedValue
    private Long id;
    @Column(nullable = false)
    private String publisher;
    /**
      Todo for future versions define the limits for tweet's length and number of urls it can have

      We can assume that a message including a url is not longer than 1000. This should be rethought though.
      On the real Twitter, it's more complicated than that .
      Leaving this number forever as 1000 gives opportunities for hackers trying to execute
      time complexity attacks (see the `UrlRemover` class)
    */
    @Column(nullable = false, length = 1000)
    private String tweet;
    @Column (nullable=true)
    private Long pre2015MigrationStatus = 0L;
    @JsonIgnore
    @Column (nullable=true)
    private Date discardDate;;

    public Tweet() {}

    private Tweet(Long id, String publisher, String tweet, Long pre2015MigrationStatus, Date discardDate) {
        this.id = id;
        this.publisher = publisher;
        this.tweet = tweet;
        this.pre2015MigrationStatus = pre2015MigrationStatus;
        this.discardDate = discardDate;
    }

    public Long getId() {
        return id;
    }

    public String getPublisher() {
        return publisher;
    }

    public String getTweet() {
        return tweet;
    }

    public Long getPre2015MigrationStatus() {
        return pre2015MigrationStatus;
    }

    public Date getDiscardDate() {
        return discardDate;
    }

    @Override
    public String toString() {
        return "Tweet(id=" + id +
                ", publisher=" + publisher +
                ", tweet=" + tweet +
                ")";
    }

    public static class TweetBuilder {
        Long id;
        String publisher;
        String tweet;
        Long pre2015MigrationStatus;
        Date discardDate;

        public TweetBuilder() {}

        public TweetBuilder fromTweet(Tweet t) {
            this.id = t.getId();
            this.publisher = t.getPublisher();
            this.tweet = t.getTweet();
            this.pre2015MigrationStatus = t.getPre2015MigrationStatus();
            this.discardDate = t.getDiscardDate();
            return this;
        }

        public TweetBuilder setId(Long id) {
            this.id = id;
            return this;
        }

        public TweetBuilder setPublisher(String publisher) {
            this.publisher = publisher;
            return this;
        }

        public TweetBuilder setTweet(String tweet) {
            this.tweet = tweet;
            return this;
        }

        public TweetBuilder setPre2015MigrationStatus(Long pre2015MigrationStatus) {
            this.pre2015MigrationStatus = pre2015MigrationStatus;
            return this;
        }

        public TweetBuilder setDiscardDate(Date discardDate) {
            this.discardDate = discardDate;
            return this;
        }

        public Tweet build() {
            return new Tweet(id, publisher, tweet, pre2015MigrationStatus, discardDate);
        }
    }
}
