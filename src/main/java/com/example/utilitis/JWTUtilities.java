package com.example.utilitis;

public class JWTUtilities {
    public static final String SECRET="Mysecret1234";
    public static final String PREFIX="Bearer ";
    public static final String AUTH_HEADER="Authorization";
    public static final long EXPIRE_ACCESS_TOKEN=2*60*1000;
    public static final long EXPIRE_REFRESH_TOKEN=24*60*60*1000;
}
