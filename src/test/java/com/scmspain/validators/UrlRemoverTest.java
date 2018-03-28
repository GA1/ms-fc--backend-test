package com.scmspain.validators;

import com.scmspain.validators.UrlRemover;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class UrlRemoverTest {

    private UrlRemover urlRemover;
    private String result;
    private String s, text, text1, text2;
    private List<String> input;
    private String url, url1, url2;

    @Before
    public void setUp() {
        urlRemover = new UrlRemover();
    }

    @Test
    public void shouldRemove1Url() {
        url = "http://foogle.co";
        input = Arrays.asList(url);
        text = "This is my url: ";
        s = text + url;
        assertEquals(text, urlRemover.removeUrls(this.s, input));
    }

    @Test
    public void shouldRemoveAllInstancesOfAUrl() {
        url1 = "http://example.com";
        input = Arrays.asList(url1);
        text1 = "This is my url1: ";
        text2 = " and this is my url1 again: ";
        s = text1 + url1 + text2 + url1;
        assertEquals(text1 + text2, urlRemover.removeUrls(this.s, input));
    }


    @Test
    public void shouldRemove2DifferentUrls() {
        url1 = "http://example.com";
        url2 = "https://noi.es";
        input = Arrays.asList(url1, url2);
        text1 = "This is my url1: ";
        text2 = " and this is my url2: ";
        s = text1 + url1 + text2 + url2;
        assertEquals(text1 + text2, urlRemover.removeUrls(this.s, input));
    }

    @Test
    public void shouldMatchAndRemoveUrls() {
        url1 = "http://example.com";
        url2 = "https://noi.es";
        text1 = "This is my url1: ";
        text2 = " and this is my url2: ";
        s = text1 + url1 + text2 + url2;
        assertEquals(text1 + text2, urlRemover.getTextWithoutUrls(s));
    }

}
