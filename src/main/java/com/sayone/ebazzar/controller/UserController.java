package com.sayone.ebazzar.controller;


import com.sayone.ebazzar.model.request.UserDetailsRequestModel;
import com.sayone.ebazzar.model.request.UserUpdateRequestModel;
import com.sayone.ebazzar.model.response.UserRestModel;
import com.sayone.ebazzar.model.response.UserUpdateResponseModel;
import com.sayone.ebazzar.service.AddressService;
import com.sayone.ebazzar.service.UserService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("users")
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    AddressService addressService;

    @PostMapping
    public UserRestModel createUser(@RequestBody UserDetailsRequestModel userDetails){

        UserRestModel userRestModel=userService.createUser(userDetails);
        return userRestModel;
    }

    @ApiImplicitParams({@ApiImplicitParam(name = "authorization", value = "${userController.authorizationHeader.description}", paramType = "header")})
    @GetMapping(path = "/{email}")
    public UserRestModel getUser(@PathVariable String email){
        UserRestModel returnValue = userService.getUserByEmail(email);
        return returnValue;
    }


    @ApiImplicitParams({@ApiImplicitParam(name = "authorization", value = "${userController.authorizationHeader.description}", paramType = "header")})
    @PutMapping(path = "/{email}")
    public UserUpdateResponseModel updateUser(@PathVariable String email, @RequestBody UserUpdateRequestModel updateRequestModel){
      UserUpdateResponseModel returnValue=userService.updateUser(updateRequestModel,email);
      return returnValue;
    }



}
