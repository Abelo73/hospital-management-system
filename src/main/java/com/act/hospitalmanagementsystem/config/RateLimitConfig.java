package com.act.hospitalmanagementsystem.config;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Refill;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

@Configuration
public class RateLimitConfig {

    private Map<String, Bucket> cache = new HashMap<>();

    @Value("${rate.limit.default.capacity:100}")
    private int defaultCapacity;

    @Value("${rate.limit.default.refill-tokens:100}")
    private int defaultRefillTokens;

    @Value("${rate.limit.default.refill-duration:1m}")
    private String defaultRefillDuration;

    @Value("${rate.limit.strict.capacity:10}")
    private int strictCapacity;

    @Value("${rate.limit.strict.refill-tokens:10}")
    private int strictRefillTokens;

    @Value("${rate.limit.strict.refill-duration:1m}")
    private String strictRefillDuration;

    @Value("${rate.limit.lenient.capacity:200}")
    private int lenientCapacity;

    @Value("${rate.limit.lenient.refill-tokens:200}")
    private int lenientRefillTokens;

    @Value("${rate.limit.lenient.refill-duration:1m}")
    private String lenientRefillDuration;

    @Bean
    public Supplier<Bucket> bucketSupplier() {
        return () -> {
            Duration duration = parseDuration(defaultRefillDuration);
            Bandwidth limit = Bandwidth.classic(defaultCapacity, Refill.greedy(defaultRefillTokens, duration));
            return Bucket.builder()
                    .addLimit(limit)
                    .build();
        };
    }

    @Bean
    public Supplier<Bucket> strictBucketSupplier() {
        return () -> {
            Duration duration = parseDuration(strictRefillDuration);
            Bandwidth limit = Bandwidth.classic(strictCapacity, Refill.greedy(strictRefillTokens, duration));
            return Bucket.builder()
                    .addLimit(limit)
                    .build();
        };
    }

    @Bean
    public Supplier<Bucket> lenientBucketSupplier() {
        return () -> {
            Duration duration = parseDuration(lenientRefillDuration);
            Bandwidth limit = Bandwidth.classic(lenientCapacity, Refill.greedy(lenientRefillTokens, duration));
            return Bucket.builder()
                    .addLimit(limit)
                    .build();
        };
    }

    public Bucket resolveBucket(String key, Supplier<Bucket> supplier) {
        return cache.computeIfAbsent(key, k -> supplier.get());
    }

    private Duration parseDuration(String duration) {
        if (duration.endsWith("s")) {
            return Duration.ofSeconds(Long.parseLong(duration.substring(0, duration.length() - 1)));
        } else if (duration.endsWith("m")) {
            return Duration.ofMinutes(Long.parseLong(duration.substring(0, duration.length() - 1)));
        } else if (duration.endsWith("h")) {
            return Duration.ofHours(Long.parseLong(duration.substring(0, duration.length() - 1)));
        } else if (duration.endsWith("d")) {
            return Duration.ofDays(Long.parseLong(duration.substring(0, duration.length() - 1)));
        }
        return Duration.ofMinutes(1); // default
    }
}
