package com.sayone.ebazzar.security;

import com.sayone.ebazzar.SpringApplicationContext;

public class SecurityConstants {

    public static final long EXPIRATION_TIME = 10800000;
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";
    public static final String SIGN_UP_URL = "/users/registration";
    public static final long PASSWORD_RESET_EXPIRATION_TIME = 3600000;
    public static final String PASSWORD_RESET_REQUEST_URL = "/users/{email}/forgot-password";
    public static final String RESET_PASSWORD_URL = "/users/{email}/resetpassword";

    public static String getTokenSecret() {
        AppProperties appProperties = (AppProperties) SpringApplicationContext.getBean("AppProperties");
        return appProperties.getTokenSecret();
    }

}
