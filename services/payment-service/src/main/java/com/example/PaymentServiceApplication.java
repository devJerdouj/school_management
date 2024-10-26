package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.ArrayList;

@SpringBootApplication
@EnableScheduling
public class PaymentServiceApplication{

    public static void main(String[] args) {
        SpringApplication.run(PaymentServiceApplication.class, args);
    }


}
