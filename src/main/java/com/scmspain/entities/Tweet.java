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

      We can assume that a message including a url is not longer than 1000. This should be rethought.
      It's more complicated than that on the real Twitter.
      Leaving this number forever as 1000 gives opportunities for hackers trying to execute
      time complexity attacks (see the `UrlRemover` class)
    */
    @Column(nullable = false, length = 1000)
    private String tweet;
    @Column (nullable=true)
    private Long pre2015MigrationStatus = 0L;
    @JsonIgnore
    @Column (nullable=true)
    private Date discardDate;

    public Tweet() {

    }

    public Tweet(String publisher, String tweet) {
        this.publisher = publisher;
        this.tweet = tweet;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getTweet() {
        return tweet;
    }

    public void setTweet(String tweet) {
        this.tweet = tweet;
    }

    public Long getPre2015MigrationStatus() {
        return pre2015MigrationStatus;
    }

    public void setPre2015MigrationStatus(Long pre2015MigrationStatus) {
        this.pre2015MigrationStatus = pre2015MigrationStatus;
    }

    public Date getDiscardDate() {
        return discardDate;
    }

    public void setDiscardDate(Date discardDate) {
        this.discardDate = discardDate;
    }

    @Override
    public String toString() {
        return "Tweet(id=" + id +
                ", publisher=" + publisher +
                ", tweet=" + tweet +
                ", discardDate=" + discardDate +
                ")";
    }

}
