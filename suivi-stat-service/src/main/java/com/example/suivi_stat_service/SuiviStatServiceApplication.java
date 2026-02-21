package com.example.suivi_stat_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class SuiviStatServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(SuiviStatServiceApplication.class, args);
    }
}