package com.tinubu.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;

@Profile("test")
@Configuration
public class ClockConfig {

    @Bean
    public Clock fixedClock() {
        return Clock.fixed(Instant.parse("2024-05-18T03:00:00Z"), ZoneId.systemDefault());
    }

}
