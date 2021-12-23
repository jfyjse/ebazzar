package com.sayone.ebazzar.controller;


import com.sayone.ebazzar.common.Notes;
import com.sayone.ebazzar.model.request.LoginRequestModel;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.ResponseHeader;


@RestController


public class AuthenticationController {
    @ApiOperation(value = "User login",notes = Notes.USER_LOGIN)
    @ApiResponses(value = {
            @ApiResponse(code = 200,
                    message = "Response Headers",
                    responseHeaders = {
                            @ResponseHeader(name = "authorization",
                                    description = "Bearer <JWT value here>")
                    })
    })
    @PostMapping("/users/login")
    public void theFakeLogin(@RequestBody LoginRequestModel loginRequestModel)
    {
        throw new IllegalStateException("This method should not be called. This method is implemented by Spring Security");
    }
}
