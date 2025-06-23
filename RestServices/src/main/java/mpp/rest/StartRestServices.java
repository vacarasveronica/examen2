package mpp.rest;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"mpp.rest", "mpp.persistance", "mpp.model"})
public class StartRestServices {
    public static void main(String[] args) {
        SpringApplication.run(StartRestServices.class, args);
    }
}

