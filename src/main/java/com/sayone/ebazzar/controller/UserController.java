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
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
    @PutMapping(path = "/update")
    public ResponseEntity<UserUpdateResponseModel> updateUser(@RequestBody UserUpdateRequestModel updateRequestModel){

        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            UserDto userDto=userService.getUser(auth.getName());
            UserUpdateResponseModel returnValue=userService.updateUser(updateRequestModel,userDto.getEmail());
            return new ResponseEntity<>(returnValue,HttpStatus.ACCEPTED);

        }

        catch (Exception e){
            e.printStackTrace();
            throw new RequestException(ErrorMessages.COULD_NOT_UPDATE_RECORD.getErrorMessages());
        }

    }

    @ApiImplicitParams({@ApiImplicitParam(name = "authorization", value = "${userController.authorizationHeader.description}", paramType = "header")})
    @GetMapping(path = "/profile")
    public ResponseEntity<UserRestModel> getUserDetails(){

        try{
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            UserDto userDto=userService.getUser(auth.getName());
            UserRestModel returnValue = userService.getUserByEmail(userDto.getEmail());
            return new ResponseEntity<>(returnValue,HttpStatus.OK);
        }
        catch (Exception e){
            e.printStackTrace();
            throw new RequestException(ErrorMessages.NO_RECORD_FOUND.getErrorMessages());
        }
    }



//    @GetMapping(path = "/{email}/forgot-password")
//    public OperationStatusModel forgotPassword(@PathVariable String email, HttpServletRequest request) throws Exception{
//
//        OperationStatusModel returnValue=new OperationStatusModel();
//        UserDto userDto=userService.getUser(email);
//
//        boolean operationResult=userService.requestPasswordReset(userDto,getSiteURL(request));
//
//        returnValue.setOperationName(RequestOperationName.REQUEST_PASSWORD_RESET.name());
//        returnValue.setOperationResult(RequestOperationStatus.ERROR.name());
//
//        if(operationResult){
//            returnValue.setOperationResult(RequestOperationStatus.SUCCESS.name());
//        }
//
//        return returnValue;
//    }


    @GetMapping(path = "/{email}/forgot-password")
    public ResponseEntity<OperationStatusModel> forgotPassword(@PathVariable String email, HttpServletRequest request) throws Exception{

        try {
            OperationStatusModel returnValue=new OperationStatusModel();
            UserDto userDto=userService.getUser(email);

            boolean operationResult=userService.requestPasswordReset(userDto,getSiteURL(request));

            returnValue.setOperationName(RequestOperationName.REQUEST_PASSWORD_RESET.name());
            returnValue.setOperationResult(RequestOperationStatus.ERROR.name());

            if(operationResult){
                returnValue.setOperationResult(RequestOperationStatus.SUCCESS.name());
            }

            return new ResponseEntity<>(returnValue,HttpStatus.ACCEPTED);
        }

        catch (Exception e){
            e.printStackTrace();
            throw new RequestException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());
        }

    }






//    @PostMapping(path = "/{email}/resetpassword")
//    public OperationStatusModel verifyPasswordResetToken(
//            @PathVariable String email,
//            @RequestParam(value = "token") String token,
//            @RequestBody PasswordResetRequestModel passwordResetRequestModel
//    ) {
//
//        OperationStatusModel returnValue = new OperationStatusModel();
//        returnValue.setOperationName(RequestOperationName.REQUEST_PASSWORD_RESET.name());
//
//        boolean isVerified=userService.verifyPasswordResetToken(email,token,passwordResetRequestModel);
//        if(isVerified){
//            returnValue.setOperationResult(RequestOperationStatus.SUCCESS.name());
//        }
//        else{
//            returnValue.setOperationResult(RequestOperationStatus.ERROR.name());
//        }
//
//        return returnValue;
//    }
//


    @PostMapping(path = "/{email}/resetpassword")
    public ResponseEntity<OperationStatusModel> verifyPasswordResetToken(
            @PathVariable String email,
            @RequestParam(value = "token") String token,
            @RequestBody PasswordResetRequestModel passwordResetRequestModel
    ) {

        try {
            OperationStatusModel returnValue = new OperationStatusModel();
            returnValue.setOperationName(RequestOperationName.REQUEST_PASSWORD_RESET.name());

            boolean isVerified=userService.verifyPasswordResetToken(email,token,passwordResetRequestModel);
            if(isVerified){
                returnValue.setOperationResult(RequestOperationStatus.SUCCESS.name());
            }
            else{
                returnValue.setOperationResult(RequestOperationStatus.ERROR.name());
            }

            return new ResponseEntity<>(returnValue,HttpStatus.ACCEPTED);
        }
        catch (Exception e){
            e.printStackTrace();
            throw new RequestException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());
        }
    }


    @ApiImplicitParams({@ApiImplicitParam(name = "authorization", value = "${userController.authorizationHeader.description}", paramType = "header")})
    @DeleteMapping(path ="/delete")
    public ResponseEntity<?> deleteUser(){

        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            UserDto userDto=userService.getUser(auth.getName());
            userService.deleteUser(userDto.getEmail());
            return new ResponseEntity<>(HttpStatus.OK);
        }

        catch (Exception e){
            e.printStackTrace();
            throw new RequestException(ErrorMessages.COULD_NOT_DELETE_RECORD.getErrorMessages());

        }

    }

    private String getSiteURL(HttpServletRequest request) {
        String siteURL = request.getRequestURL().toString();

        return siteURL.replace(request.getServletPath(), "");
    }

}
