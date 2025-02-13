package com.neslihansezen.ipcs.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class PublicApiServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(PublicApiServiceApplication.class, args);
    }

}
