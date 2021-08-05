package com.sayone.ebazzar.controller;
import com.sayone.ebazzar.common.Notes;
import com.sayone.ebazzar.common.RestResources;
import com.sayone.ebazzar.dto.UserDto;
import com.sayone.ebazzar.exception.ErrorMessages;
import com.sayone.ebazzar.exception.RequestException;
import com.sayone.ebazzar.model.request.*;
import com.sayone.ebazzar.model.response.*;
import com.sayone.ebazzar.service.UserService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
@RestController
@RequestMapping(RestResources.USER_ROOT)
public class UserController {
    @Autowired
    UserService userService;

//    http://localhost:8080/users/registration
    @ApiOperation(value = "API for user registration",notes = Notes.USER_REGISTRATION)
    @PostMapping(path =RestResources.ADD_USER)
    public ResponseEntity<UserRestModel>  createUser(@RequestBody UserDetailsRequestModel userDetails){
        try {
            UserRestModel userRestModel=userService.createUser(userDetails);
            return new ResponseEntity<>(userRestModel,HttpStatus.CREATED);
        }
        catch (Exception e){
            e.printStackTrace();
            throw new RequestException(e.getMessage());
        }
    }

//    http://localhost:8080/users/update
    @ApiOperation(value = " API for updating user details",notes = Notes.USER_UPDATE)
    @ApiImplicitParams({@ApiImplicitParam(name = "authorization", value = "${userController.authorizationHeader.description}", paramType = "header")})
    @PutMapping(path = RestResources.UPDATE_USER_DETAILS)
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

    //    http://localhost:8080/users/profile
    @ApiOperation(value = "API for getting user information",notes = Notes.USER_PROFILE)
    @ApiImplicitParams({@ApiImplicitParam(name = "authorization", value = "${userController.authorizationHeader.description}", paramType = "header")})
    @GetMapping(path = RestResources.GET_USER_DETAILS)
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

    //    http://localhost:8080/users/teena@gmail.com/forgot-password
    @ApiOperation(value = "API for forgot password",notes = Notes.FORGOT_PASSWORD)
    @GetMapping(path = RestResources.FORGET_PASSWORD)
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
            throw new RequestException(e.getMessage());
        }
    }

    //    http://localhost:8080/users/teena@gmail.com/resetpassword
    @ApiOperation(value = "API for resetting password",notes = Notes.RESET_PASSWORD)
    @PutMapping(path = RestResources.RESET_PASSWORD)
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
            throw new RequestException(ErrorMessages.NO_RECORD_FOUND.getErrorMessages());
        }
    }

    //    http://localhost:8080/users/add-address
    @ApiOperation(value = "API for adding address for the user",notes = Notes.ADD_ADDRESS)
    @ApiImplicitParams({@ApiImplicitParam(name = "authorization", value = "${userController.authorizationHeader.description}", paramType = "header")})
    @PostMapping(path = RestResources.ADD_ADDRESS)
    public ResponseEntity<AddressResponseModel>  addAddress(@RequestBody AddressRequestModel newAddress){
        try {
            Authentication auth=SecurityContextHolder.getContext().getAuthentication();
            UserDto userDto=userService.getUser(auth.getName());
            AddressResponseModel returnValue=userService.addAddress(userDto.getUserId(),newAddress);
            return new ResponseEntity<>(returnValue,HttpStatus.CREATED);
        }
        catch (Exception e){
            e.printStackTrace();
            throw new RequestException(e.getMessage());
        }
    }

  //    http://localhost:8080/users/delete
  @ApiOperation(value = "API for deleting ",notes = Notes.DELETE_USER)
  @ApiImplicitParams({@ApiImplicitParam(name = "authorization", value = "${userController.authorizationHeader.description}", paramType = "header")})
    @DeleteMapping(path =RestResources.DELETE_USER)
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
