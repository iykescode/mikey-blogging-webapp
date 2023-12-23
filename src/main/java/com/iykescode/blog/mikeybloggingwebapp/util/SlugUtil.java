package com.iykescode.blog.mikeybloggingwebapp.util;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriUtils;

@Component
@RequiredArgsConstructor
public class SlugUtil {

    private final StringUtil stringUtil;

    public String createSlug(String text) {
        String randomString = null;

        // Convert to lowercase
        String lowercaseText = text.toLowerCase();
        // Remove special characters and replace spaces with dashes
        String slug = lowercaseText.replaceAll("[^a-zA-Z0-9\\s]", "").replaceAll("\\s", "-");
        // Remove consecutive dashes
        slug = slug.replaceAll("-{2,}", "-");
        // Trim leading and trailing dashes
        slug = slug.replaceAll("^-|-$", "");
        // Encode the slug
        slug = UriUtils.encode(slug, "UTF-8");

        for (int i = 0; i < 10; i++) {
            randomString = stringUtil.generateRandomString(10);
        }

        return stringUtil.joinTwoStringsByDash(slug, randomString);
    }
}
