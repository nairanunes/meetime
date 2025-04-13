package br.com.meetime.integration.service;

import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class RateLimitService {

    private static final int MAX_REQUESTS_PER_MINUTE = 60;
    private final AtomicInteger requestCount = new AtomicInteger(0);
    private Instant lastRequestTime = Instant.now();


    public boolean checkAndUpdateRateLimit() {
        Instant currentTime = Instant.now();


        if (currentTime.minusSeconds(60).isAfter(lastRequestTime)) {
            requestCount.set(0);
            lastRequestTime = currentTime;
        }


        if (requestCount.incrementAndGet() > MAX_REQUESTS_PER_MINUTE) {
            return false;
        }

        return true;
    }
}
