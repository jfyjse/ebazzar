package com.sayone.ebazzar.config;

import com.sayone.ebazzar.entity.ProductEntity;
import com.sayone.ebazzar.repository.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.util.List;

@Configuration
public class ProductConfig {

    @Bean
    CommandLineRunner productAdd(ProductRepository productRepository){
       return args -> {
           ProductEntity productEntity = new ProductEntity("Soap", 150, "Lux Body Wash", 100);
           ProductEntity productEntity1 = new ProductEntity("Mobile", 50, "Redmi", 200);
           ProductEntity productEntity2 = new ProductEntity("Laptop", 30, "HP", 300);
           productRepository.saveAll(List.of(productEntity, productEntity1, productEntity2));
       };
    }
}
