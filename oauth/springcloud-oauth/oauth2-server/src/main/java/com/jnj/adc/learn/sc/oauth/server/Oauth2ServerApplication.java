package com.jnj.adc.learn.sc.oauth.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author : KG
 * description:
 * create date: 9:55 PM 2020/3/9
 * modified by:
 */
@SpringBootApplication
public class Oauth2ServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(Oauth2ServerApplication.class, args);
    }
}
