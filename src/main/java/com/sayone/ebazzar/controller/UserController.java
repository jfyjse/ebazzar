package com.sayone.ebazzar.controller;


import com.sayone.ebazzar.dto.UserDto;
import com.sayone.ebazzar.exception.ErrorMessages;
import com.sayone.ebazzar.exception.RequestException;
import com.sayone.ebazzar.model.request.PasswordResetRequestModel;
import com.sayone.ebazzar.model.request.RequestOperationStatus;
import com.sayone.ebazzar.model.request.UserDetailsRequestModel;
import com.sayone.ebazzar.model.request.UserUpdateRequestModel;
import com.sayone.ebazzar.model.response.OperationStatusModel;
import com.sayone.ebazzar.model.response.RequestOperationName;
import com.sayone.ebazzar.model.response.UserRestModel;
import com.sayone.ebazzar.model.response.UserUpdateResponseModel;
import com.sayone.ebazzar.service.UserService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;


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


    @ApiImplicitParams({@ApiImplicitParam(name = "authorization", value = "${userController.authorizationHeader.description}", paramType = "header")})
    @GetMapping(path = "/{email}/password-reset-request")
    public OperationStatusModel passwordResetRequest(@PathVariable String email, HttpServletRequest request) throws Exception{

        OperationStatusModel returnValue=new OperationStatusModel();
        UserDto userDto=userService.getUser(email);

        boolean operationResult=userService.requestPasswordReset(userDto,getSiteURL(request));

        returnValue.setOperationName(RequestOperationName.REQUEST_PASSWORD_RESET.name());
        returnValue.setOperationResult(RequestOperationStatus.ERROR.name());

        if(operationResult){
            returnValue.setOperationResult(RequestOperationStatus.SUCCESS.name());
        }

        return returnValue;
    }


    @ApiImplicitParams({@ApiImplicitParam(name = "authorization", value = "${userController.authorizationHeader.description}", paramType = "header")})
    @GetMapping(path = "/verify")
    public OperationStatusModel verifyEmailToken(@RequestParam(value = "code") String token){

        OperationStatusModel returnValue = new OperationStatusModel();
        returnValue.setOperationName(RequestOperationName.VERIFY_EMAIL.name());

        boolean isVerified=userService.verifyEmailToken(token);

        if(isVerified){
            returnValue.setOperationResult(RequestOperationStatus.SUCCESS.name());
        }
        else {
            returnValue.setOperationResult(RequestOperationStatus.ERROR.name());
        }

        return returnValue;

    }

    private String getSiteURL(HttpServletRequest request) {
        String siteURL = request.getRequestURL().toString();

        return siteURL.replace(request.getServletPath(), "");
    }


    @ApiImplicitParams({@ApiImplicitParam(name = "authorization", value = "${userController.authorizationHeader.description}", paramType = "header")})
    @GetMapping(path = "/{email}/resetpassword")
    public OperationStatusModel verifyPasswordResetToken(
            @PathVariable String email,
            @RequestParam(value = "token") String token,
            @RequestBody PasswordResetRequestModel passwordResetRequestModel
    ) {

        OperationStatusModel returnValue = new OperationStatusModel();
        returnValue.setOperationName(RequestOperationName.REQUEST_PASSWORD_RESET.name());

        boolean isVerified=userService.verifyPasswordResetToken(email,token,passwordResetRequestModel);
        if(isVerified){
            returnValue.setOperationResult(RequestOperationStatus.SUCCESS.name());
        }
        else{
            returnValue.setOperationResult(RequestOperationStatus.ERROR.name());
        }

        return returnValue;
    }


    @DeleteMapping(path ="logout/{email}")
    public ResponseEntity<?> logout(@PathVariable String email, @RequestHeader (name="Authorization") String authHeader ){
        try {

            userService.logout(email,authHeader);
            return new ResponseEntity<>(HttpStatus.OK);
        }

        catch (Exception e){
            e.printStackTrace();
            throw new RequestException(ErrorMessages.COULD_NOT_DELETE_RECORD.getErrorMessages());

        }

    }





}
