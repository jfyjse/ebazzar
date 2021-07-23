package com.sayone.ebazzar.config;


import com.sayone.ebazzar.entity.UserEntity;
import com.sayone.ebazzar.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;



@Configuration
public class UserConfig {

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Bean
    CommandLineRunner userCommandLineRunner(UserRepository userRepository){
        return args -> {
            UserEntity user1=new UserEntity("Teena","Alex","teena@gmail.com","123",62525,"customer");
            UserEntity user2=new UserEntity("Miya","Mathew","miya@gmail.com","123",62525,"customer");
            UserEntity user3=new UserEntity("Rahul","Thomas","rahul@gmail.com","123",62525,"customer");

            user1.setEncryptedPassword(bCryptPasswordEncoder.encode(user1.getPassword()));
            user2.setEncryptedPassword(bCryptPasswordEncoder.encode(user2.getPassword()));
            user3.setEncryptedPassword(bCryptPasswordEncoder.encode(user3.getPassword()));


            userRepository.saveAll(List.of(user1,user2,user3));
        };
    }

}
