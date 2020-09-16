package com.sai.oabs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@ServletComponentScan
@SpringBootApplication
public class OabsApplication {

    public static void main(String[] args) {
        SpringApplication.run(OabsApplication.class, args);
    }

}
