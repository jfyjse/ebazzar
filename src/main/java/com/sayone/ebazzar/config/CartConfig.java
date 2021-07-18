package com.sayone.ebazzar.config;

import com.sayone.ebazzar.entity.*;
import com.sayone.ebazzar.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.util.List;

@Configuration
public class CartConfig {

    @Bean
    CommandLineRunner commandLineRunner1(AddressRepository addressRepository,
                                         UserRepository userRepository,
                                         ProductRepository productRepository,
                                         CartRepository cartRepository,
                                         CartItemRepository cartItemRepository){
        return args -> {

            UserEntity userEntity = new UserEntity("abc","def",
                    "1@2.com","123455",87652,"customer");
            UserEntity userEntity1 = new UserEntity("jhi","klm",
                    "2@3.com","123435",987625,"seller");
            UserEntity userEntity2 = new UserEntity("nop","qrs",
                    "4@5.com","123455",983765,"customer");
            userRepository.saveAll(List.of(userEntity1,userEntity,userEntity2));

//            AddressEntity addressEntity = new AddressEntity("ABC Lane","ABC Street","ABC City","AS3223","shipping",userEntity);
//            AddressEntity addressEntity1 = new AddressEntity("XYZ Lane","XYZ Street","XYZ City","AS3223","billing",userEntity);
//            AddressEntity addressEntity2 = new AddressEntity("LMN Lane","LMN Street","LMN City","AS3223","default",userEntity);
//            AddressEntity addressEntity3 = new AddressEntity("1233SDFSDFWE","XYZ Street","XYZ City","AS3223","shipping",userEntity1);
//            AddressEntity addressEntity4 = new AddressEntity("222WERRERR","LMN Street","LMN City","AS3223","billing",userEntity1);
//            addressRepository.saveAll(List.of(addressEntity,addressEntity1,addressEntity2,addressEntity3,addressEntity4));


            CartEntity cartEntity = new CartEntity(userEntity,"open");
//            CartEntity cartEntity1 = new CartEntity(userEntity,"open");
            cartRepository.saveAll(List.of(cartEntity));
//
            ProductEntity productEntity = new ProductEntity("Soap",150,"Lux Body Wash",40);
            ProductEntity productEntity1 = new ProductEntity("Mobile",50,"Redmi",40000);
            ProductEntity productEntity2 = new ProductEntity("Laptop",30,"HP",50000);
            productRepository.saveAll(List.of(productEntity,productEntity1,productEntity2));
//
//            CartItemEntity cartItemEntity = new CartItemEntity(productEntity,10,cartEntity);
//            CartItemEntity cartItemEntity1 = new CartItemEntity(productEntity1,1,cartEntity);
//            cartItemRepository.saveAll(List.of(cartItemEntity,cartItemEntity1));


        };
    }
}