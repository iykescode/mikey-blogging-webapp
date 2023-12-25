package com.iykescode.blog.mikeybloggingwebapp.constants;

import lombok.AllArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class UrlPathConstant {

    private final Environment environment;

    /*
        Main URLS
     */
    public String MainPath() {
        return environment.getProperty("url.path");
    }
}
