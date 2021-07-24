package com.sayone.ebazzar.controller;

import com.sayone.ebazzar.common.RestResources;
import com.sayone.ebazzar.dto.UserDto;
import com.sayone.ebazzar.entity.WishlistEntity;
import com.sayone.ebazzar.service.UserService;
import com.sayone.ebazzar.service.WishlistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(RestResources.WISHLIST_ROOT)
public class WishlistController {
    @Autowired
    WishlistService wishlistService;
    @Autowired
    UserService userService;
    @PutMapping(path = RestResources.CREATE_WISHLIST)
    public ResponseEntity<WishlistEntity> addToWishlist(@PathVariable (value = "productId") Long productId){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDto user = userService.getUser(auth.getName());
        WishlistEntity wishlistEntity =wishlistService.addProductToWishlist(user.getUserId(),productId);
        return new ResponseEntity<>(wishlistEntity, HttpStatus.OK);
    }
}
