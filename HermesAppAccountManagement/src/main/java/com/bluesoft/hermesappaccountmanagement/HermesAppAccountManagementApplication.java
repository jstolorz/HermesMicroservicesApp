package com.bluesoft.hermesappaccountmanagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class HermesAppAccountManagementApplication {

    public static void main(String[] args) {
        SpringApplication.run(HermesAppAccountManagementApplication.class, args);
    }

}
