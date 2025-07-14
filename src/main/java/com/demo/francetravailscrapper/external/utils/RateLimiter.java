package com.demo.francetravailscrapper.external.utils;

public class RateLimiter {
    private final int maxRequestsPerSecond;
    private final long intervalBetweenRequestMilliseconds;
    private long lastResetTime;
    private int requestCount;

    // TODO if multi threaded use a Semaphore instead ?
    public RateLimiter(int maxRequestsPerSecond) {
        this.maxRequestsPerSecond = maxRequestsPerSecond;
        this.intervalBetweenRequestMilliseconds = 1000L;
        this.lastResetTime = System.currentTimeMillis();
        this.requestCount = 0;
    }

    public void acquire() {
        long now = System.currentTimeMillis();

        if (now - lastResetTime >= intervalBetweenRequestMilliseconds) {
            lastResetTime = now;
            requestCount = 0;
        }

        if (requestCount < maxRequestsPerSecond) {
            requestCount++;
            return;
        }

        try {
            Thread.sleep(intervalBetweenRequestMilliseconds);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Error while sleeping in rate limiter", e);
        }

        lastResetTime = System.currentTimeMillis();
        requestCount = 1;
    }
}
