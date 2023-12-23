package com.iykescode.blog.mikeybloggingwebapp.util;

import org.springframework.test.web.servlet.ResultMatcher;

public class RedirectMatchers {

    public static ResultMatcher redirectedUrlIsOneOf(final String... expectedUrls) {
        return response -> {
            String actualUrl = response.getResponse().getHeader("Location");
            for (String expectedUrl : expectedUrls) {
                if (expectedUrl.equals(actualUrl)) {
                    return; // Match found, test passes
                }
            }
            throw new AssertionError("Redirected URL did not match any of the expected URLs");
        };
    }
}
