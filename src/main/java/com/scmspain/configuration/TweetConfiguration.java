package com.scmspain.configuration;

import com.scmspain.controller.TweetController;
import com.scmspain.services.TweetService;
import com.scmspain.validators.TweetValidator;
import org.springframework.boot.actuate.metrics.writer.MetricWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManager;

@Configuration
public class TweetConfiguration {

    @Bean
    public TweetValidator getTweetValidator() {
        return new TweetValidator();
    }

    @Bean
    public TweetService getTweetService(EntityManager entityManager, MetricWriter metricWriter, TweetValidator tweetValidator) {
        return new TweetService(entityManager, metricWriter, tweetValidator);
    }

    @Bean
    public TweetController getTweetConfiguration(TweetService tweetService) {
        return new TweetController(tweetService);
    }
}
