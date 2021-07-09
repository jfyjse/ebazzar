package com.sayone.ebazzar.service;

import com.sayone.ebazzar.dto.AddressDto;
import com.sayone.ebazzar.dto.UserDto;
import com.sayone.ebazzar.entity.AddressEntity;
import com.sayone.ebazzar.entity.UserEntity;
import com.sayone.ebazzar.repository.UserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;


    public UserDto createUser(UserDto user){




        if(userRepository.findByEmail(user.getEmail()) != null) throw new RuntimeException("user already exist");


        UserEntity userEntity = new UserEntity();
        BeanUtils.copyProperties(user,userEntity);

        userEntity.setEncryptedPassword(bCryptPasswordEncoder.encode(user.getPassword()));


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

    public UserDto getUser(String email){
        UserEntity userEntity=userRepository.findByEmail(email);

        if (userEntity==null) throw new UsernameNotFoundException(email);

        UserDto returnValue=new UserDto();
        BeanUtils.copyProperties(userEntity, returnValue);
        return returnValue;
    }


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserEntity userEntity = userRepository.findByEmail(email);

        if(userEntity == null) throw new UsernameNotFoundException(email);
        return new User(userEntity.getEmail(),userEntity.getEncryptedPassword(),new ArrayList<>());
    }
}
