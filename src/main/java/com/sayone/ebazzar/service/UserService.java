package com.sayone.ebazzar.service;

import com.sayone.ebazzar.dto.AddressDto;
import com.sayone.ebazzar.dto.UserDto;
import com.sayone.ebazzar.entity.AddressEntity;
import com.sayone.ebazzar.entity.UserEntity;
import com.sayone.ebazzar.repository.UserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;


    public UserDto createUser(UserDto user){

        if(userRepository.findByEmail(user.getEmail()) != null) throw new RuntimeException("user already exist");


        UserEntity userEntity = new UserEntity();
        BeanUtils.copyProperties(user,userEntity);

        //   userEntity.setAddress(user.getAddress());
        AddressDto addressDto = new AddressDto();
        addressDto=user.getAddress();

        AddressEntity addressEntity= new AddressEntity();
        BeanUtils.copyProperties(addressDto,addressEntity);
        addressEntity.setUser(userEntity);
        userEntity.setAddress(addressEntity);


       UserEntity storedUserDetails =  userRepository.save(userEntity);


        UserDto returnValue= new UserDto();
        BeanUtils.copyProperties(storedUserDetails,returnValue);


        return returnValue;


    }


}
