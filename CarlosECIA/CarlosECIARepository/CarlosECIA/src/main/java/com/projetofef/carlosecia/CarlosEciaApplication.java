package com.projetofef.carlosecia;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@ComponentScan(basePackages = "com.projetofef")
@EntityScan(basePackages = {"com.projetofef.domains", "com.projetofef.domains.enums"})
@EnableJpaRepositories(basePackages = "com.projetofef.repositories")
@SpringBootApplication
public class CarlosEciaApplication {

    public static void main(String[] args) {
        SpringApplication.run(CarlosEciaApplication.class, args);
    }

}
