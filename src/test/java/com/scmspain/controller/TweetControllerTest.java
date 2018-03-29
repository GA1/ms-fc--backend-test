package com.scmspain.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.scmspain.configuration.TestConfiguration;
import com.scmspain.entities.Tweet;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static java.lang.String.format;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestConfiguration.class)
public class TweetControllerTest {
    @Autowired
    private WebApplicationContext context;
    private MockMvc mockMvc;
    private MvcResult mvcResult;

    private String content;
    private List<Tweet> tweets;
    private long id;

    @Before
    public void setUp() {
        this.mockMvc = webAppContextSetup(this.context).build();
    }

    @Test
    @Transactional
    public void shouldReturn200WhenInsertingAValidTweet() throws Exception {
        mockMvc.perform(publishTweet("Prospect", "Breaking the law"))
            .andExpect(status().is(201));
    }

    @Test
    @Transactional
    public void shouldReturn201WhenInsertingTweetATweetWithMessageLowerThan140PlusALongUrl() throws Exception {
        mockMvc.perform(publishTweet("Schibsted Spain", "We are Schibsted Spain (look at our home page http://www.very-long-domain-which-belongs-to.schibsted.es/), we own Vibbo, InfoJobs, fotocasa, coches.net and milanuncios. Welcome!"))
                .andExpect(status().is(201));
    }

    @Test
    @Transactional
    public void shouldReturnAllPublishedTweets() throws Exception {
        mockMvc.perform(publishTweet("Yo", "How are you?"))
                .andExpect(status().is(201));

        MvcResult getResult = mockMvc.perform(get("/tweet"))
                .andExpect(status().is(200))
                .andReturn();

        String content = getResult.getResponse().getContentAsString();
        assertThat(new ObjectMapper().readValue(content, List.class).size()).isEqualTo(1);
    }

    @Test
    @Transactional
    public void shouldReturn404whenDiscardingNonExistentTweet() throws Exception {
        mockMvc.perform(discardTweet(1234)).andExpect(status().is(404));
    }

    @Test
    @Transactional
    public void shouldReturn200whenDiscardingExistentTweet() throws Exception {
        mockMvc.perform(publishTweet("A50", "B50"));
        tweets = ensureListOfTweetsReturns200AndGetTweets();
        id = tweets.get(0).getId();
        mockMvc.perform(discardTweet(id)).andExpect(status().is(200));

    }

    private List<Tweet> ensureListOfTweetsReturns200AndGetTweets() throws Exception {
        mvcResult = mockMvc.perform(get("/tweet"))
                .andExpect(status().is(200))
                .andReturn();
        content = mvcResult.getResponse().getContentAsString();
        List<Tweet> result = new ObjectMapper().readValue(content, new TypeReference<List<Tweet>>(){});
        return result;
    }

    private MockHttpServletRequestBuilder publishTweet(String publisher, String tweet) {
        return post("/tweet")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(format("{\"publisher\": \"%s\", \"tweet\": \"%s\"}", publisher, tweet));
    }

    private MockHttpServletRequestBuilder discardTweet(long id) {
        return post("/discarded")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(format("{\"tweet\": \"%s\"}", id));
    }

}
