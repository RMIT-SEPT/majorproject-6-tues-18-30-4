package com.yuesun;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@ServletComponentScan
@EntityScan(basePackages = "com.yuesun.base")
@EnableJpaRepositories(basePackages = "com.yuesun.base")
public class OabsServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(OabsServerApplication.class, args);
    }
}
