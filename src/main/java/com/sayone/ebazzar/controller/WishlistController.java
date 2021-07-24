package com.sayone.ebazzar.controller;
import com.sayone.ebazzar.common.RestResources;
import com.sayone.ebazzar.dto.UserDto;
import com.sayone.ebazzar.entity.WishlistEntity;
import com.sayone.ebazzar.entity.WishlistItemEntity;
import com.sayone.ebazzar.exception.ErrorMessages;
import com.sayone.ebazzar.exception.RequestException;
import com.sayone.ebazzar.service.UserService;
import com.sayone.ebazzar.service.WishlistService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(RestResources.WISHLIST_ROOT)
public class WishlistController {
    @Autowired
    WishlistService wishlistService;
    @Autowired
    UserService userService;
    @ApiImplicitParams({@ApiImplicitParam(name = "authorization", value = "${userController.authorizationHeader.description}", paramType = "header")})
    @PostMapping(path = RestResources.CREATE_WISHLIST)
    public ResponseEntity<WishlistEntity> addToWishlist(@PathVariable (value = "productId") Long productId){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDto user = userService.getUser(auth.getName());
        WishlistEntity wishlistEntity =wishlistService.addProductToWishlist(user.getUserId(),productId);
        return new ResponseEntity<>(wishlistEntity, HttpStatus.OK);
    }

    @ApiImplicitParams({@ApiImplicitParam(name = "authorization", value = "${userController.authorizationHeader.description}", paramType = "header")})
    @DeleteMapping(path = RestResources.DELETE_FROM_WISHLIST)
    public void deleteProductFromWishlist(@PathVariable (value = "pid") Long productId){

        if (productId == null ){
            throw new RequestException(ErrorMessages.WISH_PID_NOTFOUND.getErrorMessages());}

        else {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            UserDto user = userService.getUser(auth.getName());
            wishlistService.removeProductFromWishlist(user.getUserId(), productId);

        }

    }

    @ApiImplicitParams({@ApiImplicitParam(name = "authorization", value = "${userController.authorizationHeader.description}", paramType = "header")})
    @GetMapping(path = RestResources.GET_WISHLIST)
    public List<WishlistItemEntity> getWishlistItems(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDto user = userService.getUser(auth.getName());
        return wishlistService.getWishlistItems(user.getUserId());

    }

}
