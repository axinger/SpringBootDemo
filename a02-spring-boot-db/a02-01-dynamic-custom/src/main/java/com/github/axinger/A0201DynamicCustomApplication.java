package com.github.axinger;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class A0201DynamicCustomApplication {
    public static void main(String[] args) {
        SpringApplication.run(A0201DynamicCustomApplication.class, args);
    }
}
