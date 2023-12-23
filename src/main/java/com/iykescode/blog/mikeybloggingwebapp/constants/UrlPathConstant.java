package com.iykescode.blog.mikeybloggingwebapp.constants;

import org.springframework.beans.factory.annotation.Value;

public class UrlPathConstant {

    @Value("${url.path}")
    private static String path;

    /*
        Main URLS
     */
    public static final String MainPath = path;
}
