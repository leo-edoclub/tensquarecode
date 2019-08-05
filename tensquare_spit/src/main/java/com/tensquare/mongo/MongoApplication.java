package com.tensquare.mongo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import util.IdWorker;

@SpringBootApplication
public class MongoApplication {
    public static void main(String[] args) {
        SpringApplication.run(MongoApplication.class);
    }

    @Bean
    public IdWorker idWorker(){
        return new IdWorker(1, 1);
    }

}
