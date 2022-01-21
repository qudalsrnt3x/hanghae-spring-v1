package com.hanghae.hanghae_spring_prac;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class HanghaeSpringPracApplication {

    public static void main(String[] args) {
        SpringApplication.run(HanghaeSpringPracApplication.class, args);
    }

}
