package com.sayone.ebazzar.controller;

import com.sayone.ebazzar.dto.AddressDto;
import com.sayone.ebazzar.dto.UserDto;
import com.sayone.ebazzar.entity.AddressEntity;
import com.sayone.ebazzar.model.request.UserDetailsRequestModel;
import com.sayone.ebazzar.model.response.AddressResponseModel;
import com.sayone.ebazzar.model.response.UserRestModel;
import com.sayone.ebazzar.service.AddressService;
import com.sayone.ebazzar.service.UserService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.modelmapper.internal.bytebuddy.description.method.MethodDescription;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("users")
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    AddressService addressService;

    @PostMapping
    public UserRestModel createUser(@RequestBody UserDetailsRequestModel userDetails){

        UserDto user=new UserDto();
        BeanUtils.copyProperties(userDetails,user);


        List<AddressDto> addressDtos = new ArrayList<AddressDto>();
        for (int i = 0; i<userDetails.getAddress().size();i++){

            AddressDto addressDto = new AddressDto();
            BeanUtils.copyProperties(userDetails.getAddress().get(i),addressDto);       //

            addressDto.setUser(user);
            addressDtos.add(addressDto);

        }

        user.setAddressDtos(addressDtos);

        UserDto createdUser = userService.createUser(user);


        List<AddressResponseModel> addressResponseModels = new ArrayList<AddressResponseModel>();
        for (int i =0;i<createdUser.getAddressDtos().size();i++){

            AddressResponseModel addressResponseModel=new AddressResponseModel();
            BeanUtils.copyProperties(createdUser.getAddressDtos().get(i),addressResponseModel);
            addressResponseModels.add(addressResponseModel);
        }

        UserRestModel userRestModel=new UserRestModel();
        BeanUtils.copyProperties(createdUser,userRestModel);
        userRestModel.setAddressResponseModels(addressResponseModels);
        return userRestModel;


    }








    @ApiImplicitParams(
            {
                    @ApiImplicitParam(name="authorization",value = "${userController.authorizationHeader.description}",paramType ="header")
            }
    )
    @GetMapping(path = "/{email}")
    public UserRestModel getUser(@PathVariable String email){

        UserRestModel returnValue = new UserRestModel();
        UserDto user = userService.getUserByEmail(email);
        BeanUtils.copyProperties(user,returnValue);


        List<AddressResponseModel> addressResponseModels = new ArrayList<AddressResponseModel>();
        for(AddressDto addressDto:user.getAddressDtos()){
            AddressResponseModel addressResponseModel= new AddressResponseModel();
            BeanUtils.copyProperties(addressDto,addressResponseModel);
            addressResponseModels.add(addressResponseModel);

        }

        returnValue.setAddressResponseModels(addressResponseModels);

        return returnValue;

        }




}
