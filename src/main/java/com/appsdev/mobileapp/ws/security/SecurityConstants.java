package com.appsdev.mobileapp.ws.security;

import com.appsdev.mobileapp.ws.SpringApplicationContext;

public class SecurityConstants {
    public static final long EXPIRATION_TIME = 864000000; // 10 days
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";
    public static final String SIGN_UP_URL = "/users";
    public static final String VERFICATION_EMAIL_URL = "/users/email-verification";

//    public static final String TOKEN_SECRET = "jf9i4gu83nfl0";

    public static String getTokenSecret() {
        AppProperties appProperties = (AppProperties) SpringApplicationContext.getBean("appProperties");
        return appProperties.getTokenSecret();
    }
}

