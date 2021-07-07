package com.sayone.ebazzar.controller;

import com.sayone.ebazzar.dto.UserDto;
import com.sayone.ebazzar.model.request.UserDetailsRequestModel;
import com.sayone.ebazzar.model.response.UserRestModel;
import com.sayone.ebazzar.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("users")
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping
    public UserRestModel createUser(@RequestBody UserDetailsRequestModel userDetails){
        UserRestModel returnValue = new UserRestModel();

        UserDto userDto = new UserDto();
        BeanUtils.copyProperties(userDetails,userDto);

        userDto.setAddress(userDetails.getAddress());


        UserDto createdUser = userService.createUser(userDto);
        BeanUtils.copyProperties(createdUser,returnValue);


        return returnValue;
    }


}
