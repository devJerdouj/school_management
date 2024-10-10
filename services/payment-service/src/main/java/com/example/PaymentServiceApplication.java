package com.example;

import com.example.exception.ResourceNotFoundException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;

@SpringBootApplication
public class PaymentServiceApplication{

    public static void main(String[] args) {
        SpringApplication.run(PaymentServiceApplication.class, args);
    }


}
