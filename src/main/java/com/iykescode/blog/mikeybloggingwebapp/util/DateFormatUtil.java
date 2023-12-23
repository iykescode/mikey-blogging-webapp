package com.iykescode.blog.mikeybloggingwebapp.util;

import org.ocpsoft.prettytime.PrettyTime;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class DateFormatUtil {

    public static String getPrettyDate(LocalDateTime dateTime) {
        PrettyTime time = new PrettyTime();
        return dateTime != null ? time.format(dateTime) : "";
    }
}
