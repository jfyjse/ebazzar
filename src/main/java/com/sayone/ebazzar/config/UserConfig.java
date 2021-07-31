package com.sayone.ebazzar.config;


import com.sayone.ebazzar.entity.AddressEntity;
import com.sayone.ebazzar.entity.UserEntity;
import com.sayone.ebazzar.repository.AddressRepository;
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
    CommandLineRunner userCommandLineRunner(UserRepository userRepository, AddressRepository addressRepository){
        return args -> {
            UserEntity user1=new UserEntity("Teena","Alex","teena@gmail.com","123",62525,"customer");
            UserEntity user2=new UserEntity("Miya","Mathew","miya@gmail.com","123",62525,"customer");
            UserEntity user3=new UserEntity("Rahul","Thomas","rahul@gmail.com","123",62525,"seller");

            user1.setEncryptedPassword(bCryptPasswordEncoder.encode(user1.getPassword()));
            user2.setEncryptedPassword(bCryptPasswordEncoder.encode(user2.getPassword()));
            user3.setEncryptedPassword(bCryptPasswordEncoder.encode(user3.getPassword()));
            user1.setUserStatus(true);
            user2.setUserStatus(true);
            user3.setUserStatus(true);
            userRepository.saveAll(List.of(user1,user2,user3));

            AddressEntity addressEntity1 = new AddressEntity("ABC Lane","ABC Street","ABC City","AS3223","shipping",user1);
            AddressEntity addressEntity2 = new AddressEntity("1233SDFSDFWE","XYZ Street","XYZ City","AS3223","billing",user1);
            AddressEntity addressEntity3 = new AddressEntity("XYZ Lane","XYZ Street","XYZ City","AS3223","shipping",user2);
            AddressEntity addressEntity4 = new AddressEntity("1233SDFSDFWE","XYZ Street","XYZ City","AS3223","billing",user2);
            AddressEntity addressEntity5 = new AddressEntity("222WERRERR","LMN Street","LMN City","AS3223","shipping",user3);
            AddressEntity addressEntity6 = new AddressEntity("1233SDFSDFWE","XYZ Street","XYZ City","AS3223","billing",user3);
            addressRepository.saveAll(List.of(addressEntity1,addressEntity2,addressEntity3,addressEntity4,addressEntity5,addressEntity6));

        };
    }

}
