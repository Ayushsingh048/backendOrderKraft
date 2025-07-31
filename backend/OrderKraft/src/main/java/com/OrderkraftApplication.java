package com;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
@SpringBootApplication
@EnableCaching
public class OrderkraftApplication {
    public static void main(String[] args) {
        SpringApplication.run(OrderkraftApplication.class, args);
        System.out.println("OrderKraft Running fine");
    }
}
