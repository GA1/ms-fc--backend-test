package com.scmspain.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public final class Tweet {
    @Id
    @GeneratedValue
    private Long id;
    @Column(nullable = false)
    private String publisher;
    @Column(nullable = false, length = 140)
    private String tweet;
    @Column (nullable=true)
    private Long pre2015MigrationStatus = 0L;

    public Tweet() {}

    public Tweet(String publisher, String tweet) {
        this.publisher = publisher;
        this.tweet = tweet;
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

}
