package com.sayone.ebazzar.controller;


import com.sayone.ebazzar.exception.ErrorMessages;
import com.sayone.ebazzar.exception.RequestException;
import com.sayone.ebazzar.model.request.UserDetailsRequestModel;
import com.sayone.ebazzar.model.request.UserUpdateRequestModel;
import com.sayone.ebazzar.model.response.UserRestModel;
import com.sayone.ebazzar.model.response.UserUpdateResponseModel;
import com.sayone.ebazzar.service.UserService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("users")
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping
    public ResponseEntity<UserRestModel>  createUser(@RequestBody UserDetailsRequestModel userDetails){

        try {
            UserRestModel userRestModel=userService.createUser(userDetails);
            return new ResponseEntity<>(userRestModel,HttpStatus.CREATED);
        }
        catch (Exception e){
            e.printStackTrace();
            throw new RequestException(ErrorMessages.INTERNAL_SERVER_ERROR.getErrorMessages());
        }

    }

    @ApiImplicitParams({@ApiImplicitParam(name = "authorization", value = "${userController.authorizationHeader.description}", paramType = "header")})
    @PutMapping(path = "/{email}")
    public ResponseEntity<UserUpdateResponseModel> updateUser(@PathVariable String email, @RequestBody UserUpdateRequestModel updateRequestModel){

        try {
            UserUpdateResponseModel returnValue=userService.updateUser(updateRequestModel,email);
            return new ResponseEntity<>(returnValue,HttpStatus.ACCEPTED);

        }

        catch (Exception e){
            e.printStackTrace();
            throw new RequestException(ErrorMessages.COULD_NOT_UPDATE_RECORD.getErrorMessages());
        }

    }

    @ApiImplicitParams({@ApiImplicitParam(name = "authorization", value = "${userController.authorizationHeader.description}", paramType = "header")})
    @GetMapping(path = "/{email}")
    public ResponseEntity<UserRestModel> getUser(@PathVariable String email){

        try{
            UserRestModel returnValue = userService.getUserByEmail(email);
            return new ResponseEntity<>(returnValue,HttpStatus.OK);

        }

        catch (Exception e){
            e.printStackTrace();
            throw new RequestException(ErrorMessages.NO_RECORD_FOUND.getErrorMessages());
        }
    }



    @ApiImplicitParams({@ApiImplicitParam(name = "authorization", value = "${userController.authorizationHeader.description}", paramType = "header")})
    @DeleteMapping(path ="/{email}")
    public ResponseEntity<?> deleteProduct(@PathVariable String email){

        try {
            userService.deleteUser(email);
            return new ResponseEntity<>(HttpStatus.OK);
        }

        catch (Exception e){
            e.printStackTrace();
            throw new RequestException(ErrorMessages.COULD_NOT_DELETE_RECORD.getErrorMessages());

        }

    }

}
