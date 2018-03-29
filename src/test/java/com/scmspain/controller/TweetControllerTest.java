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
import java.util.concurrent.TimeUnit;

import static java.lang.String.format;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
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
    private List<Tweet> tweets, discardedTweets;
    private long id, id1, id2;
    private String publisher, publisher1, publisher2;

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
    public void shouldCorrectlyListAPublishedTweet() throws Exception {
        mockMvc.perform(publishTweet("A20", "B20")).andExpect(status().is(201));
        tweets = ensureListOfTweetsReturns200AndGetTweets();
        assertEquals(1, tweets.size());
    }

    @Test
    @Transactional
    public void shouldCorrectlyList2PublishedTweets() throws Exception {
        mockMvc.perform(publishTweet("A30", "B30")).andExpect(status().is(201));
        mockMvc.perform(publishTweet("A40", "B40")).andExpect(status().is(201));
        tweets = ensureListOfTweetsReturns200AndGetTweets();
        assertEquals(2, tweets.size());
    }

    @Test
    @Transactional
    public void shouldReturn201WhenInsertingTweetATweetWithMessageLowerThan140PlusALongUrl() throws Exception {
        mockMvc.perform(publishTweet("Schibsted Spain", "We are Schibsted Spain (look at our home page http://www.very-long-domain-which-belongs-to.schibsted.es/), we own Vibbo, InfoJobs, fotocasa, coches.net and milanuncios. Welcome!"))
                .andExpect(status().is(201));
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

    @Test
    @Transactional
    public void shouldNotReturnDiscardedTweets() throws Exception {
        mockMvc.perform(publishTweet("A60", "B60"))
                .andExpect(status().is(201));
        mockMvc.perform(publishTweet("A70", "B70"))
                .andExpect(status().is(201));
        tweets = ensureListOfTweetsReturns200AndGetTweets();
        id1 = tweets.get(0).getId();
        id2 = tweets.get(1).getId();
        publisher = tweets.get(1).getPublisher();
        mockMvc.perform(discardTweet(id1)).andExpect(status().is(200));

        tweets = ensureListOfTweetsReturns200AndGetTweets();
        id = tweets.get(0).getId();
        assertThat(tweets.size()).isEqualTo(1);
        assertThat(tweets.get(0).getId()).isEqualTo(id2);
        assertThat(tweets.get(0).getPublisher()).isEqualTo(publisher);
    }

    @Test
    @Transactional
    public void listOfDiscardedTweetsShouldContainNoTweetsIfNoTweetWasDiscarded() throws Exception {
        discardedTweets = ensureListOfDiscardedTweetsReturns200AndGetTweets();
        assertThat(discardedTweets.size()).isEqualTo(0);
    }

    @Test
    @Transactional
    public void listOfDiscardedTweetsShouldContainOnlyDiscardedTweets() throws Exception {
        mockMvc.perform(publishTweet("A80", "B80"))
                .andExpect(status().is(201));
        mockMvc.perform(publishTweet("A90", "B90"))
                .andExpect(status().is(201));
        tweets = ensureListOfTweetsReturns200AndGetTweets();
        id1 = tweets.get(0).getId();
        id2 = tweets.get(1).getId();
        publisher = tweets.get(0).getPublisher();
        mockMvc.perform(discardTweet(id1)).andExpect(status().is(200));

        discardedTweets = ensureListOfDiscardedTweetsReturns200AndGetTweets();
        assertThat(discardedTweets.size()).isEqualTo(1);
        assertThat(discardedTweets.get(0).getPublisher()).isEqualTo(publisher);
    }

    @Test
    @Transactional
    public void listOfDiscardedTweetsShouldContainDiscardedTweetsSortedByDateOfDiscarding() throws Exception {
        mockMvc.perform(publishTweet("A80", "B80"))
                .andExpect(status().is(201));
        mockMvc.perform(publishTweet("A90", "B90"))
                .andExpect(status().is(201));
        tweets = ensureListOfTweetsReturns200AndGetTweets();
        id1 = tweets.get(0).getId();
        id2 = tweets.get(1).getId();
        publisher1 = tweets.get(0).getPublisher();
        publisher2 = tweets.get(1).getPublisher();
        mockMvc.perform(discardTweet(id1)).andExpect(status().is(200));
        TimeUnit.SECONDS.sleep(1);
        mockMvc.perform(discardTweet(id2)).andExpect(status().is(200));

        discardedTweets = ensureListOfDiscardedTweetsReturns200AndGetTweets();
        assertThat(discardedTweets.size()).isEqualTo(2);
        assertThat(discardedTweets.get(0).getPublisher()).isEqualTo(publisher2);
        assertThat(discardedTweets.get(1).getPublisher()).isEqualTo(publisher1);
    }

    private List<Tweet> ensureListOfDiscardedTweetsReturns200AndGetTweets() throws Exception {
        mvcResult = mockMvc.perform(get("/discarded"))
                .andExpect(status().is(200))
                .andReturn();
        content = mvcResult.getResponse().getContentAsString();
        List<Tweet> result = new ObjectMapper().readValue(content, new TypeReference<List<Tweet>>(){});
        return result;
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
        return postContentToPath(format("{\"publisher\": \"%s\", \"tweet\": \"%s\"}", publisher, tweet), "/tweet");
    }

    private MockHttpServletRequestBuilder discardTweet(long id) {
        return postContentToPath(format("{\"tweet\": \"%s\"}", id), "/discarded");
    }

    private MockHttpServletRequestBuilder postContentToPath(String content, String path) {
        return post(path)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(content);
    }



}
