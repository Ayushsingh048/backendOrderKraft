package com;

import org.springframework.boot.SpringApplication;
<<<<<<< HEAD
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
=======
>>>>>>> a7e5ae85882b4f19ec34828851e39b37163cd463

import org.springframework.boot.autoconfigure.SpringBootApplication;
@SpringBootApplication
@EnableCaching
public class OrderkraftApplication {
    public static void main(String[] args) {
        SpringApplication.run(OrderkraftApplication.class, args);
        System.out.println("OrderKraft Running fine");
    }
}
