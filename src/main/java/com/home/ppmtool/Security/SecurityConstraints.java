package com.home.ppmtool.Security;

public class SecurityConstraints {

    public static final String SIGN_UP_URLS  = "api/user/**";
    public static final String SECRET = "SecretKeyToGenJWTs";
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";
    public static final long EXPIRATION_TIME = 30_0000;
}
