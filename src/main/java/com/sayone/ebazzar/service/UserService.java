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
import java.util.List;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;


    public UserDto createUser(UserDto user){
        if(userRepository.findByEmail(user.getEmail()) != null) throw new RuntimeException("user already exist");

        for (int i =0; i<user.getAddressDtos().size();i++){
            AddressDto addressDto=user.getAddressDtos().get(i);
            addressDto.setUser(user);
            user.getAddressDtos().set(i,addressDto);
        }

        UserEntity userEntity=new UserEntity();
        BeanUtils.copyProperties(user,userEntity);

        userEntity.setEncryptedPassword(bCryptPasswordEncoder.encode(user.getPassword()));

        List<AddressEntity> addressEntities = new ArrayList<AddressEntity>();
        for (int i=0;i<user.getAddressDtos().size();i++){
            AddressDto addressDto=user.getAddressDtos().get(i);
            AddressEntity addressEntity=new AddressEntity();
            BeanUtils.copyProperties(addressDto,addressEntity);
            addressEntities.add(addressEntity);
        }

        userEntity.setAddress(addressEntities);
        UserEntity storedUserDetails=userRepository.save(userEntity);
        List<AddressDto> addressDtos = new ArrayList<AddressDto>();
        for (int i =0;i<storedUserDetails.getAddress().size();i++){
            AddressEntity addressEntity = storedUserDetails.getAddress().get(i);
            AddressDto addressDto = new AddressDto();
            BeanUtils.copyProperties(addressEntity,addressDto);
            addressDtos.add(addressDto);
        }

        UserDto returnValue= new UserDto();
        BeanUtils.copyProperties(storedUserDetails,returnValue);
        returnValue.setAddressDtos(addressDtos);
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
