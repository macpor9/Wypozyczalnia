package com.maciej.poreba.backend.authservice;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Random;

@Configuration
public class DefaultConfig {

    @Bean
    public WebClient webClient() {
        return WebClient.create();
    }

    @Bean
    public Random random(){return new Random();}

}
