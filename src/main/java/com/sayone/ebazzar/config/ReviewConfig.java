package com.sayone.ebazzar.config;

import com.sayone.ebazzar.entity.AddressEntity;
import com.sayone.ebazzar.entity.CartEntity;
import com.sayone.ebazzar.entity.ProductEntity;
import com.sayone.ebazzar.entity.UserEntity;
import com.sayone.ebazzar.repository.AddressRepository;
import com.sayone.ebazzar.repository.CartRepository;
import com.sayone.ebazzar.repository.ProductRepository;
import com.sayone.ebazzar.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.util.List;

@Configuration
public class ReviewConfig {

    @Bean
    CommandLineRunner commandLineRunner1(AddressRepository addressRepository,
                                         UserRepository userRepository,
                                         ProductRepository productRepository,
                                         CartRepository cartRepository){
        return args -> {

            UserEntity userEntity = new UserEntity("Merry","Joseph",
                    "merryjoseph369@gmail.com","123455",98765,"customer");
            UserEntity userEntity1 = new UserEntity("Meera","Joseph",
                    "meerajoseph123@xyz.com","123455",98765,"seller");
            UserEntity userEntity2 = new UserEntity("Mervin","Joseph",
                    "mervin@xyz.com","123455",98765,"customer");
            userRepository.saveAll(List.of(userEntity1,userEntity,userEntity2));

            AddressEntity addressEntity = new AddressEntity("ABC Lane","ABC Street","ABC City","AS3223","shipping",userEntity);
            AddressEntity addressEntity1 = new AddressEntity("XYZ Lane","XYZ Street","XYZ City","AS3223","billing",userEntity);
            AddressEntity addressEntity2 = new AddressEntity("LMN Lane","LMN Street","LMN City","AS3223","default",userEntity);
            AddressEntity addressEntity3 = new AddressEntity("1233SDFSDFWE","XYZ Street","XYZ City","AS3223","shipping",userEntity1);
            AddressEntity addressEntity4 = new AddressEntity("222WERRERR","LMN Street","LMN City","AS3223","billing",userEntity1);
            addressRepository.saveAll(List.of(addressEntity,addressEntity1,addressEntity2,addressEntity3,addressEntity4));


            CartEntity cartEntity = new CartEntity(3000,userEntity,"open");
            CartEntity cartEntity1 = new CartEntity(5555,userEntity,"open");
            cartRepository.saveAll(List.of(cartEntity,cartEntity1));

            ProductEntity productEntity = new ProductEntity("Soap","Lux Body Wash",40,cartEntity);
            ProductEntity productEntity1 = new ProductEntity("Mobile","Redmi",40000,cartEntity);
            ProductEntity productEntity2 = new ProductEntity("Laptop","HP",50000,cartEntity1);
            productRepository.saveAll(List.of(productEntity,productEntity1,productEntity2));











        };
    }
}
