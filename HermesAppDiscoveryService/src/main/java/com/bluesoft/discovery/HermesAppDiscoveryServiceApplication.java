package com.bluesoft.discovery;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class HermesAppDiscoveryServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(HermesAppDiscoveryServiceApplication.class, args);
    }

}
