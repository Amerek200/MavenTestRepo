package org.example.api;

public class AuthToken {
    private final String token;
    private final long expiryTime;

    public AuthToken(String token, long expiryTime) {
        this.token = token;
        this.expiryTime = expiryTime;
    }

    public String getToken() { return token; }
    public long getExpiryTime() { return expiryTime; }

    public boolean isExpired() {
        return System.currentTimeMillis() >= expiryTime;
    }

    public boolean isNearExpiry() {
        return (expiryTime - System.currentTimeMillis() < 10000);
    }
}
