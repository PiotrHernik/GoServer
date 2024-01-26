package com.example.serwer;

import org.springframework.boot.SpringApplication;
//import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;


@Configuration
@ComponentScan (basePackages = "com.example.serwer")
public class ServerApplication
{


    public static void main(String[] args)
    {
        SpringApplication spr = new SpringApplication(ServerApplication.class);
        spr.run(args);

    }

}
